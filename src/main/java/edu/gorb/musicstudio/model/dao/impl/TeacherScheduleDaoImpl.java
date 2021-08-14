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

    private static final String SELECT_SCHEDULES_FOR_TEACHER =
            "SELECT id_teacher, day_of_week, interval_start, interval_end FROM teacher_schedules\n" +
                    "WHERE id_teacher = ?\n" +
                    "ORDER BY day_of_week";

    private static final String SELECT_SCHEDULE_FOR_TEACHER_FOR_DAY =
            "SELECT id_teacher, day_of_week, interval_start, interval_end\n" +
                    "FROM teacher_schedules\n" +
                    "WHERE id_teacher =? and day_of_week =?";

    private static final String UPDATE_SCHEDULE =
            "UPDATE teacher_schedules\n" +
                    "SET day_of_week    =?,\n" +
                    "    interval_start =?,\n" +
                    "    interval_end   =?\n" +
                    "WHERE id_teacher = ? and day_of_week = ?";

    private static final String INSERT_SCHEDULE =
            "INSERT INTO teacher_schedules (id_teacher, day_of_week, interval_start, interval_end) VALUE (?, ?, ?, ?)";

    private static final String REMOVE_SCHEDULE =
            "DELETE\n" +
                    "FROM teacher_schedules\n" +
                    "WHERE id_teacher = ?\n" +
                    "  and day_of_week = ?";

    private final JdbcHelper<TeacherSchedule> jdbcHelper;

    public TeacherScheduleDaoImpl() {
        jdbcHelper = new JdbcHelper<>(ConnectionPool.getInstance(), new TeacherScheduleRowMapperImpl());
    }

    @Override
    public long insert(TeacherSchedule teacherSchedule) throws DaoException {
        jdbcHelper.executeUpdate(INSERT_SCHEDULE,
                teacherSchedule.getTeacherId(),
                teacherSchedule.getDayOfWeek(),
                teacherSchedule.getStartTime(),
                teacherSchedule.getEndTime());
        return -1;
    }

    @Override
    public void update(TeacherSchedule teacherSchedule) throws DaoException {
        jdbcHelper.executeUpdate(UPDATE_SCHEDULE,
                teacherSchedule.getDayOfWeek(),
                teacherSchedule.getStartTime(),
                teacherSchedule.getEndTime(),
                teacherSchedule.getTeacherId(),
                teacherSchedule.getDayOfWeek());
    }

    @Override
    public List<TeacherSchedule> findSchedulesForTeacher(long teacherId) throws DaoException {
        return jdbcHelper.executeQuery(SELECT_SCHEDULES_FOR_TEACHER, teacherId);
    }

    @Override
    public Optional<TeacherSchedule> findScheduleForTeacher(long teacherId, int dayOfWeek) throws DaoException {
        return jdbcHelper.executeQueryForSingleResult(SELECT_SCHEDULE_FOR_TEACHER_FOR_DAY, teacherId, dayOfWeek);
    }

    @Override
    public void removeSchedule(long teacherId, int dayOfWeek) throws DaoException {
        jdbcHelper.executeUpdate(REMOVE_SCHEDULE, teacherId, dayOfWeek);
    }

    @Override
    public List<TeacherSchedule> findAll() throws DaoException {
        throw new UnsupportedOperationException("findAll method is not implemented");
    }

    @Override
    public Optional<TeacherSchedule> findEntityById(long id) throws DaoException {
        throw new UnsupportedOperationException("findEntityById method is not implemented");
    }
}
