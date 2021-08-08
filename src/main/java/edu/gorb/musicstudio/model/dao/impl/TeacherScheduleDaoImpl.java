package edu.gorb.musicstudio.model.dao.impl;

import edu.gorb.musicstudio.entity.TeacherSchedule;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.model.dao.JdbcHelper;
import edu.gorb.musicstudio.model.dao.TeacherScheduleDao;
import edu.gorb.musicstudio.model.dao.mapper.impl.TeacherScheduleRowMapperImpl;
import edu.gorb.musicstudio.model.pool.ConnectionPool;

import java.util.List;
import java.util.Optional;

public class TeacherScheduleDaoImpl implements TeacherScheduleDao {

    private static final String SELECT_ALL_SCHEDULES =
            "SELECT id_teacher, day_of_week, interval_start, interval_end\n" +
                    "FROM teacher_schedules\n" +
                    "ORDER BY day_of_week";

    private final JdbcHelper<TeacherSchedule> jdbcHelper;

    public TeacherScheduleDaoImpl(){
        jdbcHelper = new JdbcHelper<>(ConnectionPool.getInstance(), new TeacherScheduleRowMapperImpl());
    }

    @Override
    public List<TeacherSchedule> findAll() throws DaoException {
        return jdbcHelper.executeQuery(SELECT_ALL_SCHEDULES);
    }

    @Override
    public Optional<TeacherSchedule> findEntityById(long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public int insert(TeacherSchedule teacherSchedule) throws DaoException {
        return 0;
    }

    @Override
    public void update(TeacherSchedule teacherSchedule) throws DaoException {

    }
}
