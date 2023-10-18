package edu.hw2.task2;

public class Rectangle {
    protected int width;
    protected int height;

    public Rectangle(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width and height must be positive.");
        }

        this.width = width;
        this.height = height;
    }

    public final Rectangle setWidth(int width) {
        return new Rectangle(width, this.height);
    }

    public final Rectangle setHeight(int height) {
        return new Rectangle(this.width, height);
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    public long getArea() {
        return (long) width * height;
    }
}
