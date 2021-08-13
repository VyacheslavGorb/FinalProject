package edu.gorb.musicstudio.controller.command.impl.admin;

import edu.gorb.musicstudio.controller.command.Command;
import edu.gorb.musicstudio.controller.command.CommandResult;
import edu.gorb.musicstudio.controller.command.PagePath;
import edu.gorb.musicstudio.controller.command.RequestAttribute;
import edu.gorb.musicstudio.dto.LessonScheduleDto;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.LessonScheduleService;
import edu.gorb.musicstudio.model.service.ServiceProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class AllLessonsPageCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        LessonScheduleService lessonScheduleService = ServiceProvider.getInstance().getLessonScheduleService();
        List<String> lessonScheduleDateLines;
        Map<String, List<LessonScheduleDto>> lessonScheduleDtoMap;
        try {
            List<LessonScheduleDto> lessonScheduleDtos =
                    lessonScheduleService.findAllActiveFutureSchedules();
            lessonScheduleDtoMap = lessonScheduleService.mapLessonDtosToDate(lessonScheduleDtos);
            lessonScheduleDateLines = lessonScheduleService.findDistinctDateLines(lessonScheduleDtos);
        } catch (ServiceException e) {
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }
        request.setAttribute(RequestAttribute.LESSON_SCHEDULE_DATES, lessonScheduleDateLines);
        request.setAttribute(RequestAttribute.LESSON_SCHEDULE_MAP, lessonScheduleDtoMap);
        return new CommandResult(PagePath.ALL_LESSONS, CommandResult.RoutingType.FORWARD);
    }
}
