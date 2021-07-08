package edu.gorb.musicstudio.dao;

public class ColumnName {
    /*Users table*/
    public static final String USER_ID = "id_user";
    public static final String USER_LOGIN = "login";
    public static final String USER_PASSWORD = "password_hash";
    public static final String USER_NAME = "name";
    public static final String USER_SURNAME = "surname";
    public static final String USER_PATRONYMIC = "patronymic";
    public static final String USER_EMAIL = "email";
    public static final String USER_PHONE_NUMBER = "phone_number";
    public static final String USER_STATUS = "user_status";
    public static final String USER_ROLE = "user_role";

    /*Comments table*/
    public static final String COMMENT_ID = "id_comment";
    public static final String COMMENT_STUDENT_ID = "id_student";
    public static final String COMMENT_TEACHER_ID = "id_teacher";
    public static final String COMMENT_CONTENT = "content";
    public static final String COMMENT_TIMESTAMP = "date_time";


    /*Courses table*/
    public static final String COURSE_COURSE_ID = "id_course";
    public static final String COURSE_NAME = "name";
    public static final String COURSE_DESCRIPTION = "description";
    public static final String COURSE_PICTURE_PATH = "picture_path";
    public static final String COURSE_PRICE_PER_HOUR = "price_per_hour";
    public static final String COURSE_IS_ACTIVE = "is_active";

    /*Lesson schedules table*/
    public static final String LESSON_ID = "id_schedule";
    public static final String LESSON_STATUS = "id_lesson_status";
    public static final String LESSON_TIMESTAMP = "date_time";
    public static final String LESSON_DURATION = "duration";

    /*Subscriptions table*/
    public static final String SUBSCRIPTION_ID = "id_subscriptions";
    public static final String SUBSCRIPTION_DATE_START = "date_start";
    public static final String SUBSCRIPTION_DATE_END = "date_end";
    public static final String SUBSCRIPTION_LESSON_AMOUNT = "lesson_amount";
    public static final String SUBSCRIPTION_LESSON_DURATION = "lesson_duration";
    public static final String SUBSCRIPTION_FREE_TRANSFER_AMOUNT = "free_transfer_amount";
    public static final String SUBSCRIPTION_STUDENT_DISCOUNT = "student_discount";
    public static final String SUBSCRIPTION_SUBSCRIPTION_DISCOUNT = "subscription_discount";

    /*Teacher descriptions table*/
    public static final String TEACHER_ID = "id_teacher";
    public static final String TEACHER_SELF_DESCRIPTION = "self_description";
    public static final String TEACHER_EXPERIENCE = "experience";
    public static final String TEACHER_PICTURE_PATH = "picture_path";

    /*Teacher schedules table*/
    public static final String TEACHES_SCHEDULE_DAY_OF_WEEK = "day_of_week";
    public static final String TEACHES_SCHEDULE_INTERVAL_START = "interval_start";
    public static final String TEACHES_SCHEDULE_INTERVAL_END = "interval_end";

    private ColumnName() {
    }
}
