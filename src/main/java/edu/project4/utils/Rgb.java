package edu.project4.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rgb {
    private int r;
    private int g;
    private int b;

    public Rgb() {
        this(0, 0, 0);
    }

    public Rgb(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void apply(Rgb rgb) {
        r = (r + rgb.getR()) / 2;
        g = (g + rgb.getG()) / 2;
        b = (b + rgb.getB()) / 2;
    }

    public int getRgb() {
        return (r << 16 | g << 8 | b);
    }
}
