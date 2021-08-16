package edu.gorb.musicstudio.validator;

public class PageValidator {
    private static final int MIN_PAGE_NUMBER = 1;

    private PageValidator() {
    }

    /**
     * Validates if <code>pageParameter</code> is correct
     *
     * @param pageParameter page parameter
     * @param pageCount     total page count
     * @return if <code>pageParameter</code> is correct
     */
    public static boolean isValidPageParameter(String pageParameter, int pageCount) {
        if (!IntegerNumberValidator.isNonNegativeIntegerNumber(pageParameter)) {
            return false;
        }
        int pageNumber = Integer.parseInt(pageParameter);
        return pageNumber >= MIN_PAGE_NUMBER && pageNumber <= pageCount;
    }
}
