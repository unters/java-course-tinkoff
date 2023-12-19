package edu.project4.utils;

import java.util.concurrent.ThreadLocalRandom;

public record AffineTransformation(double a, double b, double c, double d, double e, double f, Rgb color) {

    @SuppressWarnings({"MultipleVariableDeclarations", "MagicNumber"})
    public static AffineTransformation generate() {
        double a, b, c, d, e, f;
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        do {
            a = threadLocalRandom.nextDouble(-1., 1.);
            b = threadLocalRandom.nextDouble(-1., 1.);
            c = threadLocalRandom.nextDouble(-1., 1.);
            d = threadLocalRandom.nextDouble(-1., 1.);
            e = threadLocalRandom.nextDouble(-1., 1.);
            f = threadLocalRandom.nextDouble(-1., 1.);
        } while (!isValid(a, b, c, d, e, f));
        Rgb color =
            new Rgb(threadLocalRandom.nextInt(256), threadLocalRandom.nextInt(256), threadLocalRandom.nextInt(256));
        return new AffineTransformation(a, b, c, d, e, f, color);
    }

    private static boolean isValid(double a, double b, double c, double d, double e, double f) {
        if (a * a + d * d >= 1.) {
            return false;
        }
        if (b * b + e * e >= 1.) {
            return false;
        }
        if (a * a + b * b + d * d + e * e >= 1. + (a * e - b * d) * (a * e - b * d)) {
            return false;
        }
        return true;
    }
}
