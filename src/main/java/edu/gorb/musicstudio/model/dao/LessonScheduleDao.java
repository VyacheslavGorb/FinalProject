package edu.gorb.musicstudio.model.dao;

import edu.gorb.musicstudio.entity.LessonSchedule;
import edu.gorb.musicstudio.exception.DaoException;

import java.time.LocalDate;
import java.util.List;

/**
 * Dao for lesson_schedules table
 */
public interface LessonScheduleDao extends BaseDao<LessonSchedule> {
    /**
     * Finds active future schedules by teacher id
     *
     * @param teacherId teacher id
     * @return list of {@link LessonSchedule} or empty list if no entities found
     * @throws DaoException is thrown when error while query execution occurs
     */
    List<LessonSchedule> findActiveFutureSchedulesForTeacher(long teacherId) throws DaoException;

    /**
     * Finds active future schedules by student id
     *
     * @param studentId student id
     * @return list of {@link LessonSchedule} or empty list if no entities found
     * @throws DaoException is thrown when error while query execution occurs
     */
    List<LessonSchedule> findActiveFutureSchedulesForStudent(long studentId) throws DaoException;

    /**
     * Finds active future schedules by teacher id for date
     *
     * @param teacherId teacher id
     * @param date      date
     * @return list of {@link LessonSchedule} or empty list if no entities found
     * @throws DaoException is thrown when error while query execution occurs
     */
    List<LessonSchedule> findActiveScheduleForTeacherForDate(long teacherId, LocalDate date) throws DaoException;

    /**
     * Finds active future schedules by student id for course
     *
     * @param studentId student id
     * @param courseId  course id
     * @return list of {@link LessonSchedule} or empty list if no entities found
     * @throws DaoException is thrown when error while query execution occurs
     */
    List<LessonSchedule> findActiveFutureSchedulesForStudentForCourse(long studentId, long courseId) throws DaoException;

    /**
     * Finds lesson schedules by subscription id
     *
     * @param subscriptionId subscription id
     * @return list of {@link LessonSchedule} or empty list if no entities found
     * @throws DaoException is thrown when error while query execution occurs
     */
    List<LessonSchedule> findLessonSchedulesBySubscriptionId(long subscriptionId) throws DaoException;

    /**
     * Finds all active future schedules
     *
     * @return list of {@link LessonSchedule} or empty list if no entities found
     * @throws DaoException is thrown when error while query execution occurs
     */
    List<LessonSchedule> findAllActiveFutureSchedules() throws DaoException;

    /**
     * Updates lesson schedule status
     *
     * @param lessonId lesson id
     * @param status   new status
     * @throws DaoException is thrown when error while query execution occurs
     */
    void updateStatus(long lessonId, LessonSchedule.LessonStatus status) throws DaoException;
}
