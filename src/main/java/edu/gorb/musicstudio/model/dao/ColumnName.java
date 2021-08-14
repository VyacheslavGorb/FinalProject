package edu.gorb.musicstudio.model.dao;

/**
 * Class provides string constants that represent data base column names
 */
public final class ColumnName {
    /**
     * Users table
     */
    public static final String USER_ID = "id_user";
    public static final String USER_LOGIN = "login";
    public static final String USER_PASSWORD = "password_hash";
    public static final String USER_NAME = "name";
    public static final String USER_SURNAME = "surname";
    public static final String USER_PATRONYMIC = "patronymic";
    public static final String USER_EMAIL = "email";
    public static final String USER_STATUS = "user_status";
    public static final String USER_ROLE = "user_role";

    /**
     * User tokens table
     */

    public static final String TOKEN_ID = "id_token";
    public static final String TOKEN_USER_ID = "id_user";
    public static final String TOKEN = "token";
    public static final String TOKEN_TIMESTAMP = "timestamp";


    /**
     * Comments table
     */
    public static final String COMMENT_ID = "id_comment";
    public static final String COMMENT_STUDENT_ID = "id_student";
    public static final String COMMENT_COURSE_ID = "id_course";
    public static final String COMMENT_CONTENT = "content";
    public static final String COMMENT_TIMESTAMP = "date_time";
    public static final String COMMENT_IS_ACTIVE = "is_active";


    /**
     * Courses table
     */
    public static final String COURSE_COURSE_ID = "id_course";
    public static final String COURSE_NAME = "name";
    public static final String COURSE_DESCRIPTION = "description";
    public static final String COURSE_PICTURE_PATH = "picture_path";
    public static final String COURSE_PRICE_PER_HOUR = "price_per_hour";
    public static final String COURSE_IS_ACTIVE = "is_active";
    public static final String COURSE_CATEGORY = "category";


    /**
     * Lesson schedules table
     */
    public static final String LESSON_ID = "id_schedule";
    public static final String LESSON_STATUS = "status";
    public static final String LESSON_TIMESTAMP = "date_time";
    public static final String LESSON_DURATION = "duration";
    public static final String LESSON_STUDENT_ID = "id_student";
    public static final String LESSON_TEACHER_ID = "id_teacher";
    public static final String LESSON_COURSE_ID = "id_course";

    /**
     * Subscriptions table
     */
    public static final String SUBSCRIPTION_ID = "id_subscription";
    public static final String SUBSCRIPTION_DATE_START = "date_start";
    public static final String SUBSCRIPTION_DATE_END = "date_end";
    public static final String SUBSCRIPTION_LESSON_AMOUNT = "lesson_amount";
    public static final String SUBSCRIPTION_STATUS = "status";


    /**
     * Teacher descriptions table
     */
    public static final String TEACHER_ID = "id_teacher";
    public static final String TEACHER_SELF_DESCRIPTION = "self_description";
    public static final String TEACHER_EXPERIENCE = "experience";
    public static final String TEACHER_PICTURE_PATH = "picture_path";

    /**
     * Teacher schedules table
     */
    public static final String TEACHES_SCHEDULE_DAY_OF_WEEK = "day_of_week";
    public static final String TEACHES_SCHEDULE_INTERVAL_START = "interval_start";
    public static final String TEACHES_SCHEDULE_INTERVAL_END = "interval_end";

    private ColumnName() {
    }
}
