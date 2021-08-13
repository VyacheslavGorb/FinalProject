package edu.gorb.musicstudio.controller.command.impl;

import edu.gorb.musicstudio.controller.command.Command;
import edu.gorb.musicstudio.controller.command.CommandResult;
import edu.gorb.musicstudio.controller.command.PagePath;

import javax.servlet.http.HttpServletRequest;

public class GoToSendEmailAgainPageCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) {
        return new CommandResult(PagePath.SEND_EMAIL_AGAIN_PAGE, CommandResult.RoutingType.FORWARD);
    }
}
