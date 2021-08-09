package edu.gorb.musicstudio.conroller.command.impl.teacher;

import edu.gorb.musicstudio.conroller.command.*;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.model.service.TeacherScheduleService;
import edu.gorb.musicstudio.validator.FormValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AlterTeacherScheduleCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User teacher = (User) session.getAttribute(SessionAttribute.USER);

        String startHourParameter = request.getParameter(RequestParameter.START_HOUR);
        String endHourParameter = request.getParameter(RequestParameter.END_HOUR);
        String dayOfWeekParameter = request.getParameter(RequestParameter.DAY_OF_WEEK);
        String removeParameter = request.getParameter(RequestParameter.REMOVE);

        if (!FormValidator.areAlterTeacherScheduleParametersValid(
                startHourParameter, endHourParameter, removeParameter, dayOfWeekParameter)) {
            session.setAttribute(SessionAttribute.IS_TEACHER_SCHEDULE_ERROR, true);
            return new CommandResult(PagePath.TEACHER_SCHEDULE_PAGE_REDIRECT, CommandResult.RoutingType.REDIRECT);
        }

        TeacherScheduleService scheduleService = ServiceProvider.getInstance().getTeacherScheduleService();
        try {
            boolean isAltered = scheduleService.alterSchedule(teacher.getId(), startHourParameter,
                    endHourParameter, Integer.parseInt(dayOfWeekParameter),
                    removeParameter != null);
            if (!isAltered) {
                session.setAttribute(SessionAttribute.IS_TEACHER_SCHEDULE_ERROR, true);
                return new CommandResult(PagePath.TEACHER_SCHEDULE_PAGE_REDIRECT, CommandResult.RoutingType.REDIRECT);
            }
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        return new CommandResult(PagePath.TEACHER_SCHEDULE_PAGE_REDIRECT, CommandResult.RoutingType.REDIRECT);
    }
}
