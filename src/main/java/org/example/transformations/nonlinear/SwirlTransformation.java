package org.example.transformations.nonlinear;

import org.example.core.Point;
import org.example.transformations.Transformation;

public class SwirlTransformation implements Transformation {
    @Override
    public Point apply(Point p) {
        double r2 = p.x() * p.x() + p.y() * p.y();
        double sinR2 = Math.sin(r2);
        double cosR2 = Math.cos(r2);
        return new Point(
                p.x() * sinR2 - p.y() * cosR2,
                p.x() * cosR2 + p.y() * sinR2
        );
    }
}
