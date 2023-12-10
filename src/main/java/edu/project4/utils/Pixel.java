package edu.project4.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pixel {

    private int counter;
    private Rgb color;
    private double normal;

    public Pixel() {
        counter = 0;
        color = new Rgb();
    }

    public void incrementCounter() {
        ++counter;
    }
}
