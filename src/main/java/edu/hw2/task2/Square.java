package edu.hw2.task2;

public class Square extends Rectangle {
    public Square(int width, int height) {
        super(width, height);
        if (width != height) {
            throw new IllegalArgumentException("Square must have equal width and height.");
        }
    }

    public Square(int side) {
        super(side, side);
    }

    public final Square setSide(int side) {
        return new Square(side);
    }

    public final int getSide() {
        return width;
    }
}
