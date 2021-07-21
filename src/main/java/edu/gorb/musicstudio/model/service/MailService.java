package edu.gorb.musicstudio.model.service;

import edu.gorb.musicstudio.exception.ServiceException;

public interface MailService {
    void sendSignUpConfirmation(long userId, String email, String token, String locale) throws ServiceException;
}
