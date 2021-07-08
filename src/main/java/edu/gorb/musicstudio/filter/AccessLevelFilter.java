package edu.gorb.musicstudio.filter;

import edu.gorb.musicstudio.command.CommandType;
import edu.gorb.musicstudio.command.RequestAttribute;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.entity.UserRole;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumMap;
import java.util.List;

public class AccessLevelFilter implements Filter {

    private EnumMap<UserRole, List<CommandType>> availableCommands;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        availableCommands = new EnumMap<>(UserRole.class);
        availableCommands.put(UserRole.GUEST, List.of(CommandType.LOGIN));
        availableCommands.put(UserRole.STUDENT, List.of(CommandType.LOGOUT, CommandType.PERSONAL_PAGE));
        availableCommands.put(UserRole.ADMIN, List.of(CommandType.LOGOUT, CommandType.PERSONAL_PAGE));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpServletRequest.getSession();
//        User user = session.getAttribute(RequestAttribute.USER);
    }
}
