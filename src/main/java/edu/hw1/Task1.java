package edu.hw1;

import java.util.regex.Pattern;

public class Task1 {
    /* Video length is bounded on top by the rough estimation of the age of the universe.  */
    private static final String TIMESTAMP_REGEX = "\\d{2,16}:[0-5][0-9]";
    private static final long SECONDS_IN_MINUTE = 60L;

    public static long convertTimestampToSeconds(String timestamp) {
        if (timestamp == null || !Pattern.matches(TIMESTAMP_REGEX, timestamp)) {
            return -1L;
        }

        int delimiterIndex = timestamp.indexOf(':');
        long minutes = Long.parseLong(timestamp.substring(0, delimiterIndex));
        short seconds = Short.parseShort(timestamp.substring(delimiterIndex + 1));
        return minutes * SECONDS_IN_MINUTE + seconds;
    }

    private Task1() {
    }
}
