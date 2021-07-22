package edu.gorb.musicstudio.conroller.command.impl.go;

import edu.gorb.musicstudio.conroller.command.Command;
import edu.gorb.musicstudio.conroller.command.CommandResult;
import edu.gorb.musicstudio.conroller.command.PagePath;

import javax.servlet.http.HttpServletRequest;

public class GoToEmailSentPageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        return new CommandResult(PagePath.INFO_EMAIL_SENT_PAGE, CommandResult.RoutingType.FORWARD);
    }
}
