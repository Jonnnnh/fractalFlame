package org.example.transformations;

import org.example.transformations.nonlinear.*;

import java.util.*;

public class TransformationFactory {

    private static final Map<String, Transformation> transformations = new HashMap<>();

    static {
        transformations.put("heart", new HeartTransformation());
        transformations.put("disc", new DiscTransformation());
        transformations.put("exponential", new ExponentialTransformation());
        transformations.put("handkerchief", new HandkerchiefTransformation());
        transformations.put("polar", new PolarTransformation());
        transformations.put("sin", new SinTransformation());
        transformations.put("spherical", new SphericalTransformation());
        transformations.put("swirl", new SwirlTransformation());
        transformations.put("eyefish", new EyefishTransformation());
        transformations.put("tangent", new TangentTransformation());
        transformations.put("hyperbolic", new HyperbolicTransformation());
    }

    public static List<Transformation> createTransformations(List<String> transformationNames) {
        if (transformationNames == null || transformationNames.isEmpty()) {
            throw new IllegalArgumentException("Transformation list cannot be empty.");
        }
        List<Transformation> transformations = new ArrayList<>();

        for (String name : transformationNames) {

            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Transformation name cannot be empty or whitespace");
            }

            Transformation transformation = TransformationFactory.transformations.get(name.toLowerCase());
            if (transformation != null) {
                transformations.add(transformation);

            } else {
                throw new IllegalArgumentException(String.format("Unknown transformation: %s", name));
            }
        }

        return transformations;
    }
}
