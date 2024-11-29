package org.example.rendering;

import org.example.config.FractalConfig;
import org.example.core.FractalImage;
import org.example.transformations.Transformation;
import org.example.transformations.linear.AffineTransformation;

import java.util.List;

public interface FractalRenderer {
    void render(FractalImage image, List<AffineTransformation> affineTransformations,
                List<Transformation> transformations, FractalConfig config);
}
