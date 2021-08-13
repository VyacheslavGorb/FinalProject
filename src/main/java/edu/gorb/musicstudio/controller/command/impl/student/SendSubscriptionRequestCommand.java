package edu.gorb.musicstudio.controller.command.impl.student;

import edu.gorb.musicstudio.controller.command.*;
import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.entity.Subscription;
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

public class SendSubscriptionRequestCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        String courseIdParameter = request.getParameter(RequestParameter.COURSE_ID);
        String lessonCountParameter = request.getParameter(RequestParameter.LESSON_COUNT);
        if (!IntegerNumberValidator.isNonNegativeIntegerNumber(courseIdParameter)) {
            return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        long courseId = Long.parseLong(courseIdParameter);

        HttpSession session = request.getSession();
        if(!IntegerNumberValidator.isNonNegativeIntegerNumber(lessonCountParameter)){
            session.setAttribute(SessionAttribute.IS_SUBSCRIPTION_ERROR, true);
            return new CommandResult(PagePath.SUBSCRIPTION_PAGE_REDIRECT + courseId,
                    CommandResult.RoutingType.REDIRECT);
        }

        int lessonCount = Integer.parseInt(lessonCountParameter);
        UserService userService = ServiceProvider.getInstance().getUserService();
        CourseService courseService = ServiceProvider.getInstance().getCourseService();
        SubscriptionService subscriptionService = ServiceProvider.getInstance().getScheduleService();
        User student = (User) session.getAttribute(SessionAttribute.USER);
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
            int maxAvailableLessonCount = subscriptionService.calcMaxLessonPerSubscriptionCount(totalFreeSlotsCount);
            if (maxAvailableLessonCount < lessonCount) {
                session.setAttribute(SessionAttribute.IS_SUBSCRIPTION_ERROR, true);
                return new CommandResult(PagePath.SUBSCRIPTION_PAGE_REDIRECT + courseId,
                        CommandResult.RoutingType.REDIRECT);
            }

            subscriptionService.saveSubscription(courseId, student.getId(), lessonCount,
                    Subscription.SubscriptionStatus.WAITING_FOR_APPROVE);

        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        return new CommandResult(PagePath.PERSONAL_SUBSCRIPTIONS_PAGE_REDIRECT, CommandResult.RoutingType.REDIRECT);
    }
}
