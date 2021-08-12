package edu.gorb.musicstudio.conroller.command.impl.student;

import edu.gorb.musicstudio.conroller.command.*;
import edu.gorb.musicstudio.dto.LessonScheduleDto;
import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.entity.Subscription;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.CourseService;
import edu.gorb.musicstudio.model.service.LessonScheduleService;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.model.service.SubscriptionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonalSubscriptionsCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User student = (User) session.getAttribute(SessionAttribute.USER);
        SubscriptionService subscriptionService = ServiceProvider.getInstance().getScheduleService();
        LessonScheduleService lessonScheduleService = ServiceProvider.getInstance().getLessonScheduleService();
        CourseService courseService = ServiceProvider.getInstance().getCourseService();

        Map<Subscription, List<LessonScheduleDto>> subscriptionLessonSchedules = new HashMap<>();
        Map<Subscription, Course> subscriptionCourses = new HashMap<>();

        List<Subscription> subscriptions;
        try {
            subscriptions = subscriptionService.findContinuingActiveStudentSubscriptions(student.getId());
            for (Subscription subscription : subscriptions) {
                List<LessonScheduleDto> schedules =
                        lessonScheduleService.findLessonSchedulesBySubscription(subscription.getId());
                Course course = courseService.findCourseById(subscription.getCourseId()).get(); //Course always exists
                subscriptionLessonSchedules.put(subscription, schedules);
                subscriptionCourses.put(subscription, course);
            }
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        request.setAttribute(RequestAttribute.SUBSCRIPTIONS_COURSES, subscriptionCourses);
        request.setAttribute(RequestAttribute.SUBSCRIPTIONS, subscriptions);
        request.setAttribute(RequestAttribute.SUBSCRIPTION_SCHEDULES, subscriptionLessonSchedules);
        return new CommandResult(PagePath.PERSONAL_SUBSCRIPTIONS_PAGE, CommandResult.RoutingType.FORWARD);
    }
}
