package edu.gorb.musicstudio.conroller;

import edu.gorb.musicstudio.command.Command;
import edu.gorb.musicstudio.command.CommandProvider;
import edu.gorb.musicstudio.command.CommandResult;
import edu.gorb.musicstudio.command.RequestParameter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandString = req.getParameter(RequestParameter.COMMAND);
        if (commandString == null) {
            req.getRequestDispatcher("index.jsp").forward(req, resp); // TODO page
        }
        Command command = CommandProvider.getInstance().getCommand(commandString);
        CommandResult commandResult;
        try {
            commandResult = command.execute(req);
        } catch (edu.gorb.musicstudio.exception.ServiceException e) {
            throw new ServletException(e); // TODO Custom Exception?
        }
        switch (commandResult.getRoutingType()) {
            case REDIRECT:
                resp.sendRedirect("TODO"); // TODO page
                break;
            case FORWARD:
                req.getRequestDispatcher("TODO").forward(req, resp); // TODO page
                break;
        }
    }
}
