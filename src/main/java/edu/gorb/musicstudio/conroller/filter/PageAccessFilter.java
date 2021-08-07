package edu.gorb.musicstudio.conroller.filter;

import edu.gorb.musicstudio.conroller.command.*;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.entity.UserRole;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;

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
                        CONFIRM_EMAIL, GO_TO_SEND_EMAIL_AGAIN_PAGE, SEND_EMAIL_AGAIN,
                        COURSES, COURSE_PAGE));
        availableCommands.put(UserRole.STUDENT,
                List.of(CHANGE_LANGUAGE, LOGOUT, PERSONAL_PAGE, DEFAULT, HOME_PAGE, CONFIRM_EMAIL,
                        GO_TO_SEND_EMAIL_AGAIN_PAGE, SEND_EMAIL_AGAIN,
                        COURSES, COURSE_PAGE));
        availableCommands.put(UserRole.ADMIN,
                List.of(CHANGE_LANGUAGE, LOGOUT, PERSONAL_PAGE, DEFAULT, HOME_PAGE, CONFIRM_EMAIL,
                        GO_TO_SEND_EMAIL_AGAIN_PAGE, SEND_EMAIL_AGAIN,
                        COURSES, COURSE_PAGE));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String commandString = httpServletRequest.getParameter(RequestParameter.COMMAND);
        CommandType currentCommand = CommandType.convertRequestParameterToCommandType(commandString);

        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        UserRole role;

        if (user == null) {
            role = UserRole.GUEST;
        } else {
            role = user.getRole();
        }

        List<CommandType> availableCommandsForCurrentUser = availableCommands.get(role);

        logger.log(Level.DEBUG, "Role: {} | Command: {}", role, currentCommand);

        if (role != UserRole.GUEST && guestOnlyAvailableCommands.contains(currentCommand)) {
            session.setAttribute(SessionAttribute.ERROR_KEY, BundleKey.ALREADY_LOGGED_IN);
            httpServletResponse.sendRedirect(PagePath.ERROR_PAGE);
            return;
        }

        if (!availableCommandsForCurrentUser.contains(currentCommand)) {
            session.setAttribute(SessionAttribute.ERROR_KEY, BundleKey.NOT_ENOUGH_RIGHTS);
            httpServletResponse.sendRedirect(PagePath.ERROR_PAGE);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
