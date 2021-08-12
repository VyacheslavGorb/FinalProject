package edu.gorb.musicstudio.conroller.command.impl.admin;

import edu.gorb.musicstudio.conroller.command.Command;
import edu.gorb.musicstudio.conroller.command.CommandResult;
import edu.gorb.musicstudio.conroller.command.PagePath;
import edu.gorb.musicstudio.conroller.command.RequestParameter;
import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.CourseService;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.validator.IntegerNumberValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class ActivateCourseCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        String courseIdParameter = request.getParameter(RequestParameter.COURSE_ID);
        CourseService courseService = ServiceProvider.getInstance().getCourseService();
        if(!IntegerNumberValidator.isNonNegativeIntegerNumber(courseIdParameter)){
            return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        long courseId = Long.parseLong(courseIdParameter);
        try {
            Optional<Course> courseOptional = courseService.findCourseById(courseId);
            if(courseOptional.isEmpty()){
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }
            Course course = courseOptional.get();
            course.setActive(true);
            courseService.updateCourse(course);
        }catch (ServiceException e){
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        return null;
    }
}
