package edu.hw3;

@SuppressWarnings("MagicNumber")    // This suppression is vital.
public class Task4 {
    /* According to Alexander Biryukov, it is permitted to limit input value by [1, 3999].  */
    private static final int MIN_PERMITTED_VALUE = 1;
    private static final int MAX_PERMITTED_VALUE = 3999;

    public static String convertToRoman(int value) {
        if (value < MIN_PERMITTED_VALUE || value > MAX_PERMITTED_VALUE) {
            throw new IllegalArgumentException(
                "value must be a positive integer not greater than 3999 (given: " + value + ").");
        }

        return getThousands(value) + getHundreds(value) + getDozens(value) + getUnits(value);
    }

    private static String getThousands(int value) {
        return "M".repeat(value / 1000);
    }

    private static String getHundreds(int value) {
        int hundreds = (value % 1000) / 100;
        return switch (hundreds) {
            case 9 -> "CM";
            case 8, 7, 6, 5 -> "D" + "C".repeat(hundreds - 5);
            case 4 -> "CD";
            case 3, 2, 1 -> "C".repeat(hundreds);
            default -> "";
        };
    }

    private static String getDozens(int value) {
        int dozens = (value % 100) / 10;
        return switch (dozens) {
            case 9 -> "XC";
            case 8, 7, 6, 5 -> "L" + "X".repeat(dozens - 5);
            case 4 -> "XL";
            case 3, 2, 1 -> "X".repeat(dozens);
            default -> "";
        };
    }

    private static String getUnits(int value) {
        int units = value % 10;
        return switch (units) {
            case 9 -> "IX";
            case 8, 7, 6, 5 -> "V" + "I".repeat(units - 5);
            case 4 -> "IV";
            case 3, 2, 1 -> "I".repeat(units);
            default -> "";
        };
    }

    private Task4() {
    }
}
