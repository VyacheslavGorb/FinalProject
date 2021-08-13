package edu.gorb.musicstudio.controller.command.impl.student;

import edu.gorb.musicstudio.controller.command.*;
import edu.gorb.musicstudio.dto.LessonScheduleDto;
import edu.gorb.musicstudio.entity.LessonSchedule;
import edu.gorb.musicstudio.entity.Subscription;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.entity.UserRole;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.LessonScheduleService;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.model.service.SubscriptionService;
import edu.gorb.musicstudio.model.service.UserService;
import edu.gorb.musicstudio.validator.DateTimeValidator;
import edu.gorb.musicstudio.validator.IntegerNumberValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class ChooseLessonDateTimeCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        String subscriptionIdParameter = request.getParameter(RequestParameter.SUBSCRIPTION_ID);
        String teacherIdParameter = request.getParameter(RequestParameter.TEACHER_ID);
        String dateParameter = request.getParameter(RequestParameter.DATE);
        String timeParameter = request.getParameter(RequestParameter.TIME);
        if (!DateTimeValidator.isValidTime(timeParameter)
                || !DateTimeValidator.isValidDate(dateParameter)
                || !IntegerNumberValidator.isNonNegativeIntegerNumber(subscriptionIdParameter)
                || !IntegerNumberValidator.isNonNegativeIntegerNumber(teacherIdParameter)) {
            return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        long subscriptionId = Long.parseLong(subscriptionIdParameter);
        long teacherId = Long.parseLong(teacherIdParameter);
        LocalDate date = LocalDate.parse(dateParameter);
        LocalTime time = LocalTime.parse(timeParameter);

        SubscriptionService subscriptionService = ServiceProvider.getInstance().getScheduleService();
        UserService userService = ServiceProvider.getInstance().getUserService();
        LessonScheduleService lessonScheduleService = ServiceProvider.getInstance().getLessonScheduleService();
        HttpSession session = request.getSession();
        User student = (User) session.getAttribute(SessionAttribute.USER);
        try {
            Optional<Subscription> optionalSubscription = subscriptionService.findContinuingActiveSubscriptionById(subscriptionId);
            if (optionalSubscription.isEmpty()) {
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }
            Subscription subscription = optionalSubscription.get();
            if (subscription.getStatus() != Subscription.SubscriptionStatus.APPROVED
                    || subscription.getStudentId() != student.getId()) {
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }

            Optional<User> optionalTeacher = userService.findUserById(teacherId);
            if (optionalTeacher.isEmpty()) {
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }

            if (date.isAfter(subscription.getEndDate()) || date.isBefore(subscription.getStartDate())) {
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }

            User teacher = optionalTeacher.get();
            if (teacher.getRole() != UserRole.TEACHER) {
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }
            List<LocalTime> freeTeacherSlots = userService.findTeacherFreeSlotsForDate(teacherId, date);
            if (!freeTeacherSlots.contains(time)) {
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }

            lessonScheduleService.saveNewLessonSchedule(student.getId(), teacherId, subscription.getCourseId(),
                    subscriptionId, LocalDateTime.of(date, time), LessonSchedule.LessonStatus.NORMAL);

            List<LessonScheduleDto> lessonSchedules = lessonScheduleService.findLessonSchedulesBySubscriptionId(subscriptionId);
            if(lessonSchedules.size() == subscription.getLessonCount()){
                subscriptionService.updateStatus(subscriptionId, Subscription.SubscriptionStatus.ACTIVATED);
            }

        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        return new CommandResult(PagePath.PERSONAL_SUBSCRIPTIONS_PAGE_REDIRECT, CommandResult.RoutingType.REDIRECT);
    }
}
