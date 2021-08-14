package edu.gorb.musicstudio.util;

import org.apache.commons.text.StringEscapeUtils;

public class HtmlEscapeUtil {
    private HtmlEscapeUtil() {
    }

    /**
     * Escapes text from html symbols
     *
     * @param text text to be escaped
     * @return escaped text
     */
    public static String escape(String text) {
        return StringEscapeUtils.escapeHtml4(text);
    }
}
