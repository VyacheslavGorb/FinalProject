package edu.gorb.musicstudio.conroller.command.impl;

import edu.gorb.musicstudio.conroller.command.*;
import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.CourseService;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.validator.PageValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CoursesCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String DEFAULT_PAGE_NUMBER_PARAMETER = "1";

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String pageParameter = request.getParameter(RequestParameter.PAGE);
        String searchParameter = request.getParameter(RequestParameter.SEARCH);

        if(pageParameter == null){
            pageParameter = DEFAULT_PAGE_NUMBER_PARAMETER;
        }

        CourseService courseService = ServiceProvider.getInstance().getCourseService();

        int pageNumber;
        try {
            pageNumber = Integer.parseInt(pageParameter);
        } catch (NumberFormatException e) {
            return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.FORWARD); //TODO
        }

        int pageCount;
        List<Course> courses;
        try {
            pageCount = courseService.countCoursesForRequest(searchParameter);

            if(pageCount == 0){
                //TODO nothing was found
            }

            if (!PageValidator.isValidPageNumber(pageNumber, pageCount)) {
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.FORWARD); // TODO
            }

            courses = courseService.findCoursesForRequest(pageNumber, searchParameter);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error while selecting courses");
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        request.setAttribute(RequestAttribute.COURSES, courses);
        request.setAttribute(RequestAttribute.PAGE_COUNT, pageCount);
        request.setAttribute(RequestAttribute.SEARCH_LINE, searchParameter);
        return new CommandResult(PagePath.COURSES_PAGE, CommandResult.RoutingType.FORWARD);
    }
}
