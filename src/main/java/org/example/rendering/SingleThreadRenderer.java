package org.example.rendering;

import org.example.config.FractalConfig;
import org.example.core.FractalImage;
import org.example.core.Pixel;
import org.example.core.Point;
import org.example.core.Rect;
import org.example.core.Color;
import org.example.transformations.Transformation;
import org.example.transformations.nonlinear.AffineTransformation;

import java.util.List;
import java.util.Random;

public class SingleThreadRenderer implements FractalRenderer {

    private static final double XMIN = -1.0;
    private static final double XMAX = 1.0;
    private static final double YMIN = -1.0;
    private static final double YMAX = 1.0;
    private static final int RENDER_STEP_THRESHOLD = 20;
    private final Random random = new Random();

    private final SymmetryHandler symmetryHandler;

    public SingleThreadRenderer(SymmetryHandler symmetryHandler) {
        this.symmetryHandler = symmetryHandler;
    }

    @Override
    public void render(FractalImage image, List<AffineTransformation> affines,
                       List<Transformation> transformations, FractalConfig config) {
        int xRes = image.width();
        int yRes = image.height();
        int points = config.points;
        int iterations = config.iterations;
        int symmetricalParts = config.symmetricalParts;

        Rect fractalRect = new Rect(XMIN, YMIN, XMAX - XMIN, YMAX - YMIN);

        for (int num = 0; num < points; num++) {
            double newX = getRandomValue(XMIN, XMAX);
            double newY = getRandomValue(YMIN, YMAX);

            for (int step = -RENDER_STEP_THRESHOLD; step < iterations; step++) {
                int i = random.nextInt(affines.size());
                AffineTransformation affine = affines.get(i);

                Point transformedPoint = affine.apply(new Point(newX, newY));

                for (Transformation transformation : transformations) {
                    transformedPoint = transformation.apply(transformedPoint);
                }

                newX = transformedPoint.x();
                newY = transformedPoint.y();

                if (step >= 0 && newX >= XMIN && newX <= XMAX && newY >= YMIN && newY <= YMAX) {
                    int x1 = (int) (xRes - Math.floor(((XMAX - newX) / (XMAX - XMIN)) * xRes));
                    int y1 = (int) (yRes - Math.floor(((YMAX - newY) / (YMAX - YMIN)) * yRes));

                    if (image.contains(x1, y1)) {
                        Pixel pixel = image.getPixel(x1, y1);
                        Color color = affine.getColor();

                        if (pixel.hitCount() == 0) {
                            image.setPixel(x1, y1, new Pixel(color.red(), color.green(), color.blue(), 1));
                        } else {
                            int red = (pixel.red() + color.red()) / 2;
                            int green = (pixel.green() + color.green()) / 2;
                            int blue = (pixel.blue() + color.blue()) / 2;
                            image.setPixel(x1, y1, pixel.hit().setColor(red, green, blue));
                        }

                        symmetryHandler.applySymmetry(image, transformedPoint, color, xRes, yRes, fractalRect, symmetricalParts);
                    }
                }
            }
        }
    }

    private double getRandomValue(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }
}
