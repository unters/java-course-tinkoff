package edu.hw1;

import java.util.Arrays;

public class Task6 {
    private Task6() {
    }

    private final static long K = 6174;

    @SuppressWarnings("MagicNumber")
    public static int countK(long value) {
        /* value must be strictly greater than 1000.  */
        if (value <= 1000) {
            return -1;
        }

        /* value must contain at least two different digits. This check could have been done without
         * converting value to a String object, but why not?  */
        String s = Long.toString(value);
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
        return countKRecursive(value);
    }

    @SuppressWarnings("MagicNumber")
    private static int countKRecursive(long value) {
        char[] digits = Long.toString(value).toCharArray();
        Arrays.sort(digits);
        int n = digits.length;

        long a = 0;
        long b = 0;
        for (int i = 0; i < n; ++i) {
            a += (long) (digits[i] - '0') * (long) Math.pow(10, n - 1 - i);
            b += (long) (digits[n - 1 - i] - '0') * (long) Math.pow(10, n - 1 - i);
        }

        return (b - a == K) ? 1 : countKRecursive(b - a) + 1;
    }
}
