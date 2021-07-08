package edu.gorb.musicstudio.command;

import edu.gorb.musicstudio.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    CommandResult execute(HttpServletRequest request) throws ServiceException;
}
