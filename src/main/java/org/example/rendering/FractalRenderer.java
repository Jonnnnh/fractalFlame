package org.example.rendering;

import org.example.config.FractalConfig;
import org.example.core.FractalImage;
import org.example.transformations.Transformation;
import org.example.transformations.nonlinear.AffineTransformation;

import java.util.List;

public interface FractalRenderer {
    void render(FractalImage image, List<AffineTransformation> affines,
                List<Transformation> transformations, FractalConfig config);
}
