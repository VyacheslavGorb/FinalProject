package edu.gorb.musicstudio.controller.command.impl.admin;

import edu.gorb.musicstudio.controller.command.*;
import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.CourseService;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.validator.FormValidator;
import edu.gorb.musicstudio.validator.IntegerNumberValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChangeCourseCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        try {
            String courseIdParameter = request.getParameter(RequestParameter.COURSE_ID);
            String courseName = request.getParameter(RequestParameter.COURSE_NAME);
            String description = request.getParameter(RequestParameter.DESCRIPTION);
            String priceParameter = request.getParameter(RequestParameter.PRICE);
            List<Part> imageParts = request.getParts().stream()
                    .filter(part -> part.getName().equals(RequestParameter.IMAGE) && part.getSize() > 0)
                    .collect(Collectors.toList());

            if(!IntegerNumberValidator.isNonNegativeIntegerNumber(courseIdParameter)){
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }

            HttpSession session = request.getSession();

            long courseId = Long.parseLong(courseIdParameter);
            CourseService courseService = ServiceProvider.getInstance().getCourseService();
            Optional<Course> optionalCourse = courseService.findCourseById(courseId);
            if (optionalCourse.isEmpty()) {
                session.setAttribute(SessionAttribute.IS_CHANGE_COURSE_ERROR, true);
                return new CommandResult(PagePath.CHANGE_COURSE_REDIRECT + courseIdParameter,
                        CommandResult.RoutingType.REDIRECT);
            }

            if (!IntegerNumberValidator.isNonNegativeIntegerNumber(courseIdParameter)
                    || !FormValidator.areUpdateCourseParametersValid(courseName, description, priceParameter)) {
                session.setAttribute(SessionAttribute.IS_CHANGE_COURSE_ERROR, true);
                return new CommandResult(PagePath.CHANGE_COURSE_REDIRECT + courseIdParameter,
                        CommandResult.RoutingType.REDIRECT);
            }

            if (!imageParts.isEmpty()) {
                String fileName = imageParts.get(0).getSubmittedFileName();
                if (!FormValidator.isImageFileNameValid(fileName)) {
                    session.setAttribute(SessionAttribute.IS_CHANGE_COURSE_ERROR, true);
                    return new CommandResult(PagePath.CHANGE_COURSE_REDIRECT + courseIdParameter,
                            CommandResult.RoutingType.REDIRECT);
                }
            }

            Course course = optionalCourse.get();
            BigDecimal price = new BigDecimal(priceParameter);

            if (imageParts.isEmpty()) {
                course.setName(courseName);
                course.setDescription(description);
                course.setPricePerHour(price);
                courseService.updateCourse(course);
            } else {
                courseService.updateCourseWithImageUpload(course.getId(), courseName, description,
                        price, course.isActive(), imageParts);
            }
        } catch (IOException | ServletException | ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        return new CommandResult(PagePath.ALL_COURSES_REDIRECT, CommandResult.RoutingType.REDIRECT);
    }
}
