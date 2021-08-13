package edu.gorb.musicstudio.controller.command.impl.teacher;

import edu.gorb.musicstudio.controller.command.*;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.model.service.TeacherDescriptionService;
import edu.gorb.musicstudio.validator.FormValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateTeacherDescriptionCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        try {
            TeacherDescriptionService descriptionService = ServiceProvider.getInstance().getTeacherDescriptionService();
            HttpSession session = request.getSession();
            User teacher = (User) session.getAttribute(SessionAttribute.USER);

            String description = request.getParameter(RequestParameter.DESCRIPTION);
            String experienceParameter = request.getParameter(RequestParameter.EXPERIENCE);
            List<Part> imageParts = request.getParts().stream()
                    .filter(part -> part.getName().equals(RequestParameter.IMAGE) && part.getSize() > 0)
                    .collect(Collectors.toList());

            if (!FormValidator.areTeacherUpdateParametersValid(description, experienceParameter)) {
                session.setAttribute(SessionAttribute.IS_TEACHER_INFO_ERROR, true);
                return new CommandResult(PagePath.TEACHER_PERSONAL_INFO_REDIRECT, CommandResult.RoutingType.REDIRECT);
            }

            if (!imageParts.isEmpty()) {
                String fileName = imageParts.get(0).getSubmittedFileName();
                if (!FormValidator.isImageFileNameValid(fileName)) {
                    session.setAttribute(SessionAttribute.IS_TEACHER_INFO_ERROR, true);
                    return new CommandResult(PagePath.TEACHER_PERSONAL_INFO_REDIRECT, CommandResult.RoutingType.REDIRECT);
                }
            }

            int workExperience = Integer.parseInt(experienceParameter);
            if (imageParts.isEmpty()) {
                descriptionService.updateTeacherDescription(teacher.getId(), description, workExperience);
            } else {
                descriptionService.updateTeacherDescriptionWithImageUpload(teacher.getId(), imageParts, description, workExperience);
            }
        } catch (ServiceException | ServletException | IOException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        return new CommandResult(PagePath.TEACHER_PERSONAL_INFO_REDIRECT, CommandResult.RoutingType.REDIRECT);
    }
}
