package edu.gorb.musicstudio.controller.command.impl.student;

import edu.gorb.musicstudio.controller.command.*;
import edu.gorb.musicstudio.entity.Subscription;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.model.service.SubscriptionService;
import edu.gorb.musicstudio.model.service.UserService;
import edu.gorb.musicstudio.validator.DateTimeValidator;
import edu.gorb.musicstudio.validator.IntegerNumberValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ChooseLessonDateTimePageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        String subscriptionIdParameter = request.getParameter(RequestParameter.SUBSCRIPTION_ID);
        String dateParameter = request.getParameter(RequestParameter.DATE);
        if (!IntegerNumberValidator.isNonNegativeIntegerNumber(subscriptionIdParameter)) {
            return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        long subscriptionId = Long.parseLong(subscriptionIdParameter);
        LocalDate requestedDate;

        HttpSession session = request.getSession();
        User student = (User) session.getAttribute(SessionAttribute.USER);

        SubscriptionService subscriptionService = ServiceProvider.getInstance().getScheduleService();
        UserService userService = ServiceProvider.getInstance().getUserService();
        Map<User, List<LocalTime>> teachersFreeSlots;
        List<LocalDate> courseAvailableDates;
        Subscription subscription;
        try {
            Optional<Subscription> optionalSubscription = subscriptionService.findContinuingActiveSubscriptionById(subscriptionId);
            if (optionalSubscription.isEmpty()) {
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }
            subscription = optionalSubscription.get();
            if (subscription.getStatus() != Subscription.SubscriptionStatus.APPROVED
                    || subscription.getStudentId() != student.getId()) {
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }


            List<User> teachersForCourse = userService.findTeachersForCourse(subscription.getCourseId());

            courseAvailableDates = userService.findAllAvailableDatesForTeachers(teachersForCourse,
                    LocalDate.now(), subscription.getEndDate());

            if (courseAvailableDates.isEmpty()) {
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }


            if (dateParameter == null) {
                requestedDate = courseAvailableDates.get(0);
            } else {
                if (!DateTimeValidator.isValidDate(dateParameter)) {
                    return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
                }
                requestedDate = LocalDate.parse(dateParameter);
            }

            if (!courseAvailableDates.contains(requestedDate)) {
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }

            teachersFreeSlots = userService.findFreeSlotsForTeachersForDate(teachersForCourse, requestedDate);
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        request.setAttribute(RequestAttribute.DATE, requestedDate);
        request.setAttribute(RequestAttribute.SUBSCRIPTION, subscription);
        request.setAttribute(RequestAttribute.TEACHERS, teachersFreeSlots.keySet());
        request.setAttribute(RequestAttribute.TEACHERS_SCHEDULES, teachersFreeSlots);
        request.setAttribute(RequestAttribute.AVAILABLE_DATES, courseAvailableDates);
        return new CommandResult(PagePath.CHOOSE_LESSON_TIMEDATE_PAGE, CommandResult.RoutingType.FORWARD);
    }
}
