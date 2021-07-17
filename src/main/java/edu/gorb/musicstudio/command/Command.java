package edu.gorb.musicstudio.command;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    CommandResult execute(HttpServletRequest request);
}
