package edu.gorb.musicstudio.model.dao;

import edu.gorb.musicstudio.entity.TeacherSchedule;
import edu.gorb.musicstudio.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface TeacherScheduleDao extends BaseDao<TeacherSchedule>{
    List<TeacherSchedule> findSchedulesForTeacher(long teacherId) throws DaoException;

    Optional<TeacherSchedule> findScheduleForTeacher(long teacherId, int dayOfWeek) throws DaoException;

    void removeSchedule(long teacherId, int dayOfWeek) throws DaoException;
}
