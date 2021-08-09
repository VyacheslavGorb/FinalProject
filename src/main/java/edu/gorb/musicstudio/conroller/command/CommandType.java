package edu.gorb.musicstudio.conroller.command;

public enum CommandType {
    LOGIN,
    LOGOUT,
    PERSONAL_PAGE,
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
    UPDATE_TEACHER_DESCRIPTION;

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
