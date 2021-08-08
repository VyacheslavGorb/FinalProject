package edu.gorb.musicstudio.validator;

public class IntegerNumberValidator {
    private IntegerNumberValidator() {
    }

    public static boolean isIntegerNumber(String line) {
        boolean result = true;
        try {
            Long.parseLong(line);
        } catch (NumberFormatException e) {
            result = false;
        }
        return result;
    }
}
