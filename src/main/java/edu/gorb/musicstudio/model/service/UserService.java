package edu.gorb.musicstudio.model.service;

import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.entity.UserRole;
import edu.gorb.musicstudio.entity.UserStatus;
import edu.gorb.musicstudio.entity.UserToken;
import edu.gorb.musicstudio.exception.ServiceException;

import java.util.Optional;

public interface UserService {
    Optional<User> findRegisteredUser(String login, String password) throws ServiceException;

    User registerUser(UserRole userRole, String login, String password,
                      String name, String surname, String patronymic,
                      String email, UserStatus userStatus) throws ServiceException;

    boolean isLoginAvailableForNewUser(String login) throws ServiceException;

    boolean isEmailAvailableForNewUser(String email) throws ServiceException;

    String createUserToken(User user) throws ServiceException;

    public Optional<UserToken> findValidToken(String token, long userId) throws ServiceException;

    void confirmEmail(long userId) throws ServiceException;

    Optional<User> findUserByLogin(String login) throws ServiceException;

    Optional<User> findUserById(Long id) throws ServiceException;
}
