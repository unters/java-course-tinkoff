package edu.project4.functions;

import edu.project4.utils.Coordinates;

public class SphericalTransformation implements Transformation {
    @Override
    public Coordinates transform(double y, double x) {
        return new Coordinates(y / (x * x + y * y), x / (x * x + y * y));
    }
}
