package edu.hw1;

import java.math.BigInteger;

public class Task1 {
    private Task1() {
    }

    @SuppressWarnings({"MagicNumber", "ReturnCount"})
    public static BigInteger minutesToSeconds(String s) {
        if (s == null) {
            return new BigInteger("-1");
        }

        int delimiterIndex = s.indexOf(':');
        /* If no or multiple delimiters exist - return -1.  */
        if (delimiterIndex == -1 || delimiterIndex != s.lastIndexOf(':')) {
            return new BigInteger("-1");
        }

        /* If string starts or ends with delimiter - return -1.  */
        if (delimiterIndex == 0 || delimiterIndex == s.length() - 1) {
            return new BigInteger("-1");
        }

        /* There must be at least two charactes before the delimiter and exactly 2 characters after the delimiter.  */
        if (delimiterIndex < 2 || s.length() - delimiterIndex != 3) {
            return new BigInteger("-1");
        }

        /* If string contains any characters except for digits and colon - return -1.  */
        for (char c : s.toCharArray()) {
            if (!(Character.isDigit(c) || c == ':')) {
                return new BigInteger("-1");
            }
        }

        BigInteger minutes = new BigInteger(s.substring(0, delimiterIndex));
        /* Minutes cannot be negative.  */
        if (minutes.compareTo(new BigInteger("0")) < 0) {
            return new BigInteger("-1");
        }

        BigInteger seconds = new BigInteger(s.substring(delimiterIndex + 1));
        /* Seconds must range from 0 to 59 inclusive.  */
        if (seconds.compareTo(new BigInteger("0")) < 0 || seconds.compareTo(new BigInteger("60")) >= 0) {
            return new BigInteger("-1");
        }

        return minutes.multiply(new BigInteger("60")).add(seconds);
    }
}
