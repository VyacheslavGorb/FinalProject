package edu.gorb.musicstudio.model.service;

import edu.gorb.musicstudio.dto.LessonScheduleDto;
import edu.gorb.musicstudio.entity.LessonSchedule;
import edu.gorb.musicstudio.exception.ServiceException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface LessonScheduleService {
    List<LessonScheduleDto> findFutureSchedulesByTeacherId(long teacherId) throws ServiceException;

    Map<String, List<LessonScheduleDto>> mapLessonDtosToByDate(List<LessonScheduleDto> lessonScheduleDtos);

    List<String> findDistinctDateLines(List<LessonScheduleDto> lessonScheduleDtos);

    List<LessonSchedule> findTeacherLessonsForDate(long teacherId, LocalDate date) throws ServiceException;

    List<LessonSchedule> findLessonSchedulesBySubscription(long subscriptionId) throws ServiceException;
}
