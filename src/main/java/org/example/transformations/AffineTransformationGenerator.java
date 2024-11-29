package org.example.transformations;

import org.example.transformations.nonlinear.AffineTransformation;

import java.util.ArrayList;
import java.util.List;

public class AffineTransformationGenerator {

    public List<AffineTransformation> generate(int count) {
        List<AffineTransformation> affineTransformations = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            affineTransformations.add(AffineTransformation.createRandomTransformation());
        }
        return affineTransformations;
    }
}
