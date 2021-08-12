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

    List<LessonScheduleDto> findActiveFutureSchedulesByTeacherId(long teacherId) throws ServiceException;

    List<LessonScheduleDto> findActiveFutureSchedulesByStudentId(long studentId) throws ServiceException;

    List<LessonScheduleDto> findAllActiveFutureSchedules() throws ServiceException;

    Map<String, List<LessonScheduleDto>> mapLessonDtosToDate(List<LessonScheduleDto> lessonScheduleDtos);

    List<String> findDistinctDateLines(List<LessonScheduleDto> lessonScheduleDtos);

    List<LessonSchedule> findActiveTeacherLessonsForDate(long teacherId, LocalDate date) throws ServiceException;

    List<LessonScheduleDto> findLessonSchedulesBySubscriptionId(long subscriptionId) throws ServiceException;

    void saveNewLessonSchedule(long studentId, long teacherId, long courseId, long subscriptionId,
                               LocalDateTime startDateTime, LessonSchedule.LessonStatus status)
            throws ServiceException;
}
