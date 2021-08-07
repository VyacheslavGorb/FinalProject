package edu.gorb.musicstudio.validator;

public class PageValidator {
    private static final int MIN_PAGE_NUMBER = 1;

    private PageValidator() {
    }

    public static boolean isValidPageParameter(String pageParameter, int pageCount) {
        int pageNumber;
        try{
            pageNumber = Integer.parseInt(pageParameter);
        }catch (NumberFormatException e){
            return false;
        }
        return pageNumber >= MIN_PAGE_NUMBER && pageNumber <= pageCount;
    }
}
