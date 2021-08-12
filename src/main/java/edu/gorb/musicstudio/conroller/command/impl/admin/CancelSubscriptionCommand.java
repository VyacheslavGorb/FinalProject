package edu.gorb.musicstudio.conroller.command.impl.admin;

import edu.gorb.musicstudio.conroller.command.Command;
import edu.gorb.musicstudio.conroller.command.CommandResult;
import edu.gorb.musicstudio.conroller.command.PagePath;
import edu.gorb.musicstudio.conroller.command.RequestParameter;
import edu.gorb.musicstudio.entity.Subscription;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.model.service.SubscriptionService;
import edu.gorb.musicstudio.validator.IntegerNumberValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class CancelSubscriptionCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        String subscriptionIdParameter = request.getParameter(RequestParameter.SUBSCRIPTION_ID);
        if(!IntegerNumberValidator.isNonNegativeIntegerNumber(subscriptionIdParameter)){
            return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        long subscriptionId = Long.parseLong(subscriptionIdParameter);
        SubscriptionService subscriptionService = ServiceProvider.getInstance().getScheduleService();
        try{
            Optional<Subscription> optionalSubscription =
                    subscriptionService.findContinuingActiveSubscriptionById(subscriptionId);
            if(optionalSubscription.isEmpty()){
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }
            subscriptionService.updateStatus(subscriptionId, Subscription.SubscriptionStatus.CANCELLED);
        }catch (ServiceException e){
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        return new CommandResult(PagePath.ALL_SUBSCRIPTIONS_REDIRECT, CommandResult.RoutingType.REDIRECT);
    }
}
