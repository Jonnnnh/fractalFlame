package org.example;

import org.example.config.ConfigLoader;
import org.example.config.FractalConfig;
import org.example.core.FractalImage;
import org.example.processing.GammaCorrection;
import org.example.processing.ImageProcessor;
import org.example.utils.ImageUtils;
import org.example.utils.ImageFormat;
import org.example.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            FractalConfig config = ConfigLoader.loadConfig();

            ImageProcessor gammaProcessor = new GammaCorrection(config.gamma);

            FractalGenerator generator = new FractalGenerator();
            FractalImage fractalImage = generator.generateFractal(config, gammaProcessor);

            String outputPath = FileUtils.generateFileName(ImageFormat.JPEG.getStringFormat());
            saveFractalImage(fractalImage, outputPath);

            logger.info("Fractal generated and saved to a file: {}", outputPath);
        } catch (IOException e) {
            logger.error("Error when loading configuration or saving fractal", e);
        }
    }

    private static void saveFractalImage(FractalImage fractalImage, String outputPath) throws IOException {
        ImageUtils.save(fractalImage, ImageFormat.JPEG, outputPath);
    }
}
