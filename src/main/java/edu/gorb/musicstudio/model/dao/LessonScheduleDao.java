package edu.gorb.musicstudio.model.dao;

import edu.gorb.musicstudio.entity.LessonSchedule;
import edu.gorb.musicstudio.exception.DaoException;

import java.util.List;

public interface LessonScheduleDao extends BaseDao<LessonSchedule>{
    List<LessonSchedule> findFutureSchedulesForTeacher(long teacherId) throws DaoException;
}
