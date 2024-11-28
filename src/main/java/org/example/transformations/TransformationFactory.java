package org.example.transformations;

import org.example.transformations.linear.*;
import org.example.core.Point;

import java.util.*;

public class TransformationFactory {

    private static final Map<String, Transformation> transformationsMap = new HashMap<>();

    static {
        transformationsMap.put("cardioid", new CardioidTransformation());
        transformationsMap.put("disc", new DiscTransformation());
        transformationsMap.put("exponential", new ExponentialTransformation());
        transformationsMap.put("handkerchief", new HandkerchiefTransformation());
        transformationsMap.put("polar", new PolarTransformation());
        transformationsMap.put("sin", new SinTransformation());
        transformationsMap.put("spherical", new SphericalTransformation());
        transformationsMap.put("swirl", new SwirlTransformation());
    }

    public static List<Transformation> createTransformations(List<String> transformationNames) {
        List<Transformation> transformations = new ArrayList<>();

        for (String name : transformationNames) {
            Transformation transformation = transformationsMap.get(name.toLowerCase());
            if (transformation != null) {
                transformations.add(transformation);
            } else {
                throw new IllegalArgumentException("Unknown transformation: " + name);
            }
        }

        return transformations;
    }
}
