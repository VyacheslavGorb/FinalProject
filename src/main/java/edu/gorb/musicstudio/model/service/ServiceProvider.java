package edu.gorb.musicstudio.model.service;

import edu.gorb.musicstudio.model.service.impl.*;

/**
 * Class provides services
 */
public class ServiceProvider {
    private final UserService userService = new UserServiceImpl();
    private final MailService mailService = new MailServiceImpl();
    private final CourseService courseService = new CourseServiceImpl();
    private final CommentService commentService = new CommentServiceImpl();
    private final TeacherDescriptionService teacherDescriptionService = new TeacherDescriptionServiceImpl();
    private final LessonScheduleService lessonScheduleService = new LessonScheduleServiceImpl();
    private final TeacherScheduleService teacherScheduleService = new TeacherScheduleServiceImpl();
    private final SubscriptionService scheduleService = new SubscriptionServiceImpl();

    private ServiceProvider() {
    }

    public static ServiceProvider getInstance() {
        return ServiceProviderHolder.instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public MailService getMailService() {
        return mailService;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public CommentService getCommentService() {
        return commentService;
    }

    public TeacherDescriptionService getTeacherDescriptionService() {
        return teacherDescriptionService;
    }

    private static class ServiceProviderHolder {
        private static final ServiceProvider instance = new ServiceProvider();
    }

    public TeacherScheduleService getTeacherScheduleService() {
        return teacherScheduleService;
    }

    public LessonScheduleService getLessonScheduleService() {
        return lessonScheduleService;
    }

    public SubscriptionService getScheduleService() {
        return scheduleService;
    }
}
