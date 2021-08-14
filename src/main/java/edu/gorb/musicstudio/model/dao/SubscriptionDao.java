package edu.gorb.musicstudio.model.dao;

import edu.gorb.musicstudio.entity.Subscription;
import edu.gorb.musicstudio.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * Dao for subscriptions table
 */
public interface SubscriptionDao extends BaseDao<Subscription> {
    /**
     * Finds all continuing active subscriptions
     *
     * @return list of {@link Subscription} or empty list if no entities found
     * @throws DaoException is thrown when error while query execution occurs
     */
    List<Subscription> findAllContinuingActiveSubscriptions() throws DaoException;

    /**
     * Find s continuing active course subscriptions for student
     *
     * @param studentId student id
     * @param courseId  course id
     * @return optional of {@link Subscription}
     * @throws DaoException is thrown when error while query execution occurs
     */
    Optional<Subscription> findContinuingActiveCourseSubscription(long studentId, long courseId) throws DaoException;

    /**
     * Finds continuing active subscriptions by student id
     *
     * @param studentId student id
     * @return list of {@link Subscription} or empty list if no entities found
     * @throws DaoException is thrown when error while query execution occurs
     */
    List<Subscription> findContinuingActiveStudentSubscriptions(long studentId) throws DaoException;

    /**
     * Find continuing active subscription by id
     *
     * @param subscriptionId subscription id
     * @return optional of {@link Subscription}
     * @throws DaoException is thrown when error while query execution occurs
     */
    Optional<Subscription> findContinuingActiveSubscriptionById(long subscriptionId) throws DaoException;

    /**
     * Updates subscription status
     *
     * @param subscriptionId subscription if
     * @param status         subscription status
     * @throws DaoException is thrown when error while query execution occurs
     */
    void updateStatus(long subscriptionId, Subscription.SubscriptionStatus status) throws DaoException;

    /**
     * Find continuing active subscriptions by course id
     *
     * @param courseId course id
     * @return list of {@link Subscription} or empty list if no entities found
     * @throws DaoException is thrown when error while query execution occurs
     */
    List<Subscription> findContinuingActiveSubscriptionsForCourse(long courseId) throws DaoException;
}
