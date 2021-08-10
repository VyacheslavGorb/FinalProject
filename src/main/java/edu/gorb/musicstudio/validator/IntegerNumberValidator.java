package edu.gorb.musicstudio.validator;

public class IntegerNumberValidator {
    private IntegerNumberValidator() {
    }

    public static boolean isNonNegativeIntegerNumber(String line) {
        long number;
        try {
            number = Long.parseLong(line);
        } catch (NumberFormatException e) {
            return false;
        }
        return number >= 0 ;
    }
}
