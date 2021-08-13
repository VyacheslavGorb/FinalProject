package edu.gorb.musicstudio.controller.command.impl;

import edu.gorb.musicstudio.controller.command.Command;
import edu.gorb.musicstudio.controller.command.CommandResult;
import edu.gorb.musicstudio.controller.command.RequestParameter;
import edu.gorb.musicstudio.controller.command.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangeLanguageCommand implements Command {
    private static final String RUSSIAN_LOCALE = "ru";
    private static final String ENGLISH_LOCALE = "en";

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String previousQuery = (String) session.getAttribute(SessionAttribute.PREVIOUS_QUERY);
        String language = request.getParameter(RequestParameter.LANGUAGE);

        if (language != null
                && (language.equals(RUSSIAN_LOCALE) || language.equals(ENGLISH_LOCALE))) {
            session.setAttribute(SessionAttribute.LOCALE, language);
        }
        return new CommandResult(previousQuery, CommandResult.RoutingType.REDIRECT);
    }
}