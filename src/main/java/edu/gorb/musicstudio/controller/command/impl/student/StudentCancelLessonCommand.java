package edu.gorb.musicstudio.controller.command.impl.student;

import edu.gorb.musicstudio.controller.command.*;
import edu.gorb.musicstudio.entity.LessonSchedule;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.LessonScheduleService;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.validator.IntegerNumberValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class StudentCancelLessonCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        String lessonIdParameter = request.getParameter(RequestParameter.LESSON_ID);
        if (!IntegerNumberValidator.isNonNegativeIntegerNumber(lessonIdParameter)) {
            return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        long lessonId = Long.parseLong(lessonIdParameter);

        HttpSession session = request.getSession();
        User student = (User) session.getAttribute(SessionAttribute.USER);
        LessonScheduleService scheduleService = ServiceProvider.getInstance().getLessonScheduleService();
        try {
            Optional<LessonSchedule> scheduleOptional = scheduleService.findEntityById(lessonId);
            if (scheduleOptional.isEmpty() || scheduleOptional.get().getStudentId() != student.getId()
                    || scheduleOptional.get().getStatus() == LessonSchedule.LessonStatus.CANCELLED) {
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }
            scheduleService.updateStatus(scheduleOptional.get().getId(), LessonSchedule.LessonStatus.CANCELLED);
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        return new CommandResult(PagePath.STUDENT_LESSON_SCHEDULE_PAGE_REDIRECT, CommandResult.RoutingType.REDIRECT);
    }
}
