package edu.gorb.musicstudio.validator;

public class IntegerNumberValidator {
    private static final String NUMBER_REGEX = "\\d+";

    private IntegerNumberValidator() {
    }

    /**
     * Validates if <code>line</code> is integer non-negative number representation
     * @param line line to be validated
     * @return if <code>line</code> is integer non-negative number representation
     */
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
