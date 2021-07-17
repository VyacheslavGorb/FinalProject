package edu.gorb.musicstudio.command.impl;

import edu.gorb.musicstudio.command.Command;
import edu.gorb.musicstudio.command.CommandResult;
import edu.gorb.musicstudio.command.PagePath;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return new CommandResult(PagePath.HOME_PAGE_REDIRECT, CommandResult.RoutingType.REDIRECT);
    }
}
