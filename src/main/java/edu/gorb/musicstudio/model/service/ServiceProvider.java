package edu.gorb.musicstudio.model.service;

import edu.gorb.musicstudio.model.service.impl.CommentServiceImpl;
import edu.gorb.musicstudio.model.service.impl.CourseServiceImpl;
import edu.gorb.musicstudio.model.service.impl.MailServiceImpl;
import edu.gorb.musicstudio.model.service.impl.UserServiceImpl;

public class ServiceProvider {
    private final UserService userService = new UserServiceImpl();
    private final MailService mailService = new MailServiceImpl();
    private final CourseService courseService = new CourseServiceImpl();
    private final CommentService commentService = new CommentServiceImpl();

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

    private static class ServiceProviderHolder {
        private static final ServiceProvider instance = new ServiceProvider();
    }
}
