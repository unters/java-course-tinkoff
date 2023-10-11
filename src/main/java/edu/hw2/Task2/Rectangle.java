package edu.hw2.Task2;

public class Rectangle {
    /* Let's assume that width and height can equal to zero.  */
    protected short width = 0;
    protected short height = 0;

    RectangleProperties setWidth(short width) throws IllegalArgumentException {
        if (width < 0) {
            throw new IllegalArgumentException("width cannot be negative.");
        }

        this.width = width;
        return new RectangleProperties(this.width, this.height);
    }

    RectangleProperties setHeight(short height) throws IllegalArgumentException {
        if (height < 0) {
            throw new IllegalArgumentException("height cannot be negative.");
        }

        this.height = height;
        return new RectangleProperties(this.width, this.height);
    }

    int getArea() {
        return (int) width * height;
    }

    public record RectangleProperties(short width, short height) {
    }
}
