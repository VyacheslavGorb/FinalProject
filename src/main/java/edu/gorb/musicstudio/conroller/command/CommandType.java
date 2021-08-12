package edu.gorb.musicstudio.conroller.command;

public enum CommandType {
    LOGIN,
    LOGOUT,
    DEFAULT,
    HOME_PAGE,
    GO_TO_LOGIN_PAGE,
    CHANGE_LANGUAGE,
    SIGN_UP,
    GO_TO_SIGN_UP_PAGE,
    CONFIRM_EMAIL,
    GO_TO_SEND_EMAIL_AGAIN_PAGE,
    SEND_EMAIL_AGAIN,
    COURSES,
    COURSE_PAGE,
    POST_COMMENT,
    TEACHERS,
    TEACHER_PAGE,

    /*Teacher commands*/
    TEACHER_LESSON_SCHEDULE,
    TEACHER_INIT,
    SEND_TEACHER_INIT_DESCRIPTION,
    TEACHER_SCHEDULE,
    ALTER_TEACHER_SCHEDULE,
    TEACHER_PERSONAL_INFO,
    UPDATE_TEACHER_DESCRIPTION,

    /*Student commands*/
    SUBSCRIPTION_PAGE,
    SEND_SUBSCRIPTION_REQUEST,
    PERSONAL_SUBSCRIPTIONS,
    CHOOSE_LESSON_DATETIME_PAGE,
    CHOOSE_LESSON_DATETIME,
    STUDENT_LESSON_SCHEDULE,
    STUDENT_CANCEL_LESSON,

    /*Admin commands*/
    MANAGE_USERS_PAGE,
    ACTIVATE_USER,
    DEACTIVATE_USER,
    ALL_LESSONS_PAGE,
    ADMIN_CANCEL_LESSON,
    ALL_SUBSCRIPTIONS_PAGE,
    ADMIN_APPROVE_SUBSCRIPTION,
    ADMIN_CANCEL_SUBSCRIPTION,
    ALL_COURSES_PAGE,
    ADD_COURSE_PAGE,
    ADD_COURSE;

    public static CommandType convertRequestParameterToCommandType(String parameter){
        if (parameter == null) {
            return DEFAULT;
        }
        CommandType commandType;
        try {
            commandType = CommandType.valueOf(parameter.toUpperCase());
        } catch (IllegalArgumentException e) {
            commandType = DEFAULT;
        }
        return commandType;
    }
}
