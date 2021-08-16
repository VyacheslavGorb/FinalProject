package edu.gorb.musicstudio.model.service.impl;

import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.entity.LessonSchedule;
import edu.gorb.musicstudio.entity.Subscription;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.entity.dto.SubscriptionDto;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.dao.*;
import edu.gorb.musicstudio.model.service.SubscriptionService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SubscriptionServiceImpl implements SubscriptionService {
    private static final Logger logger = LogManager.getLogger();
    private static final int MAX_LESSON_PER_SUBSCRIPTION_COUNT = 8;
    private static final int DEFAULT_SUBSCRIPTION_LENGTH_DAYS = 30;

    @Override
    public List<SubscriptionDto> findAllContinuingActiveSubscriptions() throws ServiceException {
        SubscriptionDao subscriptionDao = DaoProvider.getInstance().getSubscriptionDao();
        try {
            List<Subscription> subscriptions = subscriptionDao.findAllContinuingActiveSubscriptions();
            return createSubscriptionDtosFromSubscriptions(subscriptions);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while selecting all active subscriptions. {}", e.getMessage());
            throw new ServiceException("Error while selecting all active subscriptions.", e);
        }
    }

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
    public List<SubscriptionDto> findContinuingActiveStudentSubscriptions(long studentId) throws ServiceException {
        SubscriptionDao subscriptionDao = DaoProvider.getInstance().getSubscriptionDao();
        try {
            List<Subscription> subscriptions = subscriptionDao.findContinuingActiveStudentSubscriptions(studentId);
            return createSubscriptionDtosFromSubscriptions(subscriptions);
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
    public int findPotentiallyBusySlotCountForCourse(long courseId) throws ServiceException {
        SubscriptionDao subscriptionDao = DaoProvider.getInstance().getSubscriptionDao();
        LessonScheduleDao lessonScheduleDao = DaoProvider.getInstance().getLessonScheduleDao();
        try {
            List<Subscription> subscriptions = subscriptionDao.findContinuingActiveSubscriptionsForCourse(courseId)
                    .stream()
                    .filter(subscription -> subscription.getStatus() != Subscription.SubscriptionStatus.CANCELLED
                            && subscription.getStatus() != Subscription.SubscriptionStatus.ACTIVATED)
                    .collect(Collectors.toList());
            int totalSubscriptionLessonCount = subscriptions.stream().mapToInt(Subscription::getLessonCount).sum();
            int activatedLessonCount = 0;
            for (Subscription subscription : subscriptions) {
                activatedLessonCount += lessonScheduleDao.findLessonSchedulesBySubscriptionId(subscription.getId()).size();
            }
            return totalSubscriptionLessonCount - activatedLessonCount;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while searching for potentially busy slots for course id={}. {}",
                    courseId, e.getMessage());
            throw new ServiceException("Error while searching for potentially busy slots for course id=" + courseId);
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


    private List<SubscriptionDto> createSubscriptionDtosFromSubscriptions(List<Subscription> subscriptions)
            throws ServiceException {
        UserDao userDao = DaoProvider.getInstance().getUserDao();
        CourseDao courseDao = DaoProvider.getInstance().getCourseDao();
        List<SubscriptionDto> subscriptionDtos = new ArrayList<>();
        for (Subscription subscription : subscriptions) {
            try {
                Optional<User> optionalStudent = userDao.findEntityById(subscription.getStudentId());
                Optional<Course> optionalCourse = courseDao.findEntityById(subscription.getCourseId());
                if (optionalCourse.isEmpty() || optionalStudent.isEmpty()) {
                    logger.log(Level.ERROR, "Lesson schedule contains invalid data, schedule id={}",
                            subscription.getId());
                    throw new ServiceException("Lesson schedule contains invalid data, schedule id="
                            + subscription.getId());
                }
                SubscriptionDto subscriptionDto =
                        createSubscription(subscription, optionalStudent.get(), optionalCourse.get());
                subscriptionDtos.add(subscriptionDto);
            } catch (DaoException e) {
                logger.log(Level.ERROR, "Error while searching for entity by id. {}", e.getMessage());
                throw new ServiceException("Error while searching for entity by id. {}", e);
            }
        }
        return subscriptionDtos;
    }


    private SubscriptionDto createSubscription(Subscription subscription, User student, Course course) {
        return new SubscriptionDto.Builder()
                .setSubscriptionId(subscription.getId())
                .setCourseId(course.getId())
                .setStartDate(subscription.getStartDate())
                .setEndDate(subscription.getEndDate())
                .setStatus(subscription.getStatus())
                .setLessonCount(subscription.getLessonCount())
                .setCourseName(course.getName())
                .setStudentName(student.getName())
                .setStudentSurname(student.getSurname())
                .build();
    }

}
