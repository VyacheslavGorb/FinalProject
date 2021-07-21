package edu.gorb.musicstudio.conroller.command;

public class PagePath {
    public static final String HOME_PAGE = "WEB-INF/pages/home.jsp";
    public static final String HOME_PAGE_REDIRECT = "/controller?command=home_page";
    public static final String LOGIN_PAGE = "WEB-INF/pages/login.jsp";
    public static final String LOGIN_PAGE_REDIRECT = "/controller?command=go_to_login_page";
    public static final String ERROR_PAGE = "WEB-INF/pages/error/error.jsp";
    public static final String ERROR_EMAIL_PAGE = "WEB-INF/pages/error/error_email.jsp";
    public static final String ERROR_404_PAGE = "WEB-INF/pages/error/error_404.jsp";
    public static final String ERROR_500_PAGE = "WEB-INF/pages/error/error_500.jsp";
    public static final String SIGN_UP_PAGE = "WEB-INF/pages/signup.jsp";

    private PagePath() {
    }
}
