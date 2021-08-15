package edu.gorb.musicstudio.controller.filter;

import edu.gorb.musicstudio.controller.command.PagePath;
import edu.gorb.musicstudio.controller.command.SessionAttribute;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.entity.UserStatus;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.model.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/**
 * Filter checks if session is valid
 */
public class SessionValidationFilter implements Filter {
    Logger logger = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        if (user != null) {
            long userId = user.getId();
            Optional<User> optionalUser;
            try {
                UserService userService = ServiceProvider.getInstance().getUserService();
                optionalUser = userService.findUserById(userId);
            } catch (ServiceException e) {
                logger.log(Level.ERROR, "Error while selecting user by id={}. {}", userId, e.getMessage());
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + PagePath.ERROR_500_PAGE);
                return;
            }

            if (optionalUser.isEmpty() || optionalUser.get().getStatus() == UserStatus.INACTIVE) {
                session.invalidate();
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + PagePath.HOME_PAGE_REDIRECT);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
