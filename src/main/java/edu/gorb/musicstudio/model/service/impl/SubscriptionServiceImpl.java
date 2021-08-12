package edu.gorb.musicstudio.model.service.impl;

import edu.gorb.musicstudio.entity.LessonSchedule;
import edu.gorb.musicstudio.entity.Subscription;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.dao.DaoProvider;
import edu.gorb.musicstudio.model.dao.LessonScheduleDao;
import edu.gorb.musicstudio.model.dao.SubscriptionDao;
import edu.gorb.musicstudio.model.service.SubscriptionService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class SubscriptionServiceImpl implements SubscriptionService {
    private static final Logger logger = LogManager.getLogger();
    private static final int MAX_LESSON_PER_SUBSCRIPTION_COUNT = 8;
    private static final int DEFAULT_SUBSCRIPTION_LENGTH_DAYS = 30;

    @Override
    public int calcMaxLessonPerSubscriptionCount(int freeSlotCount) {
        return Math.min(freeSlotCount, MAX_LESSON_PER_SUBSCRIPTION_COUNT);
    }

    @Override
    public void saveSubscription(long courseId, long studentId, int lessonCount, Subscription.SubscriptionStatus status)
            throws ServiceException {
        SubscriptionDao subscriptionDao = DaoProvider.getInstance().getSubscriptionDao();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(DEFAULT_SUBSCRIPTION_LENGTH_DAYS);
        try {
            subscriptionDao.insert(new Subscription(0, studentId, courseId, startDate, endDate, status, lessonCount));
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while inserting subscription. {}", e.getMessage());
            throw new ServiceException("Error while inserting subscription", e);
        }
    }

    @Override
    public List<Subscription> findContinuingActiveStudentSubscriptions(long studentId) throws ServiceException {
        SubscriptionDao subscriptionDao = DaoProvider.getInstance().getSubscriptionDao();
        try {
            return subscriptionDao.findContinuingActiveStudentSubscriptions(studentId);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while searching for student subscriptions, student id={}. {}",
                    studentId, e.getMessage());
            throw new ServiceException("Error while searching for student subscriptions, student id=" + studentId);
        }
    }

    @Override
    public Optional<Subscription> findContinuingActiveSubscriptionById(long subscriptionId) throws ServiceException {
        SubscriptionDao subscriptionDao = DaoProvider.getInstance().getSubscriptionDao();
        try {
            return subscriptionDao.findContinuingActiveSubscriptionById(subscriptionId);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while searching for subscription, id={}. {}",
                    subscriptionId, e.getMessage());
            throw new ServiceException("Error while searching for subscription, id=" + subscriptionId);
        }
    }

    @Override
    public void updateStatus(long subscriptionId, Subscription.SubscriptionStatus status) throws ServiceException {
        SubscriptionDao subscriptionDao = DaoProvider.getInstance().getSubscriptionDao();
        try {
            subscriptionDao.updateStatus(subscriptionId, status);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while updating subscription status, id={}. {}",
                    subscriptionId, e.getMessage());
            throw new ServiceException("Error while updating subscription status, id=" + subscriptionId);
        }
    }


    @Override
    public boolean isNewCourseSubscriptionAvailable(long studentId, long courseId) throws ServiceException {
        LessonScheduleDao lessonScheduleDao = DaoProvider.getInstance().getLessonScheduleDao();
        SubscriptionDao subscriptionDao = DaoProvider.getInstance().getSubscriptionDao();
        Optional<Subscription> optionalSubscription;
        try {
            optionalSubscription = subscriptionDao.findContinuingActiveCourseSubscription(studentId, courseId);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while searching for student subscription, student id={}, {}",
                    studentId, e.getMessage());
            throw new ServiceException("Error while searching for student subscription, student id=" + studentId);
        }

        if (optionalSubscription.isEmpty()) {
            return true;
        }

        Subscription.SubscriptionStatus status = optionalSubscription.get().getStatus();
        if (status == Subscription.SubscriptionStatus.WAITING_FOR_APPROVE
                || status == Subscription.SubscriptionStatus.APPROVED) {
            return false;
        }

        List<LessonSchedule> lessonSchedules;
        try {
            lessonSchedules =
                    lessonScheduleDao.findActiveFutureSchedulesForStudentForCourse(studentId, courseId);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while selecting lesson schedules for course, student id={}. {}",
                    studentId, e.getMessage());
            throw new ServiceException("Error while selecting lesson schedules for course, student id=" + studentId);
        }
        return lessonSchedules.isEmpty();
    }
}
