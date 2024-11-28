package org.example;

import org.example.config.ConfigLoader;
import org.example.config.FractalConfig;
import org.example.core.FractalImage;
import org.example.processing.GammaCorrection;
import org.example.processing.ImageProcessor;
import org.example.rendering.FractalRenderer;
import org.example.rendering.SingleThreadRenderer;
import org.example.rendering.SymmetryHandler;
import org.example.transformations.TransformationFactory;
import org.example.utils.ImageUtils;
import org.example.utils.ImageFormat;
import org.example.utils.FileUtils;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            FractalConfig config = ConfigLoader.loadConfig();

            ImageProcessor gammaProcessor = new GammaCorrection(config.gamma);

            FractalGenerator generator = new FractalGenerator();

            FractalImage fractalImage = generator.generateFractal(config, gammaProcessor);

            String outputPath = FileUtils.generateFileName(ImageFormat.PNG.getStringFormat());
            ImageFormat format = ImageFormat.PNG;

            saveFractalImage(fractalImage, format, outputPath);

            System.out.println("Фрактал сгенерирован и сохранен в файл: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка при загрузке конфигурации или сохранении фрактала");
        }
    }

    private static void saveFractalImage(FractalImage fractalImage, ImageFormat format, String outputPath) throws IOException {
        ImageUtils.save(fractalImage, format, outputPath);
    }
}
