package edu.hw1;

public class Task2 {
    private Task2() {
    }

    @SuppressWarnings("MagicNumber")
    public static int countDigits(int value) {
        if (value == 0) {
            return 1;
        }

        int count = 0;
        int valueCopy = value;
        while (valueCopy != 0) {
            ++count;
            valueCopy /= 10;
        }

        return count;
    }
}
