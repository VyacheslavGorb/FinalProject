package edu.gorb.musicstudio.service.impl;

import edu.gorb.musicstudio.dao.DaoProvider;
import edu.gorb.musicstudio.dao.UserDao;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.entity.UserRole;
import edu.gorb.musicstudio.entity.UserStatus;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    private static final String LOGIN_REGEX = "[A-Za-z][0-9a-zA-Z]{2,19}";
    private static final String PASSWORD_REGEX = "[0-9a-zA-Z]{8,20}";
    private static final String NAME_REGEX = "([ЁА-Я][ёа-я]{1,20})|([A-Z][a-z]{1,20})";
    private static final String EMAIL_REGEX = "[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*";
    private static final int MAX_EMAIL_LENGTH = 60;


    public Optional<User> findRegisteredUser(String login, String password) throws ServiceException {
        if (login == null || password == null) {
            return Optional.empty();
        }

        try {
            DaoProvider daoProvider = DaoProvider.getInstance();
            UserDao userDao = daoProvider.getUserDao();
            Optional<User> user = userDao.findUserByLogin(login);
            if (user.isEmpty()) {
                return Optional.empty();
            }

            if (hashPassword(password).equals(user.get().getPassword())) {
                return user;
            } else {
                return Optional.empty();
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while searching for registered user: {}", e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean areStudentSignUpParametersValid(String userRoleString, String login, String password,
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

    @Override
    public User registerUser(UserRole userRole, String login, String password,
                             String name, String surname, String patronymic,
                             String email, UserStatus userStatus) throws ServiceException {
        UserDao userDao = DaoProvider.getInstance().getUserDao();
        User user = new User.Builder()
                .setRole(userRole)
                .setLogin(login)
                .setPassword(hashPassword(password))
                .setName(name)
                .setSurname(surname)
                .setPatronymic(patronymic)
                .setEmail(email)
                .setStatus(userStatus)
                .build();
        try {
            int id = userDao.insert(user);
            user.setId(id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error during user registration: {}", e.getMessage());
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public boolean isLoginAvailableForNewUser(String login) throws ServiceException {
        try {
            UserDao userDao = DaoProvider.getInstance().getUserDao();
            Optional<User> user = userDao.findUserByLogin(login);
            return user.isEmpty();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while searching for user by login: {}", e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isEmailAvailableForNewUser(String email) throws ServiceException {
        try {
            UserDao userDao = DaoProvider.getInstance().getUserDao();
            Optional<User> user = userDao.findUserByEmail(email);
            return user.isEmpty();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while searching for user by email: {}", e.getMessage());
            throw new ServiceException(e);
        }
    }

    private String hashPassword(String password) {
        return DigestUtils.sha3_256Hex(password);
    }
}
