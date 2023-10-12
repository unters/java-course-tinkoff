package edu.hw2.Task2;

public class Square extends Rectangle {
    Square(short width, short height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width and height must be positive.");
        }

        if (width != height) {
            throw new IllegalArgumentException("Square must have equal width and height.");
        }

        this.width = width;
        this.height = height;
    }
}
