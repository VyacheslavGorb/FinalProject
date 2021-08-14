package edu.gorb.musicstudio.model.service;

import edu.gorb.musicstudio.entity.Subscription;
import edu.gorb.musicstudio.entity.dto.SubscriptionDto;
import edu.gorb.musicstudio.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface SubscriptionService {
    /**
     * Finds all continuing subscriptions
     *
     * @return list of {@link SubscriptionDto} or empty list if no entities found
     */
    List<SubscriptionDto> findAllContinuingActiveSubscriptions() throws ServiceException;

    /**
     * Finds active continuing subscriptions for student
     *
     * @param studentId student id
     * @return list of {@link SubscriptionDto} or empty list if no entities found
     */
    List<SubscriptionDto> findContinuingActiveStudentSubscriptions(long studentId) throws ServiceException;

    /**
     * Calculates maximal lessons for subscription count
     *
     * @param freeSlotCount flee slots for course
     * @return maximal lessons for subscription count
     */
    int calcMaxLessonPerSubscriptionCount(int freeSlotCount);

    /**
     * Saves new subscription
     *
     * @param courseId    course id
     * @param studentId   student id
     * @param lessonCount lesson count
     * @param status      subscription status
     */
    void saveSubscription(long courseId, long studentId, int lessonCount, Subscription.SubscriptionStatus status)
            throws ServiceException;

    /**
     * Checks if new course subscription is available for user
     *
     * @param studentId student id
     * @param courseId  course id
     * @return if new course subscription is available for user
     */
    boolean isNewCourseSubscriptionAvailable(long studentId, long courseId) throws ServiceException;

    /**
     * Finds active continuing subscription by id
     *
     * @param subscriptionId subscription id
     * @return optional of {@link Subscription}
     */
    Optional<Subscription> findContinuingActiveSubscriptionById(long subscriptionId) throws ServiceException;

    /**
     * Updates subscription status
     *
     * @param subscriptionId subscription id
     * @param status         new status
     */
    void updateStatus(long subscriptionId, Subscription.SubscriptionStatus status) throws ServiceException;

    /**
     * Finds potentially busy slot count for course
     *
     * @param courseId course id
     * @return potentially busy slot count for course
     */
    int findPotentiallyBusySlotCountForCourse(long courseId) throws ServiceException;
}
