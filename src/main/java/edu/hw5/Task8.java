package edu.hw5;

public class Task8 {
    /* Regex for binary strings with odd length.  */
    public static final String REGEX_1 = "^[01]([01]{2})*$";

    /* Regex for binary strings that:
     * - begin with 0 and have odd length or,
     * - begin with 1 and have even length.  */
    public static final String REGEX_2 = "^(0([01]{2})*)|(1([01]([01]{2})*))$";

    /* Regex for binary strings number of zeros in which is divisible by 3.  */
    public static final String REGEX_3 = "^([1]*0[1]*0[1]*0[1]*)*$";

    /* Regex for binary strings that are not equal to "11" or "111" (At first I have tried to use "negative look ahead"
     * but after multiple fails I have decided to use a more reliable but longer regex).  */
    public static final String REGEX_4 = "^([01])|(0[01]{1,2})|([01][0][01])|([01]{2}[0])|([01]{4,})$";

    /* Regex for binary strings every odd digit of which is equal to 1.  */
    public static final String REGEX_5 = "^(1[01])*[1]{0,1}$";

    /* Regex for binary strings that have at least 2 zeros and at most one 1.  */
    public static final String REGEX_6 = "^(1{0,1}0{2,})|(0{2,}1{0,1})|(0{1,}1{0,1}0{1,})$";

    /* Regex for binary strings that don't have any consequent 1's.  */
    public static final String REGEX_7 = "^(0*(1(?!1))*0*)*$";

    private Task8() {
    }
}
