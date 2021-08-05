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
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String commandString = httpServletRequest.getParameter(RequestParameter.COMMAND);
        CommandType currentCommand = CommandType.convertRequestParameterToCommandType(commandString);
        HttpSession session = httpServletRequest.getSession();
        if (currentCommand != CHANGE_LANGUAGE) {
            session.setAttribute(SessionAttribute.PREV_COMMAND, currentCommand.toString().toLowerCase());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
