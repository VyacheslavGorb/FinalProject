package edu.gorb.musicstudio._main;

import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.MailService;
import edu.gorb.musicstudio.model.service.impl.MailServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;

public class Main {
    public static void main(String[] args) throws ServiceException {
        MailService mailService = new MailServiceImpl();
        mailService.sendSignUpConfirmation(18, "slav2712.2@gmail.com", "token", "ru");
    }
}
