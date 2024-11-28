package org.example.transformations.linear;

import org.example.core.Point;
import org.example.transformations.Transformation;

public class SphericalTransformation implements Transformation {
    @Override
    public Point apply(Point p) {
        double r2 = p.x() * p.x() + p.y() * p.y();
        return new Point(p.x() / r2, p.y() / r2);
    }
}
