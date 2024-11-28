package org.example.utils;

import org.example.core.FractalImage;
import org.example.core.Pixel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class ImageUtils {

    private ImageUtils() {
    }

    public static void save(FractalImage image, ImageFormat format, String path) throws IOException {
        File outputDirectory = FileUtils.getOutputDirectory();
        if (!outputDirectory.exists()) {
            System.out.println("Directory does not exist, creating...");
            outputDirectory.mkdir();
        }

        BufferedImage bufferedImage = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < image.width(); x++) {
            for (int y = 0; y < image.height(); y++) {
                Pixel pixel = image.getPixel(x, y);
                int rgb = convertToRGB(pixel);
                bufferedImage.setRGB(x, y, rgb);
            }
        }

        ImageIO.write(bufferedImage, format.getStringFormat(), new File(path));
    }

    private static int convertToRGB(Pixel pixel) {
        return (pixel.red() << 16) | (pixel.green() << 8) | pixel.blue();
    }
}
