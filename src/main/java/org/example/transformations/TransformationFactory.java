package org.example.transformations;

import org.example.transformations.linear.*;

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
    }

    public static List<Transformation> createTransformations(List<String> transformationNames) {
        List<Transformation> transformations = new ArrayList<>();

        for (String name : transformationNames) {
            Transformation transformation = TransformationFactory.transformations.get(name.toLowerCase());
            if (transformation != null) {
                transformations.add(transformation);
            } else {
                throw new IllegalArgumentException(STR."Unknown transformation: \{name}");
            }
        }

        return transformations;
    }
}
