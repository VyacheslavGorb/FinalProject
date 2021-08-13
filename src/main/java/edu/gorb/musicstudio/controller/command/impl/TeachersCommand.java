package edu.gorb.musicstudio.controller.command.impl;

import edu.gorb.musicstudio.controller.command.*;
import edu.gorb.musicstudio.entity.Teacher;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.model.service.UserService;
import edu.gorb.musicstudio.validator.PageValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class TeachersCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        String pageParameter = request.getParameter(RequestParameter.PAGE);
        String searchParameter = request.getParameter(RequestParameter.SEARCH);

        UserService userService = ServiceProvider.getInstance().getUserService();

        int pageCount;
        List<Teacher> teachers;
        try {
            int teacherAmount = userService.countTeachersForRequest(searchParameter);
            if (teacherAmount == 0) {
                request.setAttribute(RequestAttribute.NOTHING_FOUND, true);
                return new CommandResult(PagePath.TEACHERS_PAGE, CommandResult.RoutingType.FORWARD);
            }
            pageCount = userService.calcPagesCount(teacherAmount);
            if (!PageValidator.isValidPageParameter(pageParameter, pageCount)) {
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }
            int pageNumber = Integer.parseInt(pageParameter);
            teachers = userService.findTeachersForRequest(pageNumber, searchParameter);
            teachers = userService.trimTeachersDescriptionForPreview(teachers);
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        request.setAttribute(RequestAttribute.COMMAND, CommandType.TEACHERS.toString().toLowerCase());
        request.setAttribute(RequestAttribute.TEACHERS, teachers);
        request.setAttribute(RequestAttribute.PAGE_COUNT, pageCount);
        request.setAttribute(RequestAttribute.SEARCH_LINE, searchParameter);

        return new CommandResult(PagePath.TEACHERS_PAGE, CommandResult.RoutingType.FORWARD);
    }
}
