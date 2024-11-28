package org.example.processing;

import org.example.core.FractalImage;
import org.example.core.Pixel;

public class GammaCorrection implements ImageProcessor {
    private final double gamma;

    public GammaCorrection(double gamma) {
        this.gamma = gamma;
    }

    @Override
    public void process(FractalImage image) {
        double max = 0.0;
        double[][] normals = new double[image.height()][image.width()];

        for (int x = 0; x < image.width(); x++) {
            for (int y = 0; y < image.height(); y++) {
                Pixel pixel = image.getPixel(x, y);
                if (pixel.hitCount() != 0) {
                    normals[y][x] = Math.log10(pixel.hitCount());
                    max = Math.max(max, normals[y][x]);
                }
            }
        }

        for (int x = 0; x < image.width(); x++) {
            for (int y = 0; y < image.height(); y++) {
                Pixel pixel = image.getPixel(x, y);
                normals[y][x] /= max;

                pixel = pixel.setColor(
                        (int) (pixel.red() * Math.pow(normals[y][x], 1.0 / gamma)),
                        (int) (pixel.green() * Math.pow(normals[y][x], 1.0 / gamma)),
                        (int) (pixel.blue() * Math.pow(normals[y][x], 1.0 / gamma))
                );

                image.setPixel(x, y, pixel);
            }
        }
    }
}
