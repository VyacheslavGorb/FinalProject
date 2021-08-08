package edu.gorb.musicstudio.validator;

import edu.gorb.musicstudio.entity.UserRole;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.core.appender.rolling.FileExtension;

import javax.servlet.ServletResponseWrapper;

public class FormValidator {

    private static final String LOGIN_REGEX = "[A-Za-z][0-9a-zA-Z]{2,19}";
    private static final String PASSWORD_REGEX = "[0-9a-zA-Z]{8,20}";
    private static final String NAME_REGEX = "([ЁА-Я][ёа-я]{1,20})|([A-Z][a-z]{1,20})";
    private static final String EMAIL_REGEX = "[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*";
    private static final int MAX_EMAIL_LENGTH = 60;

    private static final String EXPERIENCE_REGEX = "[1-9]{1,2}";
    private static final String JPEG_EXTENSION = "jpeg";
    private static final String JPG_EXTENSION = "jpg";



    private FormValidator() {
    }

    public static boolean areSignUpParametersValid(String userRoleString, String login, String password,
                                                   String passwordRepeated, String name, String surname,
                                                   String patronymic, String email) {
        if (userRoleString == null || login == null || password == null || passwordRepeated == null
                || name == null || surname == null || patronymic == null || email == null) {
            return false;
        }
        if (!password.equals(passwordRepeated)) {
            return false;
        }
        if (email.length() > MAX_EMAIL_LENGTH) {
            return false;
        }
        try {
            UserRole.valueOf(userRoleString);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return login.matches(LOGIN_REGEX) && password.matches(PASSWORD_REGEX) && name.matches(NAME_REGEX)
                && surname.matches(NAME_REGEX) && patronymic.matches(NAME_REGEX) && email.matches(EMAIL_REGEX);

    }

    public static boolean areTeacherInitParametersValid(String description, String experienceParameter, String imageName) {
        if (description == null || experienceParameter == null || imageName == null) {
            return false;
        }
        if(!experienceParameter.matches(EXPERIENCE_REGEX)){
            return false;
        }

        String extension = FilenameUtils.getExtension(imageName);
        return extension.equals(JPEG_EXTENSION) || extension.equals(JPG_EXTENSION);
    }
}
