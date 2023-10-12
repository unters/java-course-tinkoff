package edu.hw2.Task2;

public class Rectangle {
    protected short width;
    protected short height;

    Rectangle(short width, short height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width and height must be positive.");
        }

        this.width = width;
        this.height = height;
    }

    protected Rectangle() {
    }

    public short width() {
        return this.width;
    }

    public short height() {
        return this.height;
    }

    public int getArea() {
        return width * height;
    }
}
