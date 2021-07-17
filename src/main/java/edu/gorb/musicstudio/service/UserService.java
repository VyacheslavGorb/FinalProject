package edu.gorb.musicstudio.service;

import edu.gorb.musicstudio.dao.DaoProvider;
import edu.gorb.musicstudio.dao.UserDao;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.exception.ServiceException;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Optional;

public class UserService {
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

            if (isCorrectPassword(user.get(), password)) {
                return user;
            } else {
                return Optional.empty();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private boolean isCorrectPassword(User user, String password) {
        String hashedPassword = DigestUtils.sha3_256Hex(password);
        return hashedPassword.equals(user.getPassword());
    }

}
