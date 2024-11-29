package org.example.transformations.linear;

import org.example.core.Point;
import org.example.transformations.Transformation;

public class EyefishTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        double r = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        double newX = 2 * point.x() / (r + 1);
        double newY = 2 * point.y() / (r + 1);
        return new Point(newX, newY);
    }
}
