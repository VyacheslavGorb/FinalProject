package edu.gorb.musicstudio.conroller.command.impl.admin;

import edu.gorb.musicstudio.conroller.command.Command;
import edu.gorb.musicstudio.conroller.command.CommandResult;
import edu.gorb.musicstudio.conroller.command.PagePath;

import javax.servlet.http.HttpServletRequest;

public class AddCoursePageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        return new CommandResult(PagePath.ADD_COURSE, CommandResult.RoutingType.FORWARD);
    }
}
