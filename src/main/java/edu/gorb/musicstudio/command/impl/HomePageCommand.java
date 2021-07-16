package edu.gorb.musicstudio.command.impl;

import edu.gorb.musicstudio.command.Command;
import edu.gorb.musicstudio.command.CommandResult;
import edu.gorb.musicstudio.command.PagePath;

import javax.servlet.http.HttpServletRequest;

public class HomePageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        //TODO add dynamic content
        return new CommandResult(PagePath.HOME_PAGE, CommandResult.RoutingType.FORWARD);
    }
}
