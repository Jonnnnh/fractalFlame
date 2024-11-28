package org.example.transformations.linear;

import org.example.core.Point;
import org.example.transformations.Transformation;

public class SinTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = Math.sin(point.x());
        double y = Math.sin(point.y());
        return new Point(x, y);
    }
}
