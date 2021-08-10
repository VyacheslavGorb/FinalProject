package edu.gorb.musicstudio.validator;

import edu.gorb.musicstudio.entity.UserRole;
import org.apache.commons.io.FilenameUtils;

public class FormValidator {

    private static final String LOGIN_REGEX = "[A-Za-z][0-9a-zA-Z]{2,19}";
    private static final String PASSWORD_REGEX = "[0-9a-zA-Z]{8,20}";
    private static final String NAME_REGEX = "([ЁА-Я][ёа-я]{1,20})|([A-Z][a-z]{1,20})";
    private static final String EMAIL_REGEX = "[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*";
    private static final int MAX_EMAIL_LENGTH = 60;

    private static final String EXPERIENCE_REGEX = "[1-9]{1,2}";
    private static final String JPEG_EXTENSION = "jpeg";
    private static final String JPG_EXTENSION = "jpg";
    private static final int MAX_TEXT_DESCRIPTION_LENGTH = 1000;

    private static final String DAY_OF_WEEK_REGEX = "[1-7]";
    private static final int MIN_VALID_HOUR = 0;
    private static final int MAX_VALID_HOUR = 24;


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
        if (email.length() > MAX_EMAIL_LENGTH || email.isBlank()) {
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
        if (!experienceParameter.matches(EXPERIENCE_REGEX)) {
            return false;
        }
        if (description.length() > MAX_TEXT_DESCRIPTION_LENGTH || description.isBlank()) {
            return false;
        }
        return isImageFileNameValid(imageName);
    }

    public static boolean areAlterTeacherScheduleParametersValid(String startHourParam, String endHourParam,
                                                                 String removeParam, String dayOfWeekParam) {

        if (dayOfWeekParam == null || !dayOfWeekParam.matches(DAY_OF_WEEK_REGEX)) {
            return false;
        }

        if (removeParam == null && (endHourParam == null || startHourParam == null)) {
            return false;
        }

        if (removeParam != null && (endHourParam != null || startHourParam != null)) {
            return false;
        }

        if (removeParam == null) {
            if (!IntegerNumberValidator.isNonNegativeIntegerNumber(startHourParam)
                    || !IntegerNumberValidator.isNonNegativeIntegerNumber(endHourParam)) {
                return false;
            }
            int startHour = Integer.parseInt(startHourParam);
            int endHour = Integer.parseInt(endHourParam);
            if (startHour < MIN_VALID_HOUR || endHour > MAX_VALID_HOUR) {
                return false;
            }
            return startHour < endHour;
        }
        return true;
    }

    public static boolean areTeacherUpdateParametersValid(String description, String experienceParameter) {
        if (description == null || experienceParameter == null) {
            return false;
        }
        if (!experienceParameter.matches(EXPERIENCE_REGEX)) {
            return false;
        }
        return description.length() <= MAX_TEXT_DESCRIPTION_LENGTH && !description.isBlank();
    }

    public static boolean isImageFileNameValid(String imageName) {
        if (imageName == null) {
            return false;
        }
        String extension = FilenameUtils.getExtension(imageName);
        return extension.equals(JPEG_EXTENSION) || extension.equals(JPG_EXTENSION);
    }
}
