package org.example.transformations.nonlinear;

import lombok.Getter;
import org.example.core.Point;
import org.example.core.Color;
import org.example.transformations.Transformation;

import java.util.Random;

public class AffineTransformation implements Transformation {
    private static final double COEFFICIENT_RANGE_MIN = -1.0;
    private static final double COEFFICIENT_RANGE_MAX = 1.0;

    private static final Random RANDOM = new Random();

    private final double a, b, c, d, e, f;
    @Getter
    private final Color color;

    public AffineTransformation(double a, double b, double c, double d, double e, double f, Color color) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.color = color;
    }

    public static AffineTransformation getRandomTransformation() {
        double a, b, d, e, c, f;

        do {
            a = getRandomCoefficient();
            b = getRandomCoefficient();
            d = getRandomCoefficient();
            e = getRandomCoefficient();
        } while (!isValidCoefficients(a, b, d, e));

        c = getRandomCoefficient();
        f = getRandomCoefficient();

        Color randomColor = generateRandomColor();

        return new AffineTransformation(a, b, c, d, e, f, randomColor);
    }

    private static boolean isValidCoefficients(double a, double b, double d, double e) {
        return a * a + d * d < 1 && b * b + e * e < 1 && a * b + d * e < 1 + Math.pow(a * e + b * d, 2);

    }

    private static double getRandomCoefficient() {
        return COEFFICIENT_RANGE_MIN + (COEFFICIENT_RANGE_MAX - COEFFICIENT_RANGE_MIN) * RANDOM.nextDouble();
    }

    @Override
    public Point apply(Point point) {
        double x = a * point.x() + b * point.y() + c;
        double y = d * point.x() + e * point.y() + f;
        return new Point(x, y);
    }

    private static Color generateRandomColor() {
        return new Color(RANDOM.nextInt(256), RANDOM.nextInt(256), RANDOM.nextInt(256));

    }

}
