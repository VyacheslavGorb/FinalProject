package edu.gorb.musicstudio.model.dao.impl;

import edu.gorb.musicstudio.entity.Subscription;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.model.dao.JdbcHelper;
import edu.gorb.musicstudio.model.dao.SubscriptionDao;
import edu.gorb.musicstudio.model.dao.mapper.impl.SubscriptionRowMapperImpl;
import edu.gorb.musicstudio.model.pool.ConnectionPool;

import java.util.List;
import java.util.Optional;

public class SubscriptionDaoImpl implements SubscriptionDao {

    private static final String SELECT_ALL_SUBSCRIPTIONS =
            "SELECT id_subscription, id_student, id_course, date_start, date_end, lesson_amount, status\n" +
                    "FROM subscriptions";

    private static final String SELECT_CONTINUING_ACTIVE_SUBSCRIPTION_BY_ID =
            "SELECT id_subscription, id_student, id_course, date_start, date_end, lesson_amount, status\n" +
                    "FROM subscriptions\n" +
                    "WHERE DATE(date_end) >= CURDATE() and id_subscription = ? and status != 'INTERRUPTED'";

    private static final String INSERT_NEW_SUBSCRIPTION =
            "INSERT INTO subscriptions (id_student, id_course, date_start, date_end, lesson_amount, status)\n" +
                    "    VALUE (?, ?, ?, ?, ?, ?)";

    private static final String SELECT_CONTINUING_ACTIVE_SUBSCRIPTION_FOR_STUDENT_FOR_COURSE =
            "SELECT id_subscription, id_student, id_course, date_start, date_end, lesson_amount, status\n" +
                    "FROM subscriptions\n" +
                    "WHERE DATE(date_end) >= CURDATE()\n" +
                    "  and id_student = ?\n" +
                    "  and id_course = ? and status != 'INTERRUPTED'";

    private static final String SELECT_CONTINUING_ACTIVE_SUBSCRIPTIONS_FOR_STUDENT =
            "SELECT id_subscription, id_student, id_course, date_start, date_end, lesson_amount, status\n" +
                    "FROM subscriptions\n" +
                    "WHERE DATE(date_end) >= CURDATE()\n" +
                    "  and id_student = ? and status != 'INTERRUPTED'";

    private static final String UPDATE_STATUS =
            "UPDATE subscriptions\n" +
                    "SET status = ?\n" +
                    "WHERE id_subscription = ?";


    private final JdbcHelper<Subscription> jdbcHelper;

    public SubscriptionDaoImpl() {
        jdbcHelper = new JdbcHelper<>(ConnectionPool.getInstance(), new SubscriptionRowMapperImpl());
    }

    @Override
    public List<Subscription> findAll() throws DaoException {
        return jdbcHelper.executeQuery(SELECT_ALL_SUBSCRIPTIONS);
    }

    @Override
    public Optional<Subscription> findEntityById(long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int insert(Subscription subscription) throws DaoException {
        return jdbcHelper.executeInsert(INSERT_NEW_SUBSCRIPTION,
                subscription.getStudentId(),
                subscription.getCourseId(),
                subscription.getStartDate(),
                subscription.getEndDate(),
                subscription.getLessonCount(),
                subscription.getStatus().toString());
    }

    @Override
    public void update(Subscription subscription) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Subscription> findContinuingActiveCourseSubscription(long studentId, long courseId) throws DaoException {
        return jdbcHelper.executeQueryForSingleResult(SELECT_CONTINUING_ACTIVE_SUBSCRIPTION_FOR_STUDENT_FOR_COURSE,
                studentId, courseId);
    }

    @Override
    public List<Subscription> findContinuingActiveStudentSubscriptions(long studentId) throws DaoException {
        return jdbcHelper.executeQuery(SELECT_CONTINUING_ACTIVE_SUBSCRIPTIONS_FOR_STUDENT,
                studentId);
    }

    @Override
    public Optional<Subscription> findContinuingActiveSubscriptionById(long subscriptionId) throws DaoException {
        return jdbcHelper.executeQueryForSingleResult(SELECT_CONTINUING_ACTIVE_SUBSCRIPTION_BY_ID, subscriptionId);
    }

    @Override
    public void updateStatus(long subscriptionId, Subscription.SubscriptionStatus status) throws DaoException {
        jdbcHelper.executeUpdate(UPDATE_STATUS, status.toString(), subscriptionId);
    }
}
