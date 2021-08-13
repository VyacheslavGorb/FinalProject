package edu.gorb.musicstudio.controller.command.impl.admin;

import edu.gorb.musicstudio.controller.command.*;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.CourseService;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.validator.FormValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class AddCourseCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        try {
            String courseName = request.getParameter(RequestParameter.COURSE_NAME);
            String description = request.getParameter(RequestParameter.DESCRIPTION);
            String priceParameter = request.getParameter(RequestParameter.PRICE);
            List<Part> imageParts = request.getParts().stream()
                    .filter(part -> part.getName().equals(RequestParameter.IMAGE) && part.getSize() > 0)
                    .collect(Collectors.toList());
            HttpSession session = request.getSession();
            if (imageParts.isEmpty()) {
                session.setAttribute(SessionAttribute.IS_ADD_COURSE_ERROR, true);
                return new CommandResult(PagePath.ADD_COURSE_REDIRECT, CommandResult.RoutingType.REDIRECT);
            }
            String fileName = imageParts.get(0).getSubmittedFileName();
            if (!FormValidator.areAddCourseParametersValid(courseName, description, priceParameter, fileName)) {
                return new CommandResult(PagePath.ERROR_404_PAGE, CommandResult.RoutingType.REDIRECT);
            }
            CourseService courseService = ServiceProvider.getInstance().getCourseService();
            BigDecimal price = new BigDecimal(priceParameter);
            courseService.saveNewCourse(courseName, description, price, imageParts);
        } catch (ServiceException | IOException | ServletException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        return new CommandResult(PagePath.ALL_COURSES_REDIRECT, CommandResult.RoutingType.REDIRECT);
    }
}
