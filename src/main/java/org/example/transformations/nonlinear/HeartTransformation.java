package org.example.transformations.nonlinear;

import org.example.core.Point;
import org.example.transformations.Transformation;

public class HeartTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double r = Math.sqrt(Math.pow(point.x(), 2) + Math.pow(point.y(), 2));
        double theta = Math.atan2(point.y(), point.x());
        double newX = r * Math.sin(theta * r);
        double newY = -r * Math.cos(theta * r);
        return new Point(newX, newY);
    }
}
