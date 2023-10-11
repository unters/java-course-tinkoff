package edu.hw2.Task2;

public class Rectangle {
    protected short width = 0;
    protected short height = 0;

    RectangleProperties setWidth(short width) {
        this.width = width;
        return new RectangleProperties(this.width, this.height);
    }

    RectangleProperties setHeight(short height) {
        this.height = height;
        return new RectangleProperties(this.width, this.height);
    }

    int getArea() {
        return (int) width * height;
    }

    public record RectangleProperties(short width, short height) {
    }
}
