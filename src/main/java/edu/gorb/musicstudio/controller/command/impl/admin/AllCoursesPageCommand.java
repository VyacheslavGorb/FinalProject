package edu.gorb.musicstudio.controller.command.impl.admin;

import edu.gorb.musicstudio.controller.command.Command;
import edu.gorb.musicstudio.controller.command.CommandResult;
import edu.gorb.musicstudio.controller.command.PagePath;
import edu.gorb.musicstudio.controller.command.RequestAttribute;
import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.CourseService;
import edu.gorb.musicstudio.model.service.ServiceProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AllCoursesPageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        CourseService courseService = ServiceProvider.getInstance().getCourseService();
        List<Course> allCourses;
        try {
            allCourses = courseService.findAllCourses();
        }catch (ServiceException e){
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        request.setAttribute(RequestAttribute.COURSES, allCourses);
        return new CommandResult(PagePath.ALL_COURSES, CommandResult.RoutingType.FORWARD);
    }
}
