package edu.hw1;

public class Task7 {
    private Task7() {
    }

    private static final int L_BIT = 0b10000000000000000000000000000000;
    private static final int R_BIT = 0b00000000000000000000000000000001;

    @SuppressWarnings("MagicNumber")
    public static int rotateLeft(int n, int shift) {
        if (shift < 0) {
            return rotateRight(n, -1 * shift);
        }

        int nCopy = n;
        int shiftCopy = shift % 32;
        while (shiftCopy-- != 0) {
            boolean firstBinaryDigitIsOne = ((nCopy & L_BIT) == L_BIT);
            nCopy <<= 1;
            if (firstBinaryDigitIsOne) {
                nCopy |= R_BIT;
            }
        }

        return nCopy;
    }

    @SuppressWarnings("MagicNumber")
    public static int rotateRight(int n, int shift) {
        if (shift < 0) {
            return rotateLeft(n, -1 * shift);
        }

        int nCopy = n;
        int shiftCopy = shift % 32;
        while (shiftCopy-- != 0) {
            boolean firstBinaryDigitIsOne = ((nCopy & L_BIT) == L_BIT);
            boolean lastBinaryDigitIsOne = ((nCopy & R_BIT) == R_BIT);
            nCopy >>= 1;
            if (lastBinaryDigitIsOne) {
                nCopy |= L_BIT;
            } else if (firstBinaryDigitIsOne) {
                nCopy -= L_BIT;
            }
        }

        return nCopy;
    }
}
