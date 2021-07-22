package edu.gorb.musicstudio.conroller.command.impl.go;

import edu.gorb.musicstudio.conroller.command.Command;
import edu.gorb.musicstudio.conroller.command.CommandResult;
import edu.gorb.musicstudio.conroller.command.PagePath;

import javax.servlet.http.HttpServletRequest;

public class GoToSendEmailAgainPageCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) {
        return new CommandResult(PagePath.SEND_EMAIL_AGAIN_PAGE, CommandResult.RoutingType.FORWARD);
    }
}
