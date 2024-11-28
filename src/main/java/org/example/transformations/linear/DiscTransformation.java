package org.example.transformations.linear;

import org.example.core.Point;
import org.example.transformations.Transformation;

public class DiscTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double sqrtOfQuadXY = Math.sqrt(Math.pow(point.x(), 2) + Math.pow(point.y(), 2));
        double atanOfYDelX = Math.atan(point.y() / point.x());
        double oneDelPI = 1 / Math.PI;
        double x = oneDelPI * atanOfYDelX * Math.sin(Math.PI * sqrtOfQuadXY);
        double y = oneDelPI * atanOfYDelX * Math.cos(Math.PI * sqrtOfQuadXY);
        return new Point(x, y);
    }
}
