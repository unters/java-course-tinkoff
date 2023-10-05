package edu.hw1;

public class Task7 {
    /* I've decided to use byte because byte is only 8 bits long - it provides an opportunity to conveniently use
     * binary literals (which is illustrative).  */
    public static byte rotateLeft(byte n, int shift) {
        if (shift < 0)
            return rotateRight(n, -1 * shift);

        shift %= 8;
        while (shift-- != 0) {
            boolean firstBinaryDigitIsOne = ((n & 0b10000000) == 0b10000000);
            boolean lastBinaryDigitIsOne = ((n & 0b00000001) == 0b00000001);
            n <<= 1;
            if (firstBinaryDigitIsOne) {
                n = (byte) (n | 0b00000001);
            }
        }

        return n;
    }

    public static byte rotateRight(byte n, int shift) {
        if (shift < 0)
            return rotateLeft(n, -1 * shift);

        shift %= 8;
        while (shift-- != 0) {
            boolean firstBinaryDigitIsOne = ((n & 0b10000000) == 0b10000000);
            boolean lastBinaryDigitIsOne = ((n & 0b00000001) == 0b00000001);
            n >>= 1;
            if (lastBinaryDigitIsOne) {
                n = (byte) (n | 0b10000000);
            } else if (firstBinaryDigitIsOne) {
                n = (byte) (n - 0b10000000);
            }
        }

        return n;
    }
}
