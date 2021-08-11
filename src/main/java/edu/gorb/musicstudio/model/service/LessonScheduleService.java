package edu.gorb.musicstudio.model.service;

import edu.gorb.musicstudio.dto.LessonScheduleDto;
import edu.gorb.musicstudio.entity.LessonSchedule;
import edu.gorb.musicstudio.exception.ServiceException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LessonScheduleService {

    Optional<LessonSchedule> findEntityById(long lessonId) throws ServiceException;

    void updateStatus(long lessonId, LessonSchedule.LessonStatus status) throws ServiceException;

    List<LessonScheduleDto> findFutureSchedulesByTeacherId(long teacherId) throws ServiceException;

    List<LessonScheduleDto> findFutureSchedulesByStudentId(long studentId) throws ServiceException;

    Map<String, List<LessonScheduleDto>> mapLessonDtosToByDate(List<LessonScheduleDto> lessonScheduleDtos);

    List<String> findDistinctDateLines(List<LessonScheduleDto> lessonScheduleDtos);

    List<LessonSchedule> findTeacherLessonsForDate(long teacherId, LocalDate date) throws ServiceException;

    List<LessonScheduleDto> findLessonSchedulesBySubscription(long subscriptionId) throws ServiceException;

    void saveNewLessonSchedule(long studentId, long teacherId, long courseId, long subscriptionId,
                               LocalDateTime startDateTime, LessonSchedule.LessonStatus status)
            throws ServiceException;
}
