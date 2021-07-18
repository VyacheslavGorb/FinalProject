package edu.gorb.musicstudio.conroller;

import edu.gorb.musicstudio.command.Command;
import edu.gorb.musicstudio.command.CommandProvider;
import edu.gorb.musicstudio.command.CommandResult;
import edu.gorb.musicstudio.command.RequestParameter;
import edu.gorb.musicstudio.pool.ConnectionPool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {

    @Override
    public void init() throws ServletException {
        ConnectionPool.getInstance();
    }

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
        }
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroyPool();
    }
}
