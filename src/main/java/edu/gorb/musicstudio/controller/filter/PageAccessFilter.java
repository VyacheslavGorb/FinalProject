package edu.gorb.musicstudio.controller.filter;

import edu.gorb.musicstudio.controller.command.*;
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

import static edu.gorb.musicstudio.controller.command.CommandType.*;

/**
 * Checks for command availability for current user
 */
public class PageAccessFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();
    private EnumMap<UserRole, List<CommandType>> availableCommands;
    private EnumSet<CommandType> guestOnlyAvailableCommands;
    private EnumSet<CommandType> teacherWithDescriptionOnlyAvailableCommands;
    private EnumSet<CommandType> teacherWithoutDescriptionOnlyAvailableCommands;

    /**
     * Initializes commands for each role
     *
     * @param filterConfig Not used
     */
    @Override
    public void init(FilterConfig filterConfig) {
        availableCommands = new EnumMap<>(UserRole.class);
        guestOnlyAvailableCommands = EnumSet.of(LOGIN, GO_TO_LOGIN_PAGE, SIGN_UP, GO_TO_SIGN_UP_PAGE);

        teacherWithDescriptionOnlyAvailableCommands = EnumSet.of(TEACHER_LESSON_SCHEDULE,
                TEACHER_SCHEDULE, ALTER_TEACHER_SCHEDULE, TEACHER_PERSONAL_INFO, UPDATE_TEACHER_DESCRIPTION);

        teacherWithoutDescriptionOnlyAvailableCommands = EnumSet.of(TEACHER_INIT, SEND_TEACHER_INIT_DESCRIPTION);

        availableCommands.put(UserRole.GUEST,
                List.of(CHANGE_LANGUAGE, LOGIN, DEFAULT, HOME_PAGE, GO_TO_LOGIN_PAGE, SIGN_UP, GO_TO_SIGN_UP_PAGE,
                        CONFIRM_EMAIL, GO_TO_SEND_EMAIL_AGAIN_PAGE, SEND_EMAIL_AGAIN,
                        COURSES, COURSE_PAGE, TEACHERS, TEACHER_PAGE));

        availableCommands.put(UserRole.STUDENT,
                List.of(CHANGE_LANGUAGE, LOGOUT, DEFAULT, HOME_PAGE,
                        COURSES, COURSE_PAGE, POST_COMMENT, TEACHERS, TEACHER_PAGE,
                        SUBSCRIPTION_PAGE, SEND_SUBSCRIPTION_REQUEST, PERSONAL_SUBSCRIPTIONS,
                        CHOOSE_LESSON_DATETIME_PAGE, CHOOSE_LESSON_DATETIME, STUDENT_LESSON_SCHEDULE,
                        STUDENT_CANCEL_LESSON));

        availableCommands.put(UserRole.TEACHER,
                List.of(CHANGE_LANGUAGE, LOGOUT, DEFAULT, HOME_PAGE,
                        COURSES, COURSE_PAGE, TEACHERS, TEACHER_PAGE, TEACHER_INIT, TEACHER_LESSON_SCHEDULE,
                        SEND_TEACHER_INIT_DESCRIPTION, TEACHER_SCHEDULE, ALTER_TEACHER_SCHEDULE, TEACHER_PERSONAL_INFO,
                        UPDATE_TEACHER_DESCRIPTION));

        availableCommands.put(UserRole.ADMIN,
                List.of(CHANGE_LANGUAGE, LOGOUT, DEFAULT, HOME_PAGE,
                        COURSES, COURSE_PAGE, TEACHERS, TEACHER_PAGE, MANAGE_USERS_PAGE, ACTIVATE_USER, DEACTIVATE_USER,
                        ALL_LESSONS_PAGE, ADMIN_CANCEL_LESSON, ALL_SUBSCRIPTIONS_PAGE, ADMIN_APPROVE_SUBSCRIPTION,
                        ADMIN_CANCEL_SUBSCRIPTION, ALL_COURSES_PAGE, ADD_COURSE_PAGE, ADD_COURSE, ACTIVATE_COURSE,
                        DEACTIVATE_COURSE, CHANGE_COURSE_PAGE, CHANGE_COURSE, MANAGE_TEACHERS_COURSE_PAGE,
                        ADD_TEACHER_TO_COURSE, REMOVE_TEACHER_FROM_COURSE, MANAGE_COMMENTS, REMOVE_COMMENT));
    }

    /**
     * Checks for command availability for current user
     *
     * @param servletRequest  servlet request
     * @param servletResponse servlet response
     * @param filterChain     filter chain
     * @throws IOException      is thrown when {@link HttpServletResponse#sendRedirect(String)} exception occurs
     * @throws ServletException is thrown when {@link FilterChain#doFilter(ServletRequest, ServletResponse)} exception occurs
     */
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
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + PagePath.ERROR_PAGE);
            return;
        }

        if (!availableCommandsForCurrentUser.contains(currentCommand)) {
            session.setAttribute(SessionAttribute.ERROR_KEY, BundleKey.NOT_ENOUGH_RIGHTS);
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + PagePath.ERROR_PAGE);
            return;
        }

        if (role == UserRole.TEACHER) {
            boolean descriptionExists = (boolean) session.getAttribute(SessionAttribute.DESCRIPTION_EXISTS);
            if (teacherWithDescriptionOnlyAvailableCommands.contains(currentCommand) && !descriptionExists) {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath()
                        + PagePath.TEACHER_INIT_PAGE_REDIRECT);
                return;
            }
            if (teacherWithoutDescriptionOnlyAvailableCommands.contains(currentCommand) && descriptionExists) {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath()
                        + PagePath.TEACHER_LESSON_SCHEDULE_PAGE_REDIRECT);
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
