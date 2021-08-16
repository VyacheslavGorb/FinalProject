package edu.gorb.musicstudio.util;

public class DescriptionUtil {
    private static final int MAX_PREVIEW_CHARACTER_AMOUNT = 250;
    private static final String ELLIPSIS = "...";

    private DescriptionUtil() {
    }

    /**
     * Trims description for preview
     *
     * @param description description to be trimmed
     * @return trimmed description
     */
    public static String trimDescriptionForPreview(String description) {
        if (description.length() > MAX_PREVIEW_CHARACTER_AMOUNT) {
            description = description.substring(0, MAX_PREVIEW_CHARACTER_AMOUNT) + ELLIPSIS;
        }
        return description;
    }
}
