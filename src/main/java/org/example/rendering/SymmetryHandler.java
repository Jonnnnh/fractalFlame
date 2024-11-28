package org.example.rendering;

import org.example.core.*;

public class SymmetryHandler {

    public void applySymmetry(FractalImage fractalImage, Point point, Color color, int width, int height, Rect fractalRect, int symmetricalParts) {
        double[] angles = new double[symmetricalParts];
        for (int s = 0; s < symmetricalParts; s++) {
            angles[s] = (2 * Math.PI / symmetricalParts) * s;
        }

        for (double theta : angles) {
            Point rotatedPoint = Point.rotate(point, theta);

            int x = (int) ((rotatedPoint.x() - fractalRect.x()) / fractalRect.width() * width);
            int y = (int) ((rotatedPoint.y() - fractalRect.y()) / fractalRect.height() * height);

            if (fractalRect.contains(new Point(rotatedPoint.x(), rotatedPoint.y()))) {
                fractalImage.setPixel(x, y, new Pixel(color.red(), color.green(), color.blue(), 1));
            }
        }
    }
}
