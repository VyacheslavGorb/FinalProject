package edu.gorb.musicstudio.model.dao.impl;

import edu.gorb.musicstudio.entity.LessonSchedule;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.model.dao.JdbcHelper;
import edu.gorb.musicstudio.model.dao.LessonScheduleDao;
import edu.gorb.musicstudio.model.dao.mapper.impl.LessonScheduleRowMapper;
import edu.gorb.musicstudio.model.pool.ConnectionPool;

import java.util.List;
import java.util.Optional;

public class LessonScheduleDaoImpl implements LessonScheduleDao {

    private static final String SELECT_ALL_LESSON_SCHEDULES =
            "SELECT id_schedule, id_student, id_teacher, id_course, date_time, duration, status\n" +
                    "FROM lesson_schedules\n" +
                    "         JOIN lesson_statuses ls on ls.id_lesson_status = lesson_schedules.id_lesson_status";

    private static final String SELECT_FUTURE_LESSONS_FOR_TEACHER =
            "SELECT id_schedule, id_student, id_teacher, id_course, date_time, duration, status\n" +
                    "FROM lesson_schedules\n" +
                    "         JOIN lesson_statuses ls on ls.id_lesson_status = lesson_schedules.id_lesson_status\n" +
                    "WHERE DATE(date_time) >= CURDATE()\n" +
                    "  and id_teacher = ?";

    private final JdbcHelper<LessonSchedule> jdbcHelper;

    public LessonScheduleDaoImpl() {
        jdbcHelper = new JdbcHelper<>(ConnectionPool.getInstance(), new LessonScheduleRowMapper());
    }

    @Override
    public List<LessonSchedule> findAll() throws DaoException {
        return jdbcHelper.executeQuery(SELECT_ALL_LESSON_SCHEDULES);
    }

    @Override
    public Optional<LessonSchedule> findEntityById(long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int insert(LessonSchedule lessonSchedule) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(LessonSchedule lessonSchedule) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<LessonSchedule> findFutureSchedulesForTeacher(long teacherId) throws DaoException {
        return jdbcHelper.executeQuery(SELECT_FUTURE_LESSONS_FOR_TEACHER, teacherId);
    }
}
