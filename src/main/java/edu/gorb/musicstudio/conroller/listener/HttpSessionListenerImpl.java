package edu.gorb.musicstudio.conroller.listener;

import edu.gorb.musicstudio.conroller.command.SessionAttribute;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class HttpSessionListenerImpl implements HttpSessionListener {
    private static final String DEFAULT_LOCALE = "ru";
    private static final String DEFAULT_PREVIOUS_QUERY = "/controller?command=home_page";

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        HttpSession session = sessionEvent.getSession();
        session.setAttribute(SessionAttribute.LOCALE, DEFAULT_LOCALE);
        session.setAttribute(SessionAttribute.PREVIOUS_QUERY, DEFAULT_PREVIOUS_QUERY);
    }
}
