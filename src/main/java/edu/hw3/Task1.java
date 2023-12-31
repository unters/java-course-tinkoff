package edu.hw3;

public class Task1 {
    public static String encryptUsingAtbash(String s) {
        if (s == null) {
            throw new IllegalArgumentException("s cannot be null");
        }

        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; ++i) {
            char c = chars[i];
            /* According to task statement the encryption must be applied to latin letters only - all other characters
             * must stay the same.  */
            if (!Character.UnicodeBlock.of(c).equals(Character.UnicodeBlock.BASIC_LATIN)) {
                continue;
            } else if (Character.isLowerCase(c)) {
                chars[i] = (char) ('z' - (c - 'a'));
            } else if (Character.isUpperCase(c)) {
                chars[i] = (char) ('Z' - (c - 'A'));
            }
        }

        return new String(chars);
    }

    private Task1() {
    }
}
