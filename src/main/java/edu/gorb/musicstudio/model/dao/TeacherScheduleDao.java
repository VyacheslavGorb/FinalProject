package edu.gorb.musicstudio.model.dao;

import edu.gorb.musicstudio.entity.TeacherSchedule;
import edu.gorb.musicstudio.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * Dao for teacher_schedules table
 */
public interface TeacherScheduleDao extends BaseDao<TeacherSchedule> {
    /**
     * Find schedules for teacher
     *
     * @param teacherId teacher id
     * @return list of {@link TeacherSchedule} or empty list if no entities found
     * @throws DaoException is thrown when error while query execution occurs
     */
    List<TeacherSchedule> findSchedulesForTeacher(long teacherId) throws DaoException;

    /**
     * Find teacher schedule by teacher id for day of week
     *
     * @param teacherId teacher id
     * @param dayOfWeek day of week (1-Monday)
     * @return optional of {@link TeacherSchedule}
     * @throws DaoException is thrown when error while query execution occurs
     */
    Optional<TeacherSchedule> findScheduleForTeacher(long teacherId, int dayOfWeek) throws DaoException;

    /**
     * Remove teacher schedule
     *
     * @param teacherId teacher id
     * @param dayOfWeek day of week
     * @throws DaoException is thrown when error while query execution occurs
     */
    void removeSchedule(long teacherId, int dayOfWeek) throws DaoException;
}
