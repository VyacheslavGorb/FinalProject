package edu.gorb.musicstudio.command.impl;

import edu.gorb.musicstudio.command.Command;
import edu.gorb.musicstudio.command.CommandResult;
import edu.gorb.musicstudio.command.PagePath;

import javax.servlet.http.HttpServletRequest;

public class GoToSignUpPage implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        return new CommandResult(PagePath.SIGN_UP_PAGE, CommandResult.RoutingType.FORWARD);
    }
}
