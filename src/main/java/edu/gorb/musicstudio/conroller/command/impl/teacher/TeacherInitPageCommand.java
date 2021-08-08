package edu.gorb.musicstudio.conroller.command.impl.teacher;

import edu.gorb.musicstudio.conroller.command.Command;
import edu.gorb.musicstudio.conroller.command.CommandResult;
import edu.gorb.musicstudio.conroller.command.PagePath;
import edu.gorb.musicstudio.conroller.command.SessionAttribute;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.model.service.TeacherDescriptionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class TeacherInitPageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        TeacherDescriptionService teacherDescriptionService =
                ServiceProvider.getInstance().getTeacherDescriptionService();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);

        try {
            if (teacherDescriptionService.teacherDescriptionExists(user.getId())) {
                return new CommandResult(PagePath.TEACHER_LESSON_SCHEDULE_PAGE_REDIRECT, CommandResult.RoutingType.REDIRECT);
            }
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        return new CommandResult(PagePath.TEACHER_INIT_PAGE, CommandResult.RoutingType.FORWARD);
    }
}
