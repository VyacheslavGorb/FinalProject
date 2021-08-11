package edu.gorb.musicstudio.model.dao.impl;

import edu.gorb.musicstudio.entity.LessonSchedule;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.model.dao.JdbcHelper;
import edu.gorb.musicstudio.model.dao.LessonScheduleDao;
import edu.gorb.musicstudio.model.dao.mapper.impl.LessonScheduleRowMapper;
import edu.gorb.musicstudio.model.pool.ConnectionPool;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class LessonScheduleDaoImpl implements LessonScheduleDao {

    private static final String SELECT_ALL_LESSON_SCHEDULES =
            "SELECT id_schedule, id_student, id_teacher, id_course, date_time, duration, status, id_subscription\n" +
                    "FROM lesson_schedules\n" +
                    "         JOIN lesson_statuses ls on ls.id_lesson_status = lesson_schedules.id_lesson_status";

    private static final String SELECT_FUTURE_LESSONS_FOR_TEACHER =
            "SELECT id_schedule, id_student, id_teacher, id_course, date_time, duration, status, id_subscription\n" +
                    "FROM lesson_schedules\n" +
                    "         JOIN lesson_statuses ls on ls.id_lesson_status = lesson_schedules.id_lesson_status\n" +
                    "WHERE DATE(date_time) >= CURDATE() and TIME(date_time) > CURTIME()\n" +
                    "  and id_teacher = ?";

    private static final String SELECT_FUTURE_LESSONS_FOR_STUDENT =
            "SELECT id_schedule, id_student, id_teacher, id_course, date_time, duration, status, id_subscription\n" +
                    "FROM lesson_schedules\n" +
                    "         JOIN lesson_statuses ls on ls.id_lesson_status = lesson_schedules.id_lesson_status\n" +
                    "WHERE DATE(date_time) >= CURDATE() and TIME(date_time) > CURTIME()\n" +
                    "  and id_student = ?";

    private static final String SELECT_SCHEDULE_FOR_DATE =
            "SELECT id_schedule, id_student, id_teacher, id_course, date_time, duration, status, id_subscription\n" +
                    "FROM lesson_schedules\n" +
                    "         JOIN lesson_statuses ls on ls.id_lesson_status = lesson_schedules.id_lesson_status\n" +
                    "WHERE id_teacher = ? and DATE(date_time) = ?";

    private static final String SELECT_SCHEDULE_BY_SUBSCRIPTION =
            "SELECT id_schedule, id_student, id_teacher, id_course, date_time, duration, status, id_subscription\n" +
                    "FROM lesson_schedules\n" +
                    "         JOIN lesson_statuses ls on ls.id_lesson_status = lesson_schedules.id_lesson_status\n" +
                    "WHERE id_subscription = ?";

    private static final String SELECT_FUTURE_LESSONS_FOR_STUDENT_FOR_COURSE =
            "SELECT id_schedule, id_student, id_teacher, id_course, date_time, duration, status, id_subscription\n" +
                    "FROM lesson_schedules\n" +
                    "         JOIN lesson_statuses ls on ls.id_lesson_status = lesson_schedules.id_lesson_status\n" +
                    "WHERE DATE(date_time) >= CURDATE() and TIME(date_time) > CURTIME()\n" +
                    "  and id_student = ?\n" +
                    "  and id_course = ?";

    private static final String INSERT_SCHEDULE =
            "INSERT INTO lesson_schedules (id_student, id_teacher, id_course, date_time, duration, id_subscription, id_lesson_status\n" +
                    "                              ) VALUE (?, ?, ?, ?, ?, ?, (SELECT lesson_statuses.id_lesson_status\n" +
                    "                                                                         FROM lesson_statuses\n" +
                    "                                                                         WHERE lesson_statuses.status = ?))";

    private static final String SELECT_SCHEDULE_BY_ID =
            "SELECT id_schedule,\n" +
                    "       id_student,\n" +
                    "       id_teacher,\n" +
                    "       id_course,\n" +
                    "       date_time,\n" +
                    "       duration,\n" +
                    "       status,\n" +
                    "       id_subscription\n" +
                    "FROM lesson_schedules\n" +
                    "         JOIN lesson_statuses ls on ls.id_lesson_status = lesson_schedules.id_lesson_status\n" +
                    "WHERE id_schedule = ?";

    private static final String UPDATE_STATUS =
            "UPDATE lesson_schedules\n" +
                    "SET id_lesson_status = (SELECT id_lesson_status FROM lesson_statuses WHERE lesson_statuses.status = ?)\n" +
                    "WHERE id_schedule =?";

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
        return jdbcHelper.executeQueryForSingleResult(SELECT_SCHEDULE_BY_ID, id);
    }

    @Override
    public int insert(LessonSchedule lessonSchedule) throws DaoException {
        return jdbcHelper.executeInsert(INSERT_SCHEDULE,
                lessonSchedule.getStudentId(),
                lessonSchedule.getTeacherId(),
                lessonSchedule.getCourseId(),
                lessonSchedule.getStartDateTime(),
                lessonSchedule.getDuration(),
                lessonSchedule.getSubscriptionId(),
                lessonSchedule.getStatus().toString());
    }

    @Override
    public void update(LessonSchedule lessonSchedule) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<LessonSchedule> findFutureSchedulesForTeacher(long teacherId) throws DaoException {
        return jdbcHelper.executeQuery(SELECT_FUTURE_LESSONS_FOR_TEACHER, teacherId);
    }

    @Override
    public List<LessonSchedule> findFutureSchedulesForStudent(long studentId) throws DaoException {
        return jdbcHelper.executeQuery(SELECT_FUTURE_LESSONS_FOR_STUDENT, studentId);
    }

    @Override
    public List<LessonSchedule> findScheduleForTeacherForDate(long teacherId, LocalDate date) throws DaoException {
        return jdbcHelper.executeQuery(SELECT_SCHEDULE_FOR_DATE, teacherId, date);
    }

    @Override
    public List<LessonSchedule> findFutureSchedulesForStudentForCourse(long studentId, long courseId) throws DaoException {
        return jdbcHelper.executeQuery(SELECT_FUTURE_LESSONS_FOR_STUDENT_FOR_COURSE, studentId, courseId);
    }

    @Override
    public List<LessonSchedule> findLessonSchedulesBySubscription(long subscriptionId) throws DaoException {
        return jdbcHelper.executeQuery(SELECT_SCHEDULE_BY_SUBSCRIPTION, subscriptionId);
    }

    @Override
    public void updateStatus(long lessonId, LessonSchedule.LessonStatus status) throws DaoException {
        jdbcHelper.executeUpdate(UPDATE_STATUS, status.toString(), lessonId);
    }
}
