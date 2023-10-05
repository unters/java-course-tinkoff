package edu.hw1;

public class Task7 {
    private Task7() {
    }

    /* I've decided to use byte because byte is only 8 bits long - it provides an opportunity to conveniently use
     * binary literals (which is illustrative).  */

    @SuppressWarnings("MagicNumber")
    public static byte rotateLeft(byte n, int shift) {
        if (shift < 0) {
            return rotateRight(n, -1 * shift);
        }

        byte nCopy = n;
        int shiftCopy = shift % 8;
        while (shiftCopy-- != 0) {
            boolean firstBinaryDigitIsOne = ((nCopy & 0b10000000) == 0b10000000);
            boolean lastBinaryDigitIsOne = ((nCopy & 0b00000001) == 0b00000001);
            nCopy <<= 1;
            if (firstBinaryDigitIsOne) {
                nCopy = (byte) (nCopy | 0b00000001);
            }
        }

        return nCopy;
    }

    @SuppressWarnings("MagicNumber")
    public static byte rotateRight(byte n, int shift) {
        if (shift < 0) {
            return rotateLeft(n, -1 * shift);
        }

        byte nCopy = n;
        int shiftCopy = shift % 8;
        while (shiftCopy-- != 0) {
            boolean firstBinaryDigitIsOne = ((nCopy & 0b10000000) == 0b10000000);
            boolean lastBinaryDigitIsOne = ((nCopy & 0b00000001) == 0b00000001);
            nCopy >>= 1;
            if (lastBinaryDigitIsOne) {
                nCopy = (byte) (nCopy | 0b10000000);
            } else if (firstBinaryDigitIsOne) {
                nCopy = (byte) (nCopy - 0b10000000);
            }
        }

        return nCopy;
    }
}
