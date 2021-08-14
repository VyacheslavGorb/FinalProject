package edu.gorb.musicstudio.model.service;

import edu.gorb.musicstudio.exception.ServiceException;

public interface MailService {
    /**
     * Sends user sign up email confirmation
     *
     * @param userId user id
     * @param email  user email address
     * @param token  token
     * @param locale user locale
     */
    void sendSignUpConfirmation(long userId, String email, String token, String locale) throws ServiceException;
}
