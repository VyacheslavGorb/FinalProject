package edu.gorb.musicstudio.model.dao;

import edu.gorb.musicstudio.model.dao.impl.*;

/**
 * Provides Dao entities
 */
public class DaoProvider {
    private final UserDao userDao = new UserDaoImpl();
    private final UserTokenDao userTokenDao = new UserTokenDaoImpl();
    private final CourseDao courseDao = new CourseDaoImpl();
    private final TeacherDescriptionDao teacherDescriptionDao = new TeacherDescriptionDaoImpl();
    private final CommentDao commentDao = new CommentDaoImpl();
    private final LessonScheduleDao lessonScheduleDao = new LessonScheduleDaoImpl();
    private final TeacherScheduleDao teacherScheduleDao = new TeacherScheduleDaoImpl();
    private final SubscriptionDao subscriptionDao = new SubscriptionDaoImpl();

    private DaoProvider() {
    }

    public static DaoProvider getInstance() {
        return DaoProviderHolder.instance;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public UserTokenDao getUserTokenDao() {
        return userTokenDao;
    }

    public CourseDao getCourseDao() {
        return courseDao;
    }

    public CommentDao getCommentDao() {
        return commentDao;
    }

    public TeacherDescriptionDao getTeacherDescriptionDao() {
        return teacherDescriptionDao;
    }

    public LessonScheduleDao getLessonScheduleDao() {
        return lessonScheduleDao;
    }

    public TeacherScheduleDao getTeacherScheduleDao() {
        return teacherScheduleDao;
    }

    public SubscriptionDao getSubscriptionDao() {
        return subscriptionDao;
    }

    private static class DaoProviderHolder {
        private static final DaoProvider instance = new DaoProvider();
    }
}
