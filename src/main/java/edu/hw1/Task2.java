package edu.hw1;

public class Task2 {
    private static final int BASE = 10;

    public static int countDigits(int value) {
        if (value == 0) {
            return 1;
        }

        int count = 0;
        int temp = value;
        while (temp != 0) {
            ++count;
            temp /= BASE;
        }

        return count;
    }

    private Task2() {
    }
}
