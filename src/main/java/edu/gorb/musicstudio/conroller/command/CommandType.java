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
    COURSES;

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
