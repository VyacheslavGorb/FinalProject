package edu.gorb.musicstudio.command.impl;

import edu.gorb.musicstudio.command.Command;
import edu.gorb.musicstudio.command.CommandResult;
import edu.gorb.musicstudio.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class PersonalPageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request){
        throw new UnsupportedOperationException(); //TODO
    }
}
