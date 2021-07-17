package edu.gorb.musicstudio.service;

import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.exception.ServiceException;

import java.util.Optional;

public interface UserService {
    Optional<User> findRegisteredUser(String login, String password) throws ServiceException;

}
