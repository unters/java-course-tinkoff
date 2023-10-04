package edu.hw1;

import org.jetbrains.annotations.NotNull;

public class Task4 {
    public static String fixString(@NotNull String s) {
        int n = s.length();
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < n - n % 2; i += 2) {
            sb.setCharAt(i, s.charAt(i + 1));
            sb.setCharAt(i + 1, s.charAt(i));
        }

        return sb.toString();
    }
}
