package edu.hw5;

import java.util.regex.Pattern;

public class Task6 {
    public static boolean isSubstring(String s, String t) {
        if (s == null || t == null) {
            throw new IllegalArgumentException("neither s nor t can be null");
        }

        return Pattern.matches("^.*" + Pattern.quote(t) + ".*$", s);
    }

    private Task6() {
    }
}
