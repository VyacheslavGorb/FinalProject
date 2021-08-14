package edu.gorb.musicstudio.model.service;

import edu.gorb.musicstudio.entity.TeacherSchedule;
import edu.gorb.musicstudio.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface TeacherScheduleService {
    /**
     * Finds all teacher schedules by teacher id
     *
     * @param teacherId teacher id
     * @return list of {@link TeacherSchedule} or empty list if no entities found
     */
    List<TeacherSchedule> findAllById(long teacherId) throws ServiceException;

    /**
     * Alter teacher schedule
     *
     * @param teacherId      teacher id
     * @param startTimeParam work start time
     * @param endTimeParam   work end time
     * @param dayOfWeek      day of week
     * @param isRemove       is schedule removed
     * @return if schedule successfully altered
     */
    boolean alterSchedule(long teacherId, String startTimeParam, String endTimeParam, int dayOfWeek, boolean isRemove)
            throws ServiceException;

    /**
     * Finds teacher schedule for date
     *
     * @param teacherId teacher id
     * @param dayOfWeek day of week
     * @return optional of {@link TeacherSchedule}
     */
    Optional<TeacherSchedule> findScheduleForDay(long teacherId, int dayOfWeek) throws ServiceException;
}
