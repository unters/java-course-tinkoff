package edu.hw5;

public class Task7 {
    /* Regex for binary strings that contain at least 3 characters and the third character is zero.  */
    public static final String REGEX_1 = "[01]{2}0[01]*";

    /* Regex for binary strings that begin and end with the same character.  */
    public static final String REGEX_2 = "(0)|(1)|(0[01]*0)|(1[01]*1)";

    /* Regex for binary strings that are not shorter than 1 and not longer than 3 characters.  */
    public static final String REGEX_3 = "[01]{1,3}";

    private Task7() {
    }
}
