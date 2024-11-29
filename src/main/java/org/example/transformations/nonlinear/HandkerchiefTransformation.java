package org.example.transformations.nonlinear;

import org.example.core.Point;
import org.example.transformations.Transformation;

public class HandkerchiefTransformation implements Transformation {
    @Override
    public Point apply(Point p) {
        double r = Math.sqrt(p.x() * p.x() + p.y() * p.y());
        double theta = Math.atan2(p.y(), p.x());
        return new Point(
                r * Math.sin(theta + r),
                r * Math.cos(theta - r)
        );
    }
}
