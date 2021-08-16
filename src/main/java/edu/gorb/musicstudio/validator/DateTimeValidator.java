package edu.gorb.musicstudio.validator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class DateTimeValidator {
    private DateTimeValidator() {
    }

    /**
     * Validates if date representation is valid
     *
     * @param dateString date line
     * @return if <code>dateString</code> is valid date representation
     */
    public static boolean isValidDate(String dateString) {
        if (dateString == null) {
            return false;
        }
        try {
            LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Validates if time representation is valid
     *
     * @param timeString time line
     * @return if <code>timeString</code> is valid time representation
     */
    public static boolean isValidTime(String timeString) {
        if (timeString == null) {
            return false;
        }
        try {
            LocalTime.parse(timeString);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
