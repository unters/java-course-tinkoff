package edu.project4.functions;

import edu.project4.utils.Coordinates;

public class PolarTransformation implements Transformation {
    @Override
    public Coordinates transform(double y, double x) {
        return new Coordinates(Math.sqrt(x * x + y * y) - 1, Math.atan(y / x) / Math.PI);
    }
}
