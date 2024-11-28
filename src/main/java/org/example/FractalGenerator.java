package org.example;

import org.example.config.FractalConfig;
import org.example.core.FractalImage;
import org.example.processing.ImageProcessor;
import org.example.rendering.SymmetryHandler;
import org.example.transformations.Transformation;
import org.example.transformations.TransformationFactory;
import org.example.rendering.SingleThreadRenderer;
import org.example.transformations.nonlinear.AffineTransformation;

import java.util.ArrayList;
import java.util.List;

public class FractalGenerator {

    private final SingleThreadRenderer renderer;
    private final TransformationFactory transformationFactory;
    SymmetryHandler symmetryHandler = new SymmetryHandler();

    public FractalGenerator() {
        this.renderer = new SingleThreadRenderer(symmetryHandler);
        this.transformationFactory = new TransformationFactory();
    }

    public FractalGenerator(SingleThreadRenderer renderer, TransformationFactory transformationFactory) {
        this.renderer = renderer;
        this.transformationFactory = transformationFactory;
    }

    public FractalImage generateFractal(FractalConfig config, ImageProcessor imageProcessor) {
        List<Transformation> transformations = transformationFactory.createTransformations(config.transformations);
        FractalImage fractalImage = FractalImage.create(config.width, config.height);
        List<AffineTransformation> affineTransformations = generateAffineTransformations(config.affineCoefficients);
        renderer.render(fractalImage, affineTransformations, transformations, config);
        imageProcessor.process(fractalImage);
        return fractalImage;
    }

    private List<AffineTransformation> generateAffineTransformations(int count) {
        List<AffineTransformation> affineTransformations = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            affineTransformations.add(AffineTransformation.createRandomTransformation());
        }
        return affineTransformations;
    }
}
