package edu.hw1;

import java.util.Arrays;

public class Task6 {
    private final static short KAPREKARS_CONSTANT = 6174;

    @SuppressWarnings("MagicNumber")
    public static int countIterationsInKaprekarsRoutine(short value) {
        /* value must be exactly 4 digits long.  */
        if (value < 1000 || value > 9999) {
            return -1;
        }

        /* value must contain at least two different digits. This check could have been done without
         * converting value to a String object, but why not?  */
        String s = Short.toString(value);
        boolean containsAtLeastTwoDifferentCharacters = false;
        for (int i = 1; i < s.length(); ++i) {
            if (s.charAt(i) != s.charAt(i - 1)) {
                containsAtLeastTwoDifferentCharacters = true;
                break;
            }
        }

        if (!containsAtLeastTwoDifferentCharacters) {
            return -1;
        }

        /* Task statement asks to write a recursive function. I decided to separate a solution into two functions
         * to avoid overhead caused by input value being checked for being valid on each step - it should be checked for
         * being valid only once.  */
        return countIterationsInKaprekarsRoutingRecursive(value);
    }

    @SuppressWarnings("MagicNumber")
    private static int countIterationsInKaprekarsRoutingRecursive(short value) {
        short temp = value;
        byte[] digits = new byte[4];
        for (int i = 0; i < 4; ++i) {
            digits[i] = (byte) (temp % 10);
            temp /= 10;
        }

        Arrays.sort(digits);

        short a = 0;
        short b = 0;
        for (int i = 0; i < 4; ++i) {
            a += (short) (digits[i] * Math.pow(10, 3 - i));
            b += (short) (digits[3 - i] * Math.pow(10, 3 - i));
        }

        return (b - a == KAPREKARS_CONSTANT) ? 1 : countIterationsInKaprekarsRoutingRecursive((short) (b - a)) + 1;
    }

    private Task6() {
    }
}
