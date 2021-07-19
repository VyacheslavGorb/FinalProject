package edu.gorb.musicstudio.model.service;

import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.entity.UserRole;
import edu.gorb.musicstudio.entity.UserStatus;
import edu.gorb.musicstudio.exception.ServiceException;

import java.util.Optional;

public interface UserService {
    Optional<User> findRegisteredUser(String login, String password) throws ServiceException;

    boolean areStudentSignUpParametersValid(String userRoleString, String login, String password,
                                            String passwordRepeated, String name, String surname,
                                            String patronymic, String email);

    User registerUser(UserRole userRole, String login, String password,
                      String name, String surname, String patronymic,
                      String email, UserStatus userStatus) throws ServiceException;

    boolean isLoginAvailableForNewUser(String login) throws ServiceException;

    boolean isEmailAvailableForNewUser(String email) throws ServiceException;
}
