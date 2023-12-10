package edu.project4.functions;

import edu.project4.utils.Coordinates;

public class SineTransformation implements Transformation {

    @Override
    public Coordinates transform(double y, double x) {
        return new Coordinates(Math.sin(y), Math.sin(x));
    }
}
