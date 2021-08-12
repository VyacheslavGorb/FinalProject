package edu.gorb.musicstudio.model.service;

import edu.gorb.musicstudio.dto.SubscriptionDto;
import edu.gorb.musicstudio.entity.Subscription;
import edu.gorb.musicstudio.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface SubscriptionService {
    List<SubscriptionDto> findAllContinuingActiveSubscriptions() throws ServiceException;

    List<SubscriptionDto> findContinuingActiveStudentSubscriptions(long studentId) throws ServiceException;

    int calcMaxLessonPerSubscriptionCount(int freeSlotCount);

    void saveSubscription(long courseId, long studentId, int lessonCount, Subscription.SubscriptionStatus status)
            throws ServiceException;

    boolean isNewCourseSubscriptionAvailable(long studentId, long courseId) throws ServiceException;

    Optional<Subscription> findContinuingActiveSubscriptionById(long subscriptionId) throws ServiceException;

    void updateStatus(long subscriptionId, Subscription.SubscriptionStatus status) throws ServiceException;

    int findPotentiallyBusySlotCountForCourse(long courseId) throws ServiceException;
}
