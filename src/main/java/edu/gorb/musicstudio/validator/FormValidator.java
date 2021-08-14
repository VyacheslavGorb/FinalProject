package edu.gorb.musicstudio.validator;

import edu.gorb.musicstudio.entity.UserRole;
import org.apache.commons.io.FilenameUtils;

import java.math.BigDecimal;

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
    private static final int MAX_VALID_HOUR = 23;

    private static final int MAX_COURSE_NAME_LENGTH = 40;
    private static final int MAX_PRICE_VALUE = 9999;


    private FormValidator() {
    }

    /**
     * Validates sign up form
     *
     * @param userRoleString   user role
     * @param login            user login
     * @param password         user password
     * @param passwordRepeated repeated password
     * @param name             user name
     * @param surname          user surname
     * @param patronymic       user patronymic
     * @param email            user email
     * @return if parameters valid
     */
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

    /**
     * Validates teacher initialization forms
     *
     * @param description         teacher description
     * @param experienceParameter teacher work experience
     * @param imageName           teacher image name
     * @return if teacher initialization parameters valid
     */
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

    /**
     * Validates if teacher schedule alter parameters correct
     *
     * @param startHourParam work start hour
     * @param endHourParam   work end hour
     * @param removeParam    is schedule removed parameter
     * @param dayOfWeekParam day of week parameter
     * @return if teacher schedule alter parameters correct
     */
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

    /**
     * Validates teacher update parameters
     *
     * @param description         teacher description
     * @param experienceParameter teacher work experience
     * @return if teacher update parameters valid
     */
    public static boolean areTeacherUpdateParametersValid(String description, String experienceParameter) {
        if (description == null || experienceParameter == null) {
            return false;
        }
        if (!experienceParameter.matches(EXPERIENCE_REGEX)) {
            return false;
        }
        return description.length() <= MAX_TEXT_DESCRIPTION_LENGTH && !description.isBlank();
    }

    /**
     * Validates if image file name valid
     *
     * @param imageName image file name
     * @return if image file name valid
     */
    public static boolean isImageFileNameValid(String imageName) {
        if (imageName == null) {
            return false;
        }
        String extension = FilenameUtils.getExtension(imageName);
        return extension.equals(JPEG_EXTENSION) || extension.equals(JPG_EXTENSION);
    }

    /**
     * Validates if add course parameters correct
     *
     * @param name           course name
     * @param description    course description
     * @param priceParameter course price per hour
     * @param fileName       course image file name
     * @return if add course parameters correct
     */
    public static boolean areAddCourseParametersValid(String name, String description, String priceParameter, String fileName) {
        if (name == null || description == null || priceParameter == null) {
            return false;
        }
        if (name.length() > MAX_COURSE_NAME_LENGTH || description.length() > MAX_TEXT_DESCRIPTION_LENGTH) {
            return false;
        }
        BigDecimal price;
        try {
            price = new BigDecimal(priceParameter);
        } catch (NumberFormatException e) {
            return false;
        }

        if (price.intValue() < 0 || Math.ceil(price.doubleValue()) > MAX_PRICE_VALUE) {
            return false;
        }

        return isImageFileNameValid(fileName);
    }

    /**
     * Validates if update course parameters correct
     *
     * @param name           course name
     * @param description    course description
     * @param priceParameter course price per hour
     * @return if update course parameters correct
     */
    public static boolean areUpdateCourseParametersValid(String name, String description, String priceParameter) {
        if (name == null || description == null || priceParameter == null) {
            return false;
        }
        if (name.length() > MAX_COURSE_NAME_LENGTH || description.length() > MAX_TEXT_DESCRIPTION_LENGTH) {
            return false;
        }
        BigDecimal price;
        try {
            price = new BigDecimal(priceParameter);
        } catch (NumberFormatException e) {
            return false;
        }

        return price.intValue() >= 0 && Math.ceil(price.doubleValue()) <= MAX_PRICE_VALUE;
    }

}
