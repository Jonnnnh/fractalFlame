package org.example.rendering;

import org.example.config.FractalConfig;
import org.example.core.*;
import org.example.transformations.Transformation;
import org.example.transformations.linear.AffineTransformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;

public class MultiThreadRenderer implements FractalRenderer {
    private static final Logger log = LoggerFactory.getLogger(SingleThreadRenderer.class);

    private static final double X_MIN = -1.777;
    private static final double X_MAX = 1.777;
    private static final double Y_MIN = -1.0;
    private static final double Y_MAX = 1.0;
    private static final int RENDER_STEP = 20;
    private final SymmetryHandler symmetryHandler;
    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public MultiThreadRenderer(SymmetryHandler symmetryHandler) {
        if (symmetryHandler == null) {
            throw new IllegalArgumentException("SymmetryHandler cannot be null");
        }
        this.symmetryHandler = symmetryHandler;
    }

    @Override
    public void render(FractalImage image, List<AffineTransformation> affineTransformations,
                       List<Transformation> transformations, FractalConfig config) {
        if (image == null) {
            log.error("FractalImage cannot be null");
            return;
        }
        if (affineTransformations == null || affineTransformations.isEmpty()) {
            log.error("AffineTransformations list is null or empty");
            return;
        }
        if (transformations == null) {
            transformations = new ArrayList<>();
        }
        if (config == null) {
            log.error("FractalConfig cannot be null");
            return;
        }

        int xRes = image.width();
        int yRes = image.height();
        int points = config.points;
        int iterations = config.iterations;
        int symmetricalParts = config.symmetricalParts;
        int motionBlurLength = config.motionBlurLength;
        int threads = config.threads;

        if (threads <= 0) {
            log.error("Invalid number of threads: {}", threads);
            return;
        }

        Rect fractalRect = new Rect(X_MIN, Y_MIN, X_MAX - X_MIN, Y_MAX - Y_MIN);

        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        long startTime = System.nanoTime();

        int rowsPerThread = yRes / threads;
        List<FractalTask> tasks = new ArrayList<>();

        for (int t = 0; t < threads; t++) {
            final int threadIndex = t;
            tasks.add(new FractalTask(threadIndex, rowsPerThread, xRes, yRes, points, iterations, affineTransformations,
                    transformations, motionBlurLength, image, fractalRect, symmetricalParts));
        }

        try {
            executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            log.error("Rendering interrupted", e);
        } finally {
            executorService.shutdown();
        }

        long endTime = System.nanoTime();
        log.info("MultiThreadRenderer Rendering time: {} seconds", TimeUnit.NANOSECONDS.toSeconds(endTime - startTime));
    }

    private class FractalTask implements Callable<Void> {
        private final int threadIndex;
        private final int rowsPerThread;
        private final int xRes;
        private final int yRes;
        private final int points;
        private final int iterations;
        private final List<AffineTransformation> affineTransformations;
        private final List<Transformation> transformations;
        private final int motionBlurLength;
        private final FractalImage image;
        private final Rect fractalRect;
        private final int symmetricalParts;

        public FractalTask(int threadIndex, int rowsPerThread, int xRes, int yRes, int points, int iterations,
                           List<AffineTransformation> affineTransformations, List<Transformation> transformations,
                           int motionBlurLength, FractalImage image, Rect fractalRect, int symmetricalParts) {
            this.threadIndex = threadIndex;
            this.rowsPerThread = rowsPerThread;
            this.xRes = xRes;
            this.yRes = yRes;
            this.points = points;
            this.iterations = iterations;
            this.affineTransformations = affineTransformations;
            this.transformations = transformations;
            this.motionBlurLength = motionBlurLength;
            this.image = image;
            this.fractalRect = fractalRect;
            this.symmetricalParts = symmetricalParts;
        }

        @Override
        public Void call() {
            try {
                int startRow = threadIndex * rowsPerThread;
                int endRow = (threadIndex == (yRes / rowsPerThread) - 1) ? yRes : (threadIndex + 1) * rowsPerThread;

                for (int num = 0; num < points; num++) {
                    double newX = getRandomValue(X_MIN, X_MAX);
                    double newY = getRandomValue(Y_MIN, Y_MAX);

                    for (int step = -RENDER_STEP; step < iterations; step++) {
                        AffineTransformation affine = affineTransformations.get(random.nextInt(affineTransformations.size()));
                        Point transformedPoint = affine.apply(new Point(newX, newY));

                        List<Point> transformedPoints = applyAllTransformations(transformedPoint, transformations);

                        for (Point point : transformedPoints) {
                            newX = point.x();
                            newY = point.y();

                            if (step >= 0 && newX >= X_MIN && newX <= X_MAX && newY >= Y_MIN && newY <= Y_MAX) {
                                int x1 = (int) (xRes - Math.floor(((X_MAX - newX) / (X_MAX - X_MIN)) * xRes));
                                int y1 = (int) (yRes - Math.floor(((Y_MAX - newY) / (Y_MAX - Y_MIN)) * yRes));

                                if (y1 >= startRow && y1 < endRow && image.contains(x1, y1)) {
                                    Pixel pixel = image.getPixel(x1, y1);
                                    Color color = affine.getColor();
                                    for (int i = 0; i < motionBlurLength; i++) {
                                        double offsetX = getRandomValue(-0.05, 0.05);
                                        double offsetY = getRandomValue(-0.05, 0.05);

                                        int blurredX = (int) (x1 + offsetX);
                                        int blurredY = (int) (y1 + offsetY);

                                        if (image.contains(blurredX, blurredY)) {
                                            Pixel blurredPixel = image.getPixel(blurredX, blurredY);
                                            int red = (pixel.red() + color.red()) / 2;
                                            int green = (pixel.green() + color.green()) / 2;
                                            int blue = (pixel.blue() + color.blue()) / 2;

                                            synchronized (image) {
                                                image.setPixel(blurredX, blurredY, blurredPixel.hit().setColor(red, green, blue));
                                            }
                                        }
                                    }

                                    symmetryHandler.applySymmetry(image, point, color, xRes, yRes, fractalRect, symmetricalParts);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Error in FractalTask for thread {}", threadIndex, e);
            }
            return null;
        }
    }

    private List<Point> applyAllTransformations(Point point, List<Transformation> transformations) {
        List<Point> transformedPoints = new ArrayList<>();

        for (Transformation transformation : transformations) {
            Point transformedPoint = transformation.apply(point);
            transformedPoints.add(transformedPoint);
        }

        return transformedPoints;
    }

    private double getRandomValue(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }
}
