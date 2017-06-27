package com.dookay.coral.common.web.filter;

import com.dookay.coral.common.web.CookieUtil;
import com.dookay.coral.common.web.HttpContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 上下文过滤器，绑定HttpContext
 *
 * @author Luxor
 * @version v0.1.1
 * @since 2016/11/21.
 */
public class HttpContextFilter implements Filter {
    @Autowired
    private CookieLocaleResolver cookieLocaleResolver;

    private final static int MAX_COOKIE_AGE = 24*60*60*7;

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpContext.current().setRequest(request);



        HttpContext.current().setSession(request.getSession());

        //显示语言
        String cookieName = "Language";
        String checked = CookieUtil.getCookieValueByKey(request,cookieName);
        if(StringUtils.isNotBlank(checked)){
                request.getSession().setAttribute("language",checked);
            CookieUtil.setCookieValueByKey(HttpContext.current().getResponse(),cookieName,"zh_CN",MAX_COOKIE_AGE);
        }

        chain.doFilter(req, response);
    }

    public void destroy() {

    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }
}
