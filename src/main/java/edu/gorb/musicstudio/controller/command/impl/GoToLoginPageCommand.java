package edu.gorb.musicstudio.controller.command.impl;

import edu.gorb.musicstudio.controller.command.Command;
import edu.gorb.musicstudio.controller.command.CommandResult;
import edu.gorb.musicstudio.controller.command.PagePath;

import javax.servlet.http.HttpServletRequest;

public class GoToLoginPageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        return new CommandResult(PagePath.LOGIN_PAGE, CommandResult.RoutingType.FORWARD);
    }
}
