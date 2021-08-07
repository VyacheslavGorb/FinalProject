package edu.gorb.musicstudio.conroller.filter;

import edu.gorb.musicstudio.conroller.command.CommandType;
import edu.gorb.musicstudio.conroller.command.RequestParameter;
import edu.gorb.musicstudio.conroller.command.SessionAttribute;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static edu.gorb.musicstudio.conroller.command.CommandType.CHANGE_LANGUAGE;

public class PreviousCommandFilter implements Filter {
    private static final String GET_METHOD = "GET";
    private static final String QUESTION_MARK = "?";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws ServletException, IOException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        if (httpServletRequest.getMethod().equals(GET_METHOD)) {

            String contextPath = httpServletRequest.getContextPath();
            String uri = httpServletRequest.getRequestURI();
            int startIndex = uri.indexOf(contextPath) + contextPath.length();
            String substring = uri.substring(startIndex);
            String queryParameters = httpServletRequest.getQueryString();
            String queryLine = queryParameters == null ? substring : substring + QUESTION_MARK + queryParameters;

            String commandString = httpServletRequest.getParameter(RequestParameter.COMMAND);
            CommandType currentCommand = CommandType.convertRequestParameterToCommandType(commandString);
            if (currentCommand != CHANGE_LANGUAGE) {
                HttpSession session = httpServletRequest.getSession();
                session.setAttribute(SessionAttribute.PREVIOUS_QUERY, queryLine);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
