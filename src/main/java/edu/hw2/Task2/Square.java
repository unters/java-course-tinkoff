package edu.hw2.Task2;

public class Square extends Rectangle {
    RectangleProperties setSide(short sideLength) throws IllegalArgumentException {
        super.setHeight(sideLength);
        return super.setWidth(sideLength);
    }

    @Override
    RectangleProperties setWidth(short width) throws IllegalArgumentException {
        return setSide(width);
    }

    @Override
    RectangleProperties setHeight(short height) throws IllegalArgumentException {
        return setSide(height);
    }

    @Override
    int getArea() {
        return (int) width * width;
    }
}
