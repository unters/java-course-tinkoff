package edu.hw3;

public class Task4 {
    /* According to Alexander Biryukov, it is permitted to limit input value by [1, 3999].  */
    private static final int MIN_PERMITTED_VALUE = 1;
    private static final int MAX_PERMITTED_VALUE = 3999;

    @SuppressWarnings("MagicNumber")    // This suppression is vital.
    public static String convertToRoman(int value) {
        if (value < MIN_PERMITTED_VALUE || value > MAX_PERMITTED_VALUE) {
            throw new IllegalArgumentException(
                "value must be a positive integer not greater than 3999 (given: " + value + ").");
        }

        int temp = value;
        int numberOfDigits = edu.hw1.Task2.countDigits(value);
        StringBuilder sb = new StringBuilder();

        /* Thousands.  */
        sb.append("M".repeat(temp / 1000));

        /* Hundrends.  */
        temp %= 1000;
        int hundreds = temp / 100;
        switch (hundreds) {
            case 9 -> {
                sb.append("CM");
            }

            case 5, 6, 7, 8 -> {
                sb.append("D");
                sb.append("C".repeat(hundreds - 5));
            }

            case 4 -> {
                sb.append("CD");
            }

            case 1, 2, 3 -> {
                sb.append("C".repeat(hundreds));
            }

            default -> {
            }
        }

        /* Dozens.  */
        temp %= 100;
        int dozens = temp / 10;
        switch (dozens) {
            case 9 -> {
                sb.append("XC");
            }

            case 5, 6, 7, 8 -> {
                sb.append("L");
                sb.append("X".repeat(dozens - 5));
            }

            case 4 -> {
                sb.append("XL");
            }

            case 1, 2, 3 -> {
                sb.append("X".repeat(dozens));
            }

            default -> {
            }
        }

        /* Units (one-digit numbers).  */
        int units = temp % 10;
        switch (units) {
            case 9 -> {
                sb.append("IX");
            }

            case 5, 6, 7, 8 -> {
                sb.append("V");
                sb.append("I".repeat(units - 5));
            }

            case 4 -> {
                sb.append("IV");
            }

            case 1, 2, 3 -> {
                sb.append("I".repeat(units));
            }

            default -> {
            }
        }

        return sb.toString();
    }

    private Task4() {
    }
}
