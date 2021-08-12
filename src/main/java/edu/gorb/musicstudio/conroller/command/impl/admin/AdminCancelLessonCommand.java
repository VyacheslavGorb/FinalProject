package edu.gorb.musicstudio.conroller.command.impl.admin;

import edu.gorb.musicstudio.conroller.command.Command;
import edu.gorb.musicstudio.conroller.command.CommandResult;
import edu.gorb.musicstudio.conroller.command.PagePath;
import edu.gorb.musicstudio.conroller.command.RequestParameter;
import edu.gorb.musicstudio.entity.LessonSchedule;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.LessonScheduleService;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.validator.IntegerNumberValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class AdminCancelLessonCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        String lessonIdParameter = request.getParameter(RequestParameter.LESSON_ID);
        if (!IntegerNumberValidator.isNonNegativeIntegerNumber(lessonIdParameter)) {
            return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        long lessonId = Long.parseLong(lessonIdParameter);

        LessonScheduleService scheduleService = ServiceProvider.getInstance().getLessonScheduleService();
        try {
            Optional<LessonSchedule> scheduleOptional = scheduleService.findEntityById(lessonId);
            if (scheduleOptional.isEmpty()
                    || scheduleOptional.get().getStatus() == LessonSchedule.LessonStatus.CANCELLED) {
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }
            scheduleService.updateStatus(scheduleOptional.get().getId(), LessonSchedule.LessonStatus.CANCELLED);
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        return new CommandResult(PagePath.ALL_LESSONS_REDIRECT, CommandResult.RoutingType.REDIRECT);
    }
}
