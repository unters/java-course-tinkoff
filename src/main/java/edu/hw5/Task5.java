package edu.hw5;

import java.util.regex.Pattern;

public class Task5 {
    /* The following table contains description of licence number plates in Russian Federation:
     * '#' is used for a single digit,
     * `Б` - for one of 12 letters used in regular russian licence plates (А, В, Е, К, М, Н, О, Р, С, Т, У and Х).
     *
     * See more: https://en.wikipedia.org/wiki/Vehicle_registration_plates_of_Russia
     *
     * Number       Description
     * Б###ББ ##    Private vehicles
     * A#### ##     Traffic police vehicles
     * У#### ##     Patrol vehicles
     * O#### ##     Police guard dog service vehicles
     * ####ББ ##    Military vehicles
     * ББ#### ##    Military trailers
     * ББ### ##     Taxi
     * ББ#### ##    Trailers
     * ББ###Б ##    Temporary and transit vehicles
     * TББ### ##    Temporary and transit (exported) vehicles
     * ###CD# ##    Diplomatic vehicles (ambassadors)
     * ###D### ##   Diplomatic vehicles (rank-and-file diplomats)
     * ###T### ##   Diplomatic vehicles (administrative and technical staff of embassies, consulates or international
     *              organisations
     */
    private static final String LICENCE_PLATE_REGEX = "^([АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{2}\\d{2})|(А\\d{6})|"
        + "(У\\d{6})|(О\\d{6})|(\\d{4}[АВЕКМНОРСТУХ]{2}\\d{2})|([АВЕКМНОРСТУХ]{2}\\d{5,6})|([АВЕКМНОРСТУХ]{2}\\d{3}"
        + "[АВЕКМНОРСТУХ]\\d{2})|(Т[АВЕКМНОРСТУХ]{2}\\d{5})|(\\d{3}CD\\d{3})|(\\d{3}[DT]\\d{5})$";

    private static final String LICENCE_PLATE_IS_NULL_MESSAGE = "licencePlate cannot be null";

    public static boolean isLicencePlateValid(String licencePlate) {
        if (licencePlate == null) {
            throw new IllegalArgumentException(LICENCE_PLATE_IS_NULL_MESSAGE);
        }

        return Pattern.matches(LICENCE_PLATE_REGEX, licencePlate);
    }

    private Task5() {
    }
}
