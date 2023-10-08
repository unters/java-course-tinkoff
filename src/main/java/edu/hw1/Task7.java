package edu.hw1;

public class Task7 {
    private static final int BITS_IN_INT = 32;
    /* L_BIT is an integer in the binary representation of which all bits are set to zero except for the most
     * significant (the leftmost) bit which is set to one.  */
    private static final int L_BIT = 0b10000000_00000000_00000000_00000000;
    /* R_BIT is an integer in the binary representation of which all bits are set to zero except for the least
     * significant (the rightmost) bit which is set to one.  */
    private static final int R_BIT = 0b00000000_00000000_00000000_00000001;

    public static int rotateLeft(int n, int shift) {
        /* Left rotation with a negative shift is in essence a right rotation with a positive shift.  */
        if (shift < 0) {
            return rotateRight(n, -1 * shift);
        }

        int nCopy = n;
        /* 32-bit left shift of a 4-byte integer will result in a value equal to the initial one.  */
        int shiftCopy = shift % BITS_IN_INT;
        while (shiftCopy-- != 0) {
            boolean firstBinaryDigitIsOne = ((nCopy & L_BIT) == L_BIT);
            nCopy <<= 1;
            /* When the left shift is performed, the right bit is set to 0. If before the left shift the leftmost bit
             * was equal to 1 then after the left shift the rightmost bit must be set to 1.  */
            if (firstBinaryDigitIsOne) {
                nCopy |= R_BIT;
            }
        }

        return nCopy;
    }

    public static int rotateRight(int n, int shift) {
        /* Right rotation with a negative shift is in essence a left rotation with a positive shift.  */
        if (shift < 0) {
            return rotateLeft(n, -1 * shift);
        }

        int nCopy = n;
        /* 32-bit unsigned right shift of a 4-byte integer will result in a value equal to the initial one.  */
        int shiftCopy = shift % BITS_IN_INT;
        while (shiftCopy-- != 0) {
            boolean lastBinaryDigitIsOne = ((nCopy & R_BIT) == R_BIT);
            nCopy >>>= 1;
            /* When the unsigned right shift is performed, the left bit is set to 0. If before the unsigned right shift
             * the rightmost bit was equal to 1 then after the unsigned right shift the leftmost bit must be set
             * to 1.  */
            if (lastBinaryDigitIsOne) {
                nCopy |= L_BIT;
            }
        }

        return nCopy;
    }

    private Task7() {
    }
}
