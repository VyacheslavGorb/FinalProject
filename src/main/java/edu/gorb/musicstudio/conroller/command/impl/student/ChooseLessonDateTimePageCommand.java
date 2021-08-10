package edu.gorb.musicstudio.conroller.command.impl.student;

import edu.gorb.musicstudio.conroller.command.*;
import edu.gorb.musicstudio.entity.Subscription;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.model.service.SubscriptionService;
import edu.gorb.musicstudio.model.service.UserService;
import edu.gorb.musicstudio.validator.DateValidator;
import edu.gorb.musicstudio.validator.IntegerNumberValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class ChooseLessonDateTimePageCommand implements Command { //TODO move logic to service
    @Override
    public CommandResult execute(HttpServletRequest request) {
        String subscriptionIdParameter = request.getParameter(RequestParameter.SUBSCRIPTION_ID);
        String dateParameter = request.getParameter(RequestParameter.DATE);
        if (!DateValidator.isValidDate(dateParameter)
                || !IntegerNumberValidator.isNonNegativeIntegerNumber(subscriptionIdParameter)) {
            return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        long subscriptionId = Long.parseLong(subscriptionIdParameter);
        LocalDate requestedDate = LocalDate.parse(dateParameter);

        HttpSession session = request.getSession();
        User student = (User) session.getAttribute(SessionAttribute.USER);

        SubscriptionService subscriptionService = ServiceProvider.getInstance().getScheduleService();
        UserService userService = ServiceProvider.getInstance().getUserService();
        Map<User, List<LocalTime>> teachersFreeSlots = new HashMap<>();
        List<LocalDate> courseAvailableDates = new ArrayList<>();
        try {
            Optional<Subscription> optionalSubscription = subscriptionService.findActiveSubscriptionById(subscriptionId);
            if (optionalSubscription.isEmpty()) {
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }
            Subscription subscription = optionalSubscription.get();
            if (subscription.getStatus() != Subscription.SubscriptionStatus.APPROVED
                    || subscription.getStudentId() != student.getId()) {
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }

            if (requestedDate.isAfter(subscription.getEndDate()) || requestedDate.isBefore(requestedDate)) {
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }

            List<LocalDate> allSubscriptionAvailableDates = new ArrayList<>();
            LocalDate currentDate = LocalDate.now();
            while (!currentDate.isAfter(subscription.getEndDate())) {
                allSubscriptionAvailableDates.add(currentDate);
                currentDate = currentDate.plusDays(1);
            }
            List<User> teachersForCourse = userService.findTeachersForCourse(subscription.getCourseId());

            for (LocalDate date : allSubscriptionAvailableDates) {
                for (User teacher : teachersForCourse) {
                    if (!userService.findTeacherFreeSlotsForDate(teacher.getId(), date).isEmpty()) { //TODO add time check
                        courseAvailableDates.add(date);
                        break;
                    }
                }
            }

            if (courseAvailableDates.isEmpty()) {
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }

            LocalTime now = LocalTime.now();
            for (User teacher : teachersForCourse) {
                List<LocalTime> currentTeacherFreeSlots =
                        userService.findTeacherFreeSlotsForDate(teacher.getId(), requestedDate).stream()
                        .filter(localTime -> localTime.isAfter(now))
                        .collect(Collectors.toList());
                if (!currentTeacherFreeSlots.isEmpty()) {
                    teachersFreeSlots.put(teacher, currentTeacherFreeSlots);
                }
            }
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        request.setAttribute(RequestAttribute.TEACHERS, teachersFreeSlots.keySet());
        request.setAttribute(RequestAttribute.TEACHERS_SCHEDULES, teachersFreeSlots);
        request.setAttribute(RequestAttribute.AVAILABLE_DATES, courseAvailableDates);
        return new CommandResult(PagePath.CHOOSE_LESSON_TIMEDATE_PAGE, CommandResult.RoutingType.FORWARD);
    }
}
