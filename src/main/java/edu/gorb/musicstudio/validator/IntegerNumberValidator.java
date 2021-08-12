package edu.gorb.musicstudio.validator;

public class IntegerNumberValidator {
    private static final String NUMBER_REGEX = "\\d+";

    private IntegerNumberValidator() {
    }

    public static boolean isNonNegativeIntegerNumber(String line) {
        if(line == null || !line.matches(NUMBER_REGEX)){
            return false;
        }
        long number;
        try {
            number = Long.parseLong(line);
        } catch (NumberFormatException e) {
            return false;
        }
        return number >= 0 ;
    }
}
