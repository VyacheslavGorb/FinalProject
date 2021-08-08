package edu.gorb.musicstudio.util;

import org.apache.commons.text.StringEscapeUtils;

public class HtmlEscapeUtil {
    private HtmlEscapeUtil(){
    }

    public static final String  escape(String text){
        return StringEscapeUtils.escapeHtml4(text);
    }
}
