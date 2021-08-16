package edu.gorb.musicstudio.controller.command.impl.student;

import edu.gorb.musicstudio.controller.command.*;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.entity.dto.LessonScheduleDto;
import edu.gorb.musicstudio.entity.dto.SubscriptionDto;
import edu.gorb.musicstudio.exception.ServiceException;
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
        Map<SubscriptionDto, List<LessonScheduleDto>> subscriptionLessonSchedules = new HashMap<>();
        try {
            List<SubscriptionDto> subscriptionDtos =
                    subscriptionService.findContinuingActiveStudentSubscriptions(student.getId());
            for (SubscriptionDto subscriptionDto : subscriptionDtos) {
                List<LessonScheduleDto> schedules =
                        lessonScheduleService.findLessonSchedulesBySubscriptionId(subscriptionDto.getSubscriptionId());
                subscriptionLessonSchedules.put(subscriptionDto, schedules);
            }
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        request.setAttribute(RequestAttribute.SUBSCRIPTION_SCHEDULE_MAP, subscriptionLessonSchedules);
        return new CommandResult(PagePath.PERSONAL_SUBSCRIPTIONS_PAGE, CommandResult.RoutingType.FORWARD);
    }
}
