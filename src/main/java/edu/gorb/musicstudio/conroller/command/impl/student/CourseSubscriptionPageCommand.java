package edu.gorb.musicstudio.conroller.command.impl.student;

import edu.gorb.musicstudio.conroller.command.*;
import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.CourseService;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.model.service.SubscriptionService;
import edu.gorb.musicstudio.model.service.UserService;
import edu.gorb.musicstudio.validator.IntegerNumberValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class CourseSubscriptionPageCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String courseIdParameter = request.getParameter(RequestParameter.COURSE_ID);
        if (!IntegerNumberValidator.isNonNegativeIntegerNumber(courseIdParameter)) {
            return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        long courseId = Long.parseLong(courseIdParameter);
        UserService userService = ServiceProvider.getInstance().getUserService();
        CourseService courseService = ServiceProvider.getInstance().getCourseService();
        SubscriptionService subscriptionService = ServiceProvider.getInstance().getScheduleService();
        HttpSession session = request.getSession();
        User student = (User) session.getAttribute(SessionAttribute.USER);
        int maxAvailableLessonCount;
        try {
            if (!subscriptionService.isNewCourseSubscriptionAvailable(student.getId(), courseId)) {
                session.setAttribute(SessionAttribute.INFO_KEY, BundleKey.ALREADY_SUBSCRIBED);
                return new CommandResult(PagePath.INFO_PAGE, CommandResult.RoutingType.REDIRECT);
            }
            Optional<Course> courseOptional = courseService.findCourseById(courseId);
            if (courseOptional.isEmpty()) {
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }
            int totalFreeSlotsCount = 0;
            List<User> teachersForCourse = userService.findTeachersForCourse(courseId);
            for (User teacher : teachersForCourse) {
                totalFreeSlotsCount += userService.findTeacherFreeSlotCountForNextMonth(teacher.getId());
            }
            maxAvailableLessonCount = subscriptionService.calcMaxLessonPerSubscriptionCount(totalFreeSlotsCount);
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        request.setAttribute(RequestAttribute.COURSE, courseId);
        request.setAttribute(RequestAttribute.MAX_AVAILABLE_LESSON_COUNT, maxAvailableLessonCount);
        return new CommandResult(PagePath.SUBSCRIPTION_PAGE, CommandResult.RoutingType.FORWARD);
    }
}
