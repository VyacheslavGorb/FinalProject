package edu.gorb.musicstudio.model.service;

import edu.gorb.musicstudio.entity.Subscription;
import edu.gorb.musicstudio.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface SubscriptionService {
    int calcMaxLessonPerSubscriptionCount(int freeSlotCount);

    void saveSubscription(long courseId, long studentId, int lessonCount, Subscription.SubscriptionStatus status)
            throws ServiceException;

    boolean isNewCourseSubscriptionAvailable(long studentId, long courseId) throws ServiceException;

    List<Subscription> findAllCurrentStudentSubscriptions(long studentId) throws ServiceException;

    Optional<Subscription> findActiveSubscriptionById(long subscriptionId) throws ServiceException;
}
