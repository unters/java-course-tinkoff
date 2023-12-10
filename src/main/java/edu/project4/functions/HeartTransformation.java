package edu.project4.functions;

import edu.project4.utils.Coordinates;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class HeartTransformation implements Transformation {
    @Override
    public Coordinates transform(double y, double x) {
        double vecLength = sqrt(x * x + y * y);
        double phi = vecLength * atan(y / x);
        return new Coordinates(
            -1. * vecLength * cos(phi),
            vecLength * sin(phi)
        );
    }
}
