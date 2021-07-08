package edu.gorb.musicstudio.filter;

import edu.gorb.musicstudio.command.CommandType;
import edu.gorb.musicstudio.command.RequestParameter;
import edu.gorb.musicstudio.command.SessionAttribute;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.entity.UserRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumMap;
import java.util.List;

import static edu.gorb.musicstudio.command.CommandType.*;

public class AccessLevelFilter implements Filter {
    private EnumMap<UserRole, List<CommandType>> availableCommands;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        availableCommands = new EnumMap<>(UserRole.class);
        availableCommands.put(UserRole.GUEST, List.of(LOGIN, DEFAULT));
        availableCommands.put(UserRole.STUDENT, List.of(LOGOUT, PERSONAL_PAGE, DEFAULT));
        availableCommands.put(UserRole.ADMIN, List.of(LOGOUT, PERSONAL_PAGE, DEFAULT));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpServletRequest.getSession();
        UserRole role;
        Object attributeObject = session.getAttribute(SessionAttribute.USER);
        if (session.getAttribute(SessionAttribute.USER) == null || !(attributeObject instanceof User)) {
            role = UserRole.GUEST;
        } else {
            User user = (User) session.getAttribute(SessionAttribute.USER);
            role = user.getRole();
        }
        CommandType commandType = extractRequestCommandType(httpServletRequest);

        List<CommandType> availableCommandsForCurrentUser = availableCommands.get(role);
        if (!availableCommandsForCurrentUser.contains(commandType)) {
            httpServletRequest.getRequestDispatcher("error page").forward(servletRequest, servletResponse); //TODO page
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private CommandType extractRequestCommandType(HttpServletRequest httpServletRequest) {
        CommandType commandType;
        String commandString = httpServletRequest.getParameter(RequestParameter.COMMAND);
        if (commandString == null) {
            return DEFAULT;
        }

        try {
            commandType = CommandType.valueOf(commandString.toUpperCase());
        } catch (IllegalArgumentException e) {
            commandType = DEFAULT;
        }
        return commandType;
    }
}
