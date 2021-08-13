package edu.gorb.musicstudio.controller.listener;

import edu.gorb.musicstudio.controller.command.SessionAttribute;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class HttpSessionListenerImpl implements HttpSessionListener {
    private static final String DEFAULT_LOCALE = "ru";
    private static final String DEFAULT_PREVIOUS_QUERY = "/controller?command=home_page";
    private static final boolean DEFAULT_DESCRIPTION_EXISTS_VALUE = false;

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        HttpSession session = sessionEvent.getSession();
        session.setAttribute(SessionAttribute.LOCALE, DEFAULT_LOCALE);
        session.setAttribute(SessionAttribute.PREVIOUS_QUERY, DEFAULT_PREVIOUS_QUERY);
        session.setAttribute(SessionAttribute.DESCRIPTION_EXISTS, DEFAULT_DESCRIPTION_EXISTS_VALUE);
    }
}
