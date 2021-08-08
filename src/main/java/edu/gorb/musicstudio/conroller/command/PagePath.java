package edu.gorb.musicstudio.conroller.command;

public class PagePath {
    public static final String HOME_PAGE = "WEB-INF/pages/home.jsp";
    public static final String HOME_PAGE_REDIRECT = "/controller?command=home_page";
    public static final String LOGIN_PAGE = "WEB-INF/pages/login.jsp";
    public static final String LOGIN_PAGE_REDIRECT = "/controller?command=go_to_login_page";
    public static final String SIGN_UP_PAGE = "WEB-INF/pages/signup.jsp";
    public static final String SIGN_UP_PAGE_REDIRECT = "/controller?command=go_to_sign_up_page";

    public static final String SEND_EMAIL_AGAIN_PAGE = "WEB-INF/pages/send_email_again.jsp";
    public static final String SEND_EMAIL_AGAIN_PAGE_REDIRECT = "/controller?command=go_to_send_email_again_page";

    public static final String COURSES_PAGE = "WEB-INF/pages/courses.jsp";
    public static final String TEACHERS_PAGE = "WEB-INF/pages/teachers.jsp";

    public static final String INFO_PAGE = "/static_pages/info.jsp";
    public static final String ERROR_PAGE = "/static_pages/error.jsp";
    public static final String ERROR_EMAIL_PAGE = "/static_pages/error_email.jsp";
    public static final String ERROR_404_PAGE = "/static_pages/error_404.jsp";
    public static final String ERROR_500_PAGE = "/static_pages/error_500.jsp";

    public static final String COURSE_PAGE = "WEB-INF/pages/course_page.jsp";
    public static final String TEACHER_PAGE = "WEB-INF/pages/teacher_page.jsp";

    private PagePath() {
    }
}
