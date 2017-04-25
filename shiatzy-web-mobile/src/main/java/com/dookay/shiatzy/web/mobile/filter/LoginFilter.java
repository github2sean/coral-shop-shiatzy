package com.dookay.shiatzy.web.mobile.filter;


import com.dookay.coral.host.user.context.UserContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 登录过滤器
 *
 * @author : ChaosGod
 * @version : v0.0.1
 * @since : 2016/12/13
 */
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String loginRef = "/passport/login?ref=" + URLEncoder.encode(request.getServletPath(), "UTF-8");
//        IAccountService accountService = SpringContextHolder.getBean("accountService");
        if (UserContext.isGuest()) {
            response.sendRedirect(loginRef);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
