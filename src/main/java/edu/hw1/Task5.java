package edu.hw1;

public class Task5 {
    /* Let's assume that we can build descendant value only for integers with even number of digits.  */
    public static boolean isPalindromeDescendant(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("value cannot be negative.");
        }

        String s = Integer.toString(value);
        do {
            if (isPalindrome(s)) {
                return true;
            }

            if (s.length() % 2 == 1) {
                return false;
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); i += 2) {
                sb.append(Integer.toString(s.charAt(i) - '0' + s.charAt(i + 1) - '0'));
            }

            s = sb.toString();
        } while (s.length() != 1);

        return false;
    }

    private static boolean isPalindrome(String s) {
        int n = s.length();
        for (int i = 0; i < n / 2; ++i) {
            if (s.charAt(i) != s.charAt(n - 1 - i)) {
                return false;
            }
        }

        return true;
    }

    private Task5() {
    }
}
