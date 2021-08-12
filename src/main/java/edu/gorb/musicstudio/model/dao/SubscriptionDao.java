package edu.gorb.musicstudio.model.dao;

import edu.gorb.musicstudio.entity.Subscription;
import edu.gorb.musicstudio.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface SubscriptionDao extends BaseDao<Subscription> {
    List<Subscription> findAllContinuingActiveSubscriptions() throws DaoException;

    Optional<Subscription> findContinuingActiveCourseSubscription(long studentId, long courseId) throws DaoException;

    List<Subscription> findContinuingActiveStudentSubscriptions(long studentId) throws DaoException;

    Optional<Subscription> findContinuingActiveSubscriptionById(long subscriptionId) throws DaoException;

    void updateStatus(long subscriptionId, Subscription.SubscriptionStatus status) throws DaoException;

    List<Subscription> findContinuingActiveSubscriptionsForCourse(long courseId) throws DaoException;
}
