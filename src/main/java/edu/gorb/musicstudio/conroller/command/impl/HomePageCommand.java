package edu.gorb.musicstudio.conroller.command.impl;

import edu.gorb.musicstudio.conroller.command.Command;
import edu.gorb.musicstudio.conroller.command.CommandResult;
import edu.gorb.musicstudio.conroller.command.PagePath;

import javax.servlet.http.HttpServletRequest;

public class HomePageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        return new CommandResult(PagePath.HOME_PAGE, CommandResult.RoutingType.FORWARD);
    }
}
