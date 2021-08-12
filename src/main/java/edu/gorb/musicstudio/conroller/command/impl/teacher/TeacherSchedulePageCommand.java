package edu.gorb.musicstudio.conroller.command.impl.teacher;

import edu.gorb.musicstudio.conroller.command.*;
import edu.gorb.musicstudio.entity.TeacherSchedule;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.model.service.TeacherScheduleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class TeacherSchedulePageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        User teacher = (User) session.getAttribute(SessionAttribute.USER);

        TeacherScheduleService scheduleService = ServiceProvider.getInstance().getTeacherScheduleService();
        List<TeacherSchedule> teacherSchedules;
        try {
            teacherSchedules = scheduleService.findAllById(teacher.getId());
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        request.setAttribute(RequestAttribute.TEACHER_SCHEDULE, teacherSchedules);
        return new CommandResult(PagePath.TEACHER_SCHEDULE_PAGE, CommandResult.RoutingType.FORWARD);
    }
}

