package edu.gorb.musicstudio.controller.command.impl.admin;

import edu.gorb.musicstudio.controller.command.*;
import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.entity.UserRole;
import edu.gorb.musicstudio.entity.UserStatus;
import edu.gorb.musicstudio.entity.dto.LessonScheduleDto;
import edu.gorb.musicstudio.entity.dto.SubscriptionDto;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.*;
import edu.gorb.musicstudio.validator.IntegerNumberValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class DeactivateUserCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        String userIdParameter = request.getParameter(RequestParameter.USER_ID);
        if (!IntegerNumberValidator.isNonNegativeIntegerNumber(userIdParameter)) {
            return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        UserService userService = ServiceProvider.getInstance().getUserService();
        LessonScheduleService lessonScheduleService = ServiceProvider.getInstance().getLessonScheduleService();
        CourseService courseService = ServiceProvider.getInstance().getCourseService();
        SubscriptionService subscriptionService = ServiceProvider.getInstance().getScheduleService();
        HttpSession session = request.getSession();
        User currentAdminUser = (User) session.getAttribute(SessionAttribute.USER);
        try {
            long userId = Long.parseLong(userIdParameter);
            Optional<User> optionalUser = userService.findUserById(userId);
            if (optionalUser.isEmpty()) {
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }

            User user = optionalUser.get();

            if (user.getRole() == UserRole.ADMIN) {
                if (currentAdminUser.getId() == userId) {
                    session.setAttribute(SessionAttribute.IS_USER_MANAGE_ERROR, true);
                    session.setAttribute(SessionAttribute.ERROR_KEY, BundleKey.CANT_DEACTIVATE_YOURSELF);
                    return new CommandResult(PagePath.MANAGE_USERS_REDIRECT, CommandResult.RoutingType.REDIRECT);
                }
            } else if (user.getRole() == UserRole.TEACHER) {
                List<LessonScheduleDto> scheduleDtos = lessonScheduleService.findActiveFutureSchedulesByTeacherId(userId);
                if (!scheduleDtos.isEmpty()) {
                    session.setAttribute(SessionAttribute.IS_USER_MANAGE_ERROR, true);
                    session.setAttribute(SessionAttribute.ERROR_KEY, BundleKey.TEACHER_HAS_LESSONS);
                    return new CommandResult(PagePath.MANAGE_USERS_REDIRECT, CommandResult.RoutingType.REDIRECT);
                }
                List<Course> teacherCourses = courseService.findActiveCoursesByTeacherId(userId);
                if (!teacherCourses.isEmpty()) {
                    session.setAttribute(SessionAttribute.IS_USER_MANAGE_ERROR, true);
                    session.setAttribute(SessionAttribute.ERROR_KEY, BundleKey.TEACHER_HAS_COURSES);
                    return new CommandResult(PagePath.MANAGE_USERS_REDIRECT, CommandResult.RoutingType.REDIRECT);
                }
            } else if (user.getRole() == UserRole.STUDENT) {
                List<SubscriptionDto> studentSubscriptions =
                        subscriptionService.findContinuingActiveStudentSubscriptions(userId);
                if (!studentSubscriptions.isEmpty()) {
                    session.setAttribute(SessionAttribute.IS_USER_MANAGE_ERROR, true);
                    session.setAttribute(SessionAttribute.ERROR_KEY, BundleKey.STUDENT_HAS_SUBSCRIPTIONS);
                    return new CommandResult(PagePath.MANAGE_USERS_REDIRECT, CommandResult.RoutingType.REDIRECT);
                }
            }
            userService.updateUserStatus(userId, UserStatus.INACTIVE);
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        return new CommandResult(PagePath.MANAGE_USERS_REDIRECT, CommandResult.RoutingType.REDIRECT);
    }
}
