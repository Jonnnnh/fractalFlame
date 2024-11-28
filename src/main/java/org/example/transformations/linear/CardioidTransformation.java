package org.example.transformations.linear;

import org.example.core.Point;
import org.example.transformations.Transformation;

public class CardioidTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double r = Math.pow(point.x(), 2) + Math.pow(point.y(), 2);
        double k = Math.sqrt(r);
        double arcTan = Math.atan2(point.y(), point.x());
        return new Point(k * Math.sin(k * arcTan), -k * Math.cos(k * arcTan));
    }
}
