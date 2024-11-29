package org.example;

import org.example.config.FractalConfig;
import org.example.core.FractalImage;
import org.example.processing.ImageProcessor;
import org.example.rendering.SymmetryHandler;
import org.example.rendering.SingleThreadRenderer;
import org.example.transformations.Transformation;
import org.example.transformations.TransformationFactory;
import org.example.transformations.AffineTransformationGenerator;
import org.example.transformations.nonlinear.AffineTransformation;

import java.util.List;

public class FractalGenerator {

    private final SingleThreadRenderer renderer;
    private final AffineTransformationGenerator affineTransformationGenerator;

    public FractalGenerator() {
        SymmetryHandler symmetryHandler = new SymmetryHandler();
        this.renderer = new SingleThreadRenderer(symmetryHandler);
        this.affineTransformationGenerator = new AffineTransformationGenerator();
    }

    public FractalImage generateFractal(FractalConfig config, ImageProcessor imageProcessor) {
        List<Transformation> transformations = TransformationFactory.createTransformations(config.transformations);
        FractalImage fractalImage = FractalImage.create(config.width, config.height);
        List<AffineTransformation> affineTransformations = affineTransformationGenerator.generate(config.affineCoefficients);
        renderer.render(fractalImage, affineTransformations, transformations, config);
        imageProcessor.process(fractalImage);
        return fractalImage;
    }
}
