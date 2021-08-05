package edu.gorb.musicstudio.conroller.command.impl;

import edu.gorb.musicstudio.conroller.command.*;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.entity.UserRole;
import edu.gorb.musicstudio.entity.UserStatus;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.MailService;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.model.service.UserService;
import edu.gorb.musicstudio.validator.FormValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SignUpCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {

        String userRoleString = request.getParameter(RequestParameter.USER_ROLE);
        String login = request.getParameter(RequestParameter.LOGIN);
        String password = request.getParameter(RequestParameter.PASSWORD);
        String passwordRepeated = request.getParameter(RequestParameter.PASSWORD_REPEAT);
        String name = request.getParameter(RequestParameter.NAME);
        String surname = request.getParameter(RequestParameter.SURNAME);
        String patronymic = request.getParameter(RequestParameter.PATRONYMIC);
        String email = request.getParameter(RequestParameter.EMAIL);

        boolean isValidRequest = FormValidator.areSignUpParametersValid(
                userRoleString, login, password, passwordRepeated, name, surname, patronymic, email);

        HttpSession session = request.getSession();

        if (!isValidRequest) {
            logger.log(Level.DEBUG, "Invalid request parameters");
            session.setAttribute(SessionAttribute.IS_SEND_EMAIL_AGAIN_ERROR, true);
            session.setAttribute(SessionAttribute.ERROR_KEY, BundleKey.SIGNUP_INVALID_REQUEST);
            return new CommandResult(PagePath.SIGN_UP_PAGE_REDIRECT, CommandResult.RoutingType.REDIRECT);
        }

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        MailService mailService = serviceProvider.getMailService();

        try {
            if (!userService.isLoginAvailableForNewUser(login)) {
                logger.log(Level.DEBUG, "Login already exists");
                session.setAttribute(SessionAttribute.IS_SEND_EMAIL_AGAIN_ERROR, true);
                session.setAttribute(SessionAttribute.ERROR_KEY, BundleKey.SIGNUP_LOGIN_NOT_AVAILABLE);
                return new CommandResult(PagePath.SIGN_UP_PAGE_REDIRECT, CommandResult.RoutingType.REDIRECT);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error while checking login availability during registration {}",
                    e.getMessage());
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        try {
            if (!userService.isEmailAvailableForNewUser(email)) {
                logger.log(Level.DEBUG, "Email already exists");
                session.setAttribute(SessionAttribute.IS_SEND_EMAIL_AGAIN_ERROR , true);
                session.setAttribute(SessionAttribute.ERROR_KEY, BundleKey.SIGNUP_EMAIL_NOT_AVAILABLE);
                return new CommandResult(PagePath.SIGN_UP_PAGE_REDIRECT, CommandResult.RoutingType.REDIRECT);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error while checking login availability during registration {}",
                    e.getMessage());
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);

        }

        User user;
        try {
            user = userService.registerUser(UserRole.valueOf(userRoleString), login,
                    password, name, surname, patronymic, email, UserStatus.EMAIL_NOT_CONFIRMED);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error during user registration {}",
                    e.getMessage());
            return new CommandResult(PagePath.ERROR_500_PAGE, CommandResult.RoutingType.REDIRECT);
        }

        String locale = (String) session.getAttribute(SessionAttribute.LOCALE);

        try {
            String token = userService.createUserToken(user);
            mailService.sendSignUpConfirmation(user.getId(), email, token, locale);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Error while sending email: {}", e.getMessage());
        }

        session.setAttribute(SessionAttribute.INFO_KEY, BundleKey.EMAIL_SENT);
        return new CommandResult(PagePath.INFO_PAGE, CommandResult.RoutingType.REDIRECT);
    }
}
