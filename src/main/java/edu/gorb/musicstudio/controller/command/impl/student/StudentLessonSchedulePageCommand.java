package edu.gorb.musicstudio.controller.command.impl.student;

import edu.gorb.musicstudio.controller.command.*;
import edu.gorb.musicstudio.dto.LessonScheduleDto;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.LessonScheduleService;
import edu.gorb.musicstudio.model.service.ServiceProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public class StudentLessonSchedulePageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        LessonScheduleService lessonScheduleService = ServiceProvider.getInstance().getLessonScheduleService();
        HttpSession session = request.getSession();
        User student = (User) session.getAttribute(SessionAttribute.USER);
        List<String> lessonScheduleDateLines;
        Map<String, List<LessonScheduleDto>> lessonScheduleDtoMap;
        try {
            List<LessonScheduleDto> lessonScheduleDtos =
                    lessonScheduleService.findActiveFutureSchedulesByStudentId(student.getId());

            lessonScheduleDtoMap = lessonScheduleService.mapLessonDtosToDate(lessonScheduleDtos);
            lessonScheduleDateLines = lessonScheduleService.findDistinctDateLines(lessonScheduleDtos);
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        request.setAttribute(RequestAttribute.LESSON_SCHEDULE_DATES, lessonScheduleDateLines);
        request.setAttribute(RequestAttribute.LESSON_SCHEDULE_MAP, lessonScheduleDtoMap);
        return new CommandResult(PagePath.STUDENT_LESSON_SCHEDULE_PAGE, CommandResult.RoutingType.FORWARD);
    }
}
