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

    public final Rectangle setWidth(short width) {
        if (width <= 0) {
            throw new IllegalArgumentException("width must be positive.");
        }

        return new Rectangle(width, this.height);
    }

    public final Rectangle setHeight(short height) {
        if (height <= 0) {
            throw new IllegalArgumentException("height must be positive.");
        }

        return new Rectangle(this.width, height);
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
