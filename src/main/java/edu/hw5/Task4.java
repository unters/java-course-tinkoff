package edu.hw5;

import java.util.regex.Pattern;

public class Task4 {
    /* Password must contain at least one of the characters from the following list: "~ ! @ # $ % ^ & * |". Password
     * can also contain english letters in lower or upper case, digits and underscores.  */
    private static final String PASSWORD_REGEX = "^(\\w*[~!@#$%^&*|]+\\w*)+$";

    private static final String NULL_STRING_GIVEN_MESSAGE = "password cannot be null";

    public static boolean isPasswordValid(String password) {
        if (password == null) {
            throw new IllegalArgumentException(NULL_STRING_GIVEN_MESSAGE);
        }

        return Pattern.matches(PASSWORD_REGEX, password);
    }

    private Task4() {
    }
}
