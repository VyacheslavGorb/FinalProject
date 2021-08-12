package edu.gorb.musicstudio.conroller.command.impl.admin;

import edu.gorb.musicstudio.conroller.command.Command;
import edu.gorb.musicstudio.conroller.command.CommandResult;
import edu.gorb.musicstudio.conroller.command.PagePath;
import edu.gorb.musicstudio.conroller.command.RequestAttribute;
import edu.gorb.musicstudio.dto.SubscriptionDto;
import edu.gorb.musicstudio.entity.Subscription;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.model.service.SubscriptionService;
import edu.gorb.musicstudio.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AllSubscriptionsPageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        SubscriptionService subscriptionService = ServiceProvider.getInstance().getScheduleService();
        UserService userService = ServiceProvider.getInstance().getUserService();
        List<SubscriptionDto> allActiveSubscriptions;
        List<SubscriptionDto> waitingForApproveSubscriptions;
        Map<SubscriptionDto, Integer> availableLessonCount = new HashMap<>();
        Map<SubscriptionDto, Integer> forSureAvailableLessonCount = new HashMap<>();
        try {
            List<SubscriptionDto> allSubscriptions = subscriptionService.findAllContinuingActiveSubscriptions();
            waitingForApproveSubscriptions = allSubscriptions.stream().
                    filter(s -> s.getStatus() == Subscription.SubscriptionStatus.WAITING_FOR_APPROVE)
                    .collect(Collectors.toList());
            allActiveSubscriptions = allSubscriptions.stream().
                    filter(s -> s.getStatus() != Subscription.SubscriptionStatus.WAITING_FOR_APPROVE)
                    .collect(Collectors.toList());
            for (SubscriptionDto subscription : waitingForApproveSubscriptions) {
                int totalFreeSlotsCount = 0;
                List<User> teachersForCourse = userService.findTeachersForCourse(subscription.getCourseId());
                for (User teacher : teachersForCourse) {
                    totalFreeSlotsCount += userService.findTeacherFreeSlotCountForFuturePeriod(
                            subscription.getStartDate(),
                            subscription.getEndDate(),
                            teacher.getId());
                }
                int potentiallyBusySlotCount =
                        subscriptionService.findPotentiallyBusySlotCountForCourse(subscription.getCourseId());
                potentiallyBusySlotCount -= subscription.getLessonCount();
                forSureAvailableLessonCount.put(subscription, totalFreeSlotsCount - potentiallyBusySlotCount);
                availableLessonCount.put(subscription, totalFreeSlotsCount);
            }
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        request.setAttribute(RequestAttribute.ACTIVE_SUBSCRIPTIONS, allActiveSubscriptions);
        request.setAttribute(RequestAttribute.WAITING_FOR_APPROVE_SUBSCRIPTIONS, waitingForApproveSubscriptions);
        request.setAttribute(RequestAttribute.SUBSCRIPTIONS_LESSON_COUNT, availableLessonCount);
        request.setAttribute(RequestAttribute.SUBSCRIPTIONS_FOR_SURE_LESSON_COUNT, forSureAvailableLessonCount);
        return new CommandResult(PagePath.ALL_SUBSCRIPTIONS, CommandResult.RoutingType.FORWARD);
    }
}
