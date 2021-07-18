package edu.gorb.musicstudio.command.impl;

import edu.gorb.musicstudio.command.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangeLanguageCommand implements Command {
    private static final String REDIRECT_TEMPLATE = "/controller?command=";
    private static final String RUSSIAN_LOCALE = "ru_RU";
    private static final String ENGLISH_LOCALE = "en_EN";

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String command = (String) session.getAttribute(SessionAttribute.PREV_COMMAND);
        String language = request.getParameter(RequestParameter.LANGUAGE);
        if (language != null
                && (language.equals(RUSSIAN_LOCALE) || language.equals(ENGLISH_LOCALE))) {
            session.setAttribute(SessionAttribute.LOCALE, language);
        }
        if (command == null) {
            return new CommandResult(PagePath.HOME_PAGE_REDIRECT, CommandResult.RoutingType.REDIRECT);
        }
        return new CommandResult(REDIRECT_TEMPLATE + command, CommandResult.RoutingType.REDIRECT);
    }
}

//TODO Error or do nothing
