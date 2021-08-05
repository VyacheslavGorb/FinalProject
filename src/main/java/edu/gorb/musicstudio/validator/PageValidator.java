package edu.gorb.musicstudio.validator;

public class PageValidator {
    private static final int MIN_PAGE_NUMBER = 1;

    private PageValidator() {
    }

    public static boolean isValidPageNumber(int pageNumber, int pageCount) {
        return pageNumber >= MIN_PAGE_NUMBER && pageNumber <= pageCount;
    }
}
