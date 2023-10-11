package edu.hw2.Task2;

public class Square extends Rectangle {
    RectangleProperties setSide(short sideLength) {
        super.setHeight(sideLength);
        return super.setWidth(sideLength);
    }

    @Override
    RectangleProperties setWidth(short width) {
        return setSide(width);
    }

    @Override
    RectangleProperties setHeight(short height) {
        return setSide(height);
    }

    @Override
    int getArea() {
        return (int) width * width;
    }
}
