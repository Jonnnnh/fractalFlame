package org.example.transformations.linear;

import org.example.core.Point;
import org.example.transformations.Transformation;

public class TangentTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double newX = Math.sin(point.x()) / Math.cos(point.y());
        double newY = Math.tan(point.y());
        return new Point(newX, newY);
    }
}
