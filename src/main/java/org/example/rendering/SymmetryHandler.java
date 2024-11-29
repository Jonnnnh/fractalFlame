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

            int x = (int) Math.round((rotatedPoint.x() - fractalRect.x()) / fractalRect.width() * width);
            int y = (int) Math.round((rotatedPoint.y() - fractalRect.y()) / fractalRect.height() * height);

            if (x >= 0 && x < width && y >= 0 && y < height) {
                if (fractalRect.contains(new Point(rotatedPoint.x(), rotatedPoint.y()))) {
                    Pixel existingPixel = fractalImage.getPixel(x, y);

                    int red = Math.min(255, (existingPixel.red() + color.red()) / 2);
                    int green = Math.min(255, (existingPixel.green() + color.green()) / 2);
                    int blue = Math.min(255, (existingPixel.blue() + color.blue()) / 2);

                    fractalImage.setPixel(x, y, new Pixel(red, green, blue,  existingPixel.hitCount() + 1));
                }
            }
        }
    }
}


