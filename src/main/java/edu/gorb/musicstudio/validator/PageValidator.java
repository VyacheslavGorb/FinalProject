package edu.gorb.musicstudio.validator;

public class PageValidator {
    private static final int MIN_PAGE_NUMBER = 1;

    private PageValidator() {
    }

    public static boolean isValidPageParameter(String pageParameter, int pageCount) {
        if (!IntegerNumberValidator.isIntegerNumber(pageParameter)) {
            return false;
        }
        int pageNumber = Integer.parseInt(pageParameter);
        return pageNumber >= MIN_PAGE_NUMBER && pageNumber <= pageCount;
    }
}
