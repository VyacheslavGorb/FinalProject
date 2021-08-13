package edu.gorb.musicstudio.controller.command.impl.teacher;

import edu.gorb.musicstudio.controller.command.Command;
import edu.gorb.musicstudio.controller.command.CommandResult;
import edu.gorb.musicstudio.controller.command.PagePath;

import javax.servlet.http.HttpServletRequest;

public class GoToTeacherInitPageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        return new CommandResult(PagePath.TEACHER_INIT_PAGE, CommandResult.RoutingType.FORWARD);
    }
}
