package edu.gorb.musicstudio.model.service.impl;

import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.entity.UserRole;
import edu.gorb.musicstudio.entity.UserStatus;
import edu.gorb.musicstudio.entity.UserToken;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.dao.DaoProvider;
import edu.gorb.musicstudio.model.dao.UserDao;
import edu.gorb.musicstudio.model.dao.UserTokenDao;
import edu.gorb.musicstudio.model.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    private static final int LINK_EXPIRE_TIMEOUT = 10; // minutes


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

            if (hashString(password).equals(user.get().getPassword())) {
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
    public User registerUser(UserRole userRole, String login, String password,
                             String name, String surname, String patronymic,
                             String email, UserStatus userStatus) throws ServiceException {
        UserDao userDao = DaoProvider.getInstance().getUserDao();
        User user = new User.Builder()
                .setRole(userRole)
                .setLogin(login)
                .setPassword(hashString(password))
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

    @Override
    public String createUserToken(User user) throws ServiceException {
        String token = hashString(user.toString() + LocalDateTime.now());
        UserTokenDao userTokenDao = DaoProvider.getInstance().getUserTokenDao();
        try {
            userTokenDao.insetUserToken(user.getId(), token, LocalDateTime.now());
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while generating user token: {}", e.getMessage());
            throw new ServiceException(e);
        }
        return token;
    }

    @Override
    public Optional<UserToken> findValidToken(String token, long userId) throws ServiceException {
        try {
            UserTokenDao userTokenDao = DaoProvider.getInstance().getUserTokenDao();
            Optional<UserToken> userToken = userTokenDao.findTokenByValue(token, userId);
            if (userToken.isEmpty()) {
                return Optional.empty();
            }
            String tokenValue = userToken.get().getToken();
            LocalDateTime timestamp = userToken.get().getCreationTimestamp();
            if (!tokenValue.equals(token) || timestamp.plusMinutes(LINK_EXPIRE_TIMEOUT).isBefore(LocalDateTime.now())) {
                return Optional.empty();
            }
            return userToken;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while checking user token: {}", e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public void confirmEmail(long userId) throws ServiceException {
        UserDao userDao = DaoProvider.getInstance().getUserDao();
        try {
            Optional<User> user = userDao.findEntityById(userId);
            if (user.isEmpty()) {
                logger.log(Level.ERROR, "User with id {} not found", userId);
                throw new ServiceException("User with id " + userId + " not found");
            }
            UserStatus updatedStatus =
                    user.get().getRole() == UserRole.STUDENT ? UserStatus.ACTIVE : UserStatus.WAITING_FOR_APPROVEMENT;
            userDao.updateUserStatus(userId, updatedStatus);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while updating user status: {}", e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws ServiceException {
        UserDao userDao = DaoProvider.getInstance().getUserDao();
        try{
            return userDao.findUserByLogin(login);
        }catch (DaoException e){
            logger.log(Level.ERROR, "Error while searching for user with login: {}. {}", login, e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> findUserById(Long id) throws ServiceException {
        UserDao userDao = DaoProvider.getInstance().getUserDao();
        try{
            return userDao.findEntityById(id);
        }catch (DaoException e){
            logger.log(Level.ERROR, "Error while searching for user with id: {}. {}", id, e.getMessage());
            throw new ServiceException(e);
        }
    }


    private String hashString(String s) {
        return DigestUtils.sha3_256Hex(s);
    }
}
