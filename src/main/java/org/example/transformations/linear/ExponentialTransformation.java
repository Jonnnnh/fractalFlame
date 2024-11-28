package org.example.transformations.linear;

import org.example.core.Point;
import org.example.transformations.Transformation;

public class ExponentialTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double expFactor = Math.exp(x - 1);
        double newX = expFactor * Math.cos(Math.PI * y);
        double newY = expFactor * Math.sin(Math.PI * y);

        return new Point(newX, newY);
    }
}