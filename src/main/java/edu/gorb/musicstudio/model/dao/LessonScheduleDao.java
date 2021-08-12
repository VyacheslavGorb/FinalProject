package edu.gorb.musicstudio.model.dao;

import edu.gorb.musicstudio.entity.LessonSchedule;
import edu.gorb.musicstudio.exception.DaoException;

import java.time.LocalDate;
import java.util.List;

public interface LessonScheduleDao extends BaseDao<LessonSchedule> {
    List<LessonSchedule> findActiveFutureSchedulesForTeacher(long teacherId) throws DaoException;

    List<LessonSchedule> findActiveFutureSchedulesForStudent(long studentId) throws DaoException;

    List<LessonSchedule> findActiveScheduleForTeacherForDate(long teacherId, LocalDate date) throws DaoException;

    List<LessonSchedule> findActiveFutureSchedulesForStudentForCourse(long studentId, long courseId) throws DaoException;

    List<LessonSchedule> findLessonSchedulesBySubscription(long subscriptionId) throws DaoException;

    List<LessonSchedule> findAllActiveFutureSchedules() throws DaoException;

    void updateStatus(long lessonId, LessonSchedule.LessonStatus status) throws DaoException;
}
