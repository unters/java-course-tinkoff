package edu.hw1;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Task3 {
    private Task3() {
    }

    public static boolean isNestable(int[] a, int[] b) {
        if (b == null || a == null) {
            throw new IllegalArgumentException("Input arrays cannot be null.");
        }

        if (a.length == 0 || b.length == 0) {
            return false;
        }

        int aMin = Integer.MAX_VALUE;
        int aMax = Integer.MIN_VALUE;
        for (int v : a) {
            aMin = min(aMin, v);
            aMax = max(aMax, v);
        }

        int bMin = Integer.MAX_VALUE;
        int bMax = Integer.MIN_VALUE;
        for (int v : b) {
            bMin = min(bMin, v);
            bMax = max(bMax, v);
        }

        return aMin > bMin && aMax < bMax;
    }
}
