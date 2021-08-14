package edu.gorb.musicstudio.model.service;

import edu.gorb.musicstudio.entity.LessonSchedule;
import edu.gorb.musicstudio.entity.dto.LessonScheduleDto;
import edu.gorb.musicstudio.exception.ServiceException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LessonScheduleService {

    /**
     * Finds lesson schedule by id
     *
     * @param lessonId lesson schedule id
     * @return optional of {@link LessonSchedule}
     */
    Optional<LessonSchedule> findEntityById(long lessonId) throws ServiceException;

    /**
     * Updates lesson schedule status
     *
     * @param lessonId lesson schedule id
     * @param status   lesson schedule status
     */
    void updateStatus(long lessonId, LessonSchedule.LessonStatus status) throws ServiceException;

    /**
     * Finds active future lesson schedules by teacher id
     *
     * @param teacherId teacher id
     * @return list of {@link LessonScheduleDto} or empty list if no entities found
     */
    List<LessonScheduleDto> findActiveFutureSchedulesByTeacherId(long teacherId) throws ServiceException;

    /**
     * Finds active future lesson schedules by student id
     *
     * @param studentId student id
     * @return list of {@link LessonScheduleDto} or empty list if no entities found
     */
    List<LessonScheduleDto> findActiveFutureSchedulesByStudentId(long studentId) throws ServiceException;

    /**
     * Finds active future lesson schedules
     *
     * @return list of {@link LessonScheduleDto} or empty list if no entities found
     */
    List<LessonScheduleDto> findAllActiveFutureSchedules() throws ServiceException;

    /**
     * Maps lesson schedules to dates
     *
     * @param lessonScheduleDtos list of lesson schedules
     * @return lesson schedules mapped to dates
     */
    Map<String, List<LessonScheduleDto>> mapLessonDtosToDate(List<LessonScheduleDto> lessonScheduleDtos);

    /**
     * Finds distinct date lines
     *
     * @param lessonScheduleDtos list of {@link LocalDate} entities
     * @return list of distinct strings representing dates
     */
    List<String> findDistinctDateLines(List<LessonScheduleDto> lessonScheduleDtos);

    /**
     * Finds active teacher lessons for date
     *
     * @param teacherId teacher id
     * @param date      date
     * @return list of {@link LessonSchedule} or empty list if no entities found
     */
    List<LessonSchedule> findActiveTeacherLessonsForDate(long teacherId, LocalDate date) throws ServiceException;

    /**
     * Finds lesson schedules by subscription id
     *
     * @param subscriptionId subscription id
     * @return list of {@link LessonScheduleDto} or empty list if no entities found
     */
    List<LessonScheduleDto> findLessonSchedulesBySubscriptionId(long subscriptionId) throws ServiceException;

    /**
     * Saves new lesson schedule
     *
     * @param studentId      student id
     * @param teacherId      teacher id
     * @param courseId       course id
     * @param subscriptionId subscription id
     * @param startDateTime  schedule start timestamp
     * @param status         lesson schedule status
     */
    void saveNewLessonSchedule(long studentId, long teacherId, long courseId, long subscriptionId,
                               LocalDateTime startDateTime, LessonSchedule.LessonStatus status)
            throws ServiceException;
}
