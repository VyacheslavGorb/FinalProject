package edu.gorb.musicstudio.conroller.filter;

import edu.gorb.musicstudio.conroller.command.*;
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

import static edu.gorb.musicstudio.conroller.command.CommandType.*;

public class PageAccessFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();
    private EnumMap<UserRole, List<CommandType>> availableCommands;
    private EnumSet<CommandType> guestOnlyAvailableCommands;

    @Override
    public void init(FilterConfig filterConfig) {
        availableCommands = new EnumMap<>(UserRole.class);
        guestOnlyAvailableCommands = EnumSet.of(LOGIN, GO_TO_LOGIN_PAGE, SIGN_UP, GO_TO_SIGN_UP_PAGE);
        availableCommands.put(UserRole.GUEST,
                List.of(CHANGE_LANGUAGE, LOGIN, DEFAULT, HOME_PAGE, GO_TO_LOGIN_PAGE, SIGN_UP, GO_TO_SIGN_UP_PAGE,
                        CONFIRM_EMAIL, GO_TO_SEND_EMAIL_AGAIN_PAGE, SEND_EMAIL_AGAIN, GO_TO_EMAIL_SENT_PAGE,
                        GO_TO_EMAIL_CONFIRMED_PAGE));
        availableCommands.put(UserRole.STUDENT,
                List.of(CHANGE_LANGUAGE, LOGOUT, PERSONAL_PAGE, DEFAULT, HOME_PAGE, CONFIRM_EMAIL,
                        GO_TO_SEND_EMAIL_AGAIN_PAGE, SEND_EMAIL_AGAIN, GO_TO_EMAIL_SENT_PAGE,
                        GO_TO_EMAIL_CONFIRMED_PAGE));
        availableCommands.put(UserRole.ADMIN,
                List.of(CHANGE_LANGUAGE, LOGOUT, PERSONAL_PAGE, DEFAULT, HOME_PAGE, CONFIRM_EMAIL,
                        GO_TO_SEND_EMAIL_AGAIN_PAGE, SEND_EMAIL_AGAIN, GO_TO_EMAIL_SENT_PAGE, GO_TO_EMAIL_CONFIRMED_PAGE));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpServletRequest.getSession();
        UserRole role;
        User user = (User) session.getAttribute(SessionAttribute.USER);
        if (user == null) {
            role = UserRole.GUEST;
        } else {
            role = user.getRole();
        }

        List<CommandType> availableCommandsForCurrentUser = availableCommands.get(role);
        String commandString = httpServletRequest.getParameter(RequestParameter.COMMAND);

        CommandType currentCommand = convertParameterToCommand(commandString);
        CommandType previousCommand = convertParameterToCommand((String) session.getAttribute(SessionAttribute.PREV_COMMAND));

        logger.log(Level.DEBUG, "Role: {} | Command: {}", role, currentCommand);

        if (currentCommand == GO_TO_EMAIL_CONFIRMED_PAGE
                && previousCommand != CONFIRM_EMAIL
                && previousCommand != GO_TO_EMAIL_CONFIRMED_PAGE) {
            httpServletRequest.getRequestDispatcher(PagePath.ERROR_404_PAGE).forward(servletRequest, servletResponse);
            return;
        }

        if (currentCommand == GO_TO_EMAIL_SENT_PAGE
                && previousCommand != SIGN_UP
                && previousCommand != SEND_EMAIL_AGAIN
                && previousCommand != GO_TO_EMAIL_SENT_PAGE) {
            httpServletRequest.getRequestDispatcher(PagePath.ERROR_404_PAGE).forward(servletRequest, servletResponse);
            return;
        }

        if (currentCommand != CHANGE_LANGUAGE) {
            session.setAttribute(SessionAttribute.PREV_COMMAND, currentCommand.toString().toLowerCase(Locale.ROOT));
        }

        if (role != UserRole.GUEST && guestOnlyAvailableCommands.contains(currentCommand)) {
            httpServletRequest.setAttribute(RequestAttribute.ERROR_KEY, BundleKey.ALREADY_LOGGED_IN);
            httpServletRequest.getRequestDispatcher(PagePath.ERROR_PAGE).forward(servletRequest, servletResponse);
            return;
        }

        if (!availableCommandsForCurrentUser.contains(currentCommand)) {
            httpServletRequest.setAttribute(RequestAttribute.ERROR_KEY, BundleKey.NOT_ENOUGH_RIGHTS);
            httpServletRequest.getRequestDispatcher(PagePath.ERROR_PAGE).forward(servletRequest, servletResponse);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private CommandType convertParameterToCommand(String commandString) {
        if (commandString == null) {
            return DEFAULT;
        }

        CommandType commandType;
        try {
            commandType = CommandType.valueOf(commandString.toUpperCase());
        } catch (IllegalArgumentException e) {
            commandType = DEFAULT;
        }
        return commandType;
    }
}
