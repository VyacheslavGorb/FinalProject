package edu.gorb.musicstudio.controller.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface of servlet command
 */
public interface Command {
    /**
     * Method is called by servlet when processing request
     *
     * @param request servlet request object
     * @return result of command execution containing page path and routing type
     */
    CommandResult execute(HttpServletRequest request);
}
