package edu.gorb.musicstudio.conroller.filter;

import edu.gorb.musicstudio.conroller.command.SessionAttribute;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LocaleFilter implements Filter {
    private String defaultLocale;

    @Override
    public void init(FilterConfig filterConfig){
        defaultLocale = filterConfig.getInitParameter("defaultLocale");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        if (session.getAttribute(SessionAttribute.LOCALE) == null) {
            session.setAttribute(SessionAttribute.LOCALE, defaultLocale);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
