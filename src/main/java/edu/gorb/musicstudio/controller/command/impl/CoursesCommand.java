package edu.gorb.musicstudio.controller.command.impl;

import edu.gorb.musicstudio.controller.command.*;
import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.CourseService;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.validator.PageValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CoursesCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String pageParameter = request.getParameter(RequestParameter.PAGE);
        String searchParameter = request.getParameter(RequestParameter.SEARCH);

        CourseService courseService = ServiceProvider.getInstance().getCourseService();

        int pageCount;
        List<Course> courses;
        try {
            int courseAmount = courseService.countCoursesForRequest(searchParameter);
            if (courseAmount == 0) {
                request.setAttribute(RequestAttribute.NOTHING_FOUND, true);
                return new CommandResult(PagePath.COURSES_PAGE, CommandResult.RoutingType.FORWARD);
            }
            pageCount = courseService.calcPagesCount(courseAmount);

            if (!PageValidator.isValidPageParameter(pageParameter, pageCount)) {
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }

            int pageNumber = Integer.parseInt(pageParameter);

            courses = courseService.findCoursesForRequest(pageNumber, searchParameter);
            courses = courseService.trimCoursesDescriptionForPreview(courses);
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        request.setAttribute(RequestAttribute.COMMAND, CommandType.COURSES.toString().toLowerCase());
        request.setAttribute(RequestAttribute.COURSES, courses);
        request.setAttribute(RequestAttribute.PAGE_COUNT, pageCount);
        request.setAttribute(RequestAttribute.SEARCH_LINE, searchParameter);
        return new CommandResult(PagePath.COURSES_PAGE, CommandResult.RoutingType.FORWARD);
    }
}
