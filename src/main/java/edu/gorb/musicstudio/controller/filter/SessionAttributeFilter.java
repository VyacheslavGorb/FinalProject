package edu.gorb.musicstudio.controller.filter;

import edu.gorb.musicstudio.controller.command.CommandType;
import edu.gorb.musicstudio.controller.command.RequestParameter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static edu.gorb.musicstudio.controller.command.CommandType.*;
import static edu.gorb.musicstudio.controller.command.SessionAttribute.*;

public class SessionAttributeFilter implements Filter {

    private Map<CommandType, List<String>> attributesToRemove;

    @Override
    public void init(FilterConfig filterConfig) {
        attributesToRemove = new EnumMap<>(CommandType.class);
        attributesToRemove.put(GO_TO_LOGIN_PAGE, List.of(IS_LOGIN_ERROR, EMAIL_NOT_CONFIRMED));
        attributesToRemove.put(GO_TO_SIGN_UP_PAGE, List.of(IS_SIGNUP_ERROR));
        attributesToRemove.put(GO_TO_SEND_EMAIL_AGAIN_PAGE, List.of(IS_SEND_EMAIL_AGAIN_ERROR));
        attributesToRemove.put(TEACHER_INIT, List.of(IS_TEACHER_INIT_ERROR));
        attributesToRemove.put(TEACHER_SCHEDULE, List.of(IS_TEACHER_SCHEDULE_ERROR));
        attributesToRemove.put(TEACHER_PERSONAL_INFO, List.of(IS_TEACHER_INFO_ERROR));
        attributesToRemove.put(SUBSCRIPTION_PAGE, List.of(IS_SUBSCRIPTION_ERROR));
        attributesToRemove.put(MANAGE_USERS_PAGE, List.of(IS_USER_MANAGE_ERROR, ERROR_KEY));
        attributesToRemove.put(ADD_COURSE_PAGE, List.of(IS_ADD_COURSE_ERROR));
        attributesToRemove.put(CHANGE_COURSE_PAGE, List.of(IS_CHANGE_COURSE_ERROR));
        attributesToRemove.put(CHOOSE_LESSON_DATETIME_PAGE, List.of(IS_SUBSCRIPTION_ERROR));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String commandString = httpServletRequest.getParameter(RequestParameter.COMMAND);
        CommandType currentCommand = CommandType.convertRequestParameterToCommandType(commandString);
        HttpSession session = httpServletRequest.getSession();
        attributesToRemove.forEach((command, attributes) -> {
            if (currentCommand != command) {
                attributes.forEach(session::removeAttribute);
            }
        });
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
