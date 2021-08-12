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

    public static final String TEACHER_INIT_PAGE = "WEB-INF/pages/teacher/teacher_init_page.jsp";
    public static final String TEACHER_INIT_PAGE_REDIRECT = "/controller?command=teacher_init_page";

    public static final String TEACHER_LESSON_SCHEDULE_PAGE = "WEB-INF/pages/teacher/teacher_lesson_schedule_page.jsp";
    public static final String TEACHER_LESSON_SCHEDULE_PAGE_REDIRECT = "/controller?command=teacher_lesson_schedule";

    public static final String TEACHER_SCHEDULE_PAGE = "WEB-INF/pages/teacher/teacher_schedule.jsp";
    public static final String TEACHER_SCHEDULE_PAGE_REDIRECT = "/controller?command=teacher_schedule";

    public static final String TEACHER_PERSONAL_INFO = "WEB-INF/pages/teacher/teacher_personal_info.jsp";
    public static final String TEACHER_PERSONAL_INFO_REDIRECT = "/controller?command=teacher_personal_info";

    public static final String SUBSCRIPTION_PAGE = "WEB-INF/pages/student/subscription_page.jsp";
    public static final String SUBSCRIPTION_PAGE_REDIRECT = "/controller?command=subscription_page&course_id=";

    public static final String PERSONAL_SUBSCRIPTIONS_PAGE = "WEB-INF/pages/student/personal_subscriptions.jsp";
    public static final String PERSONAL_SUBSCRIPTIONS_PAGE_REDIRECT = "/controller?command=personal_subscriptions";

    public static final String CHOOSE_LESSON_TIMEDATE_PAGE = "WEB-INF/pages/student/choose_lesson_timedate.jsp";

    public static final String STUDENT_LESSON_SCHEDULE_PAGE = "WEB-INF/pages/student/student_lesson_schedule.jsp";
    public static final String STUDENT_LESSON_SCHEDULE_PAGE_REDIRECT = "/controller?command=student_lesson_schedule";

    public static final String MANAGE_USERS = "WEB-INF/pages/admin/manage_users.jsp";
    public static final String MANAGE_USERS_REDIRECT = "/controller?command=manage_users_page";

    public static final String ALL_LESSONS = "WEB-INF/pages/admin/all_lessons.jsp";
    public static final String ALL_LESSONS_REDIRECT = "/controller?command=all_lessons_page";

    public static final String ALL_SUBSCRIPTIONS = "WEB-INF/pages/admin/all_subscriptions.jsp";
    public static final String ALL_SUBSCRIPTIONS_REDIRECT = "/controller?command=all_subscriptions_page";

    public static final String ALL_COURSES = "WEB-INF/pages/admin/all_courses.jsp";
    public static final String ALL_COURSES_REDIRECT = "/controller?command=all_courses_page";

    public static final String ADD_COURSE = "WEB-INF/pages/admin/add_course.jsp";
    public static final String ADD_COURSE_REDIRECT = "/controller?command=add_course_page";

    private PagePath() {
    }
}
