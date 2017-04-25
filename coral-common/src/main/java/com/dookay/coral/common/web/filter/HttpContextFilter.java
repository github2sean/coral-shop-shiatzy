package com.dookay.coral.common.web.filter;

import com.dookay.coral.common.web.HttpContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 上下文过滤器，绑定HttpContext
 *
 * @author Luxor
 * @version v0.1.1
 * @since 2016/11/21.
 */
public class HttpContextFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpContext.current().setRequest(request);
        HttpContext.current().setResponse(response);
        HttpContext.current().setSession(request.getSession());
        chain.doFilter(req, response);
    }

    public void destroy() {

    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }
}
