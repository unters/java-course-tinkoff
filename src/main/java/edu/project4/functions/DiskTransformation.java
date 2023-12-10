package edu.project4.functions;

import edu.project4.utils.Coordinates;
import static java.lang.Math.PI;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class DiskTransformation implements Transformation {

    @Override
    public Coordinates transform(double y, double x) {
        double multiplier = 1. / PI * atan(y / x);
        double phi = PI * sqrt(x * x + y * y);
        return new Coordinates(
            multiplier * cos(phi),
            multiplier * sin(phi)
        );
    }
}
