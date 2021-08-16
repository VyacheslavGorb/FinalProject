package edu.gorb.musicstudio.controller;

import edu.gorb.musicstudio.controller.command.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main controller servlet
 */
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     * Executes command from request parameter and forwards request or sends redirect
     *
     * @param req  http request
     * @param resp http response
     * @throws ServletException is thrown when forward exception occurs
     * @throws IOException      is thrown when send redirect exception occurs
     */
    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandString = req.getParameter(RequestParameter.COMMAND);
        Command command = CommandProvider.getInstance().getCommand(commandString);
        CommandResult commandResult;
        commandResult = command.execute(req);
        switch (commandResult.getRoutingType()) {
            case FORWARD:
                req.getRequestDispatcher(commandResult.getPage()).forward(req, resp);
                break;
            case REDIRECT:
                resp.sendRedirect(req.getContextPath() + commandResult.getPage());
                break;
            default:
                logger.log(Level.ERROR, "Illegal routing type");
                resp.sendRedirect(PagePath.ERROR_500_PAGE);
        }
    }
}
