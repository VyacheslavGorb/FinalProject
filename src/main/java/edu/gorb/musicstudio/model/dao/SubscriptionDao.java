package edu.gorb.musicstudio.model.dao;

import edu.gorb.musicstudio.entity.Subscription;
import edu.gorb.musicstudio.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface SubscriptionDao extends BaseDao<Subscription> {
    Optional<Subscription> findActiveCourseSubscription(long studentId, long courseId) throws DaoException;

    List<Subscription> findActiveStudentSubscriptions(long studentId) throws DaoException;

    Optional<Subscription> findActiveSubscriptionById(long subscriptionId) throws DaoException;

    void updateStatus(long subscriptionId, Subscription.SubscriptionStatus status) throws DaoException;
}
