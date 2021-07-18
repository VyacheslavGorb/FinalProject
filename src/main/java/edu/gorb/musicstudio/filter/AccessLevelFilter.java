package edu.gorb.musicstudio.filter;

import edu.gorb.musicstudio.command.*;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.entity.UserRole;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;

import static edu.gorb.musicstudio.command.CommandType.*;

public class AccessLevelFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();
    private EnumMap<UserRole, List<CommandType>> availableCommands;
    private EnumSet<CommandType> guestOnlyAvailableCommands;

    @Override
    public void init(FilterConfig filterConfig) {
        availableCommands = new EnumMap<>(UserRole.class);
        guestOnlyAvailableCommands = EnumSet.of(LOGIN, GO_TO_LOGIN_PAGE, SIGN_UP, GO_TO_SIGN_UP_PAGE);
        availableCommands.put(UserRole.GUEST,
                List.of(CHANGE_LANGUAGE, LOGIN, DEFAULT, HOME_PAGE, GO_TO_LOGIN_PAGE, SIGN_UP, GO_TO_SIGN_UP_PAGE));
        availableCommands.put(UserRole.STUDENT,
                List.of(CHANGE_LANGUAGE, LOGOUT, PERSONAL_PAGE, DEFAULT, HOME_PAGE));
        availableCommands.put(UserRole.ADMIN,
                List.of(CHANGE_LANGUAGE, LOGOUT, PERSONAL_PAGE, DEFAULT, HOME_PAGE));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpServletRequest.getSession();
        UserRole role;
        if (session.getAttribute(SessionAttribute.USER) == null) { // TODO is type check required
            role = UserRole.GUEST;
        } else {
            User user = (User) session.getAttribute(SessionAttribute.USER);
            role = user.getRole();
        }

        List<CommandType> availableCommandsForCurrentUser = availableCommands.get(role);
        CommandType commandType = extractRequestCommandType(httpServletRequest);

        if (commandType != CHANGE_LANGUAGE) {
            session.setAttribute(SessionAttribute.PREV_COMMAND, commandType.toString().toLowerCase(Locale.ROOT));
        }

        logger.log(Level.DEBUG, "Role: {} | Command: {}", role, commandType);
        if (role != UserRole.GUEST && guestOnlyAvailableCommands.contains(commandType)) {
            httpServletRequest.setAttribute(RequestAttribute.ERROR_KEY, "error.already_logged_in");
            httpServletRequest.getRequestDispatcher(PagePath.ERROR_PAGE).forward(servletRequest, servletResponse);
            return;
        }

        if (!availableCommandsForCurrentUser.contains(commandType)) {
            httpServletRequest.setAttribute(RequestAttribute.ERROR_KEY, "error.not_enough_rights");
            httpServletRequest.getRequestDispatcher(PagePath.ERROR_PAGE).forward(servletRequest, servletResponse);
            return;
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
