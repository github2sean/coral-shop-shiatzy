package com.dookay.coral.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 封装有关单个 HTTP 请求的所有 HTTP 特定的信息。
 * @author Luxor
 * @version v0.0.1
 * @since 2016/11/21
 */
public class HttpContext {

    private static final HttpContext instance = new HttpContext();
    private ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();
    private  ThreadLocal<HttpServletResponse> response = new ThreadLocal<HttpServletResponse>();
    private boolean initialized = false;

    private HttpSession session;
    private Map<String, Object> itemMap = new HashMap<String, Object>();
    public static HttpContext current() {
        return instance;
    }

    public HttpServletRequest getRequest() {
        if (request != null) {
            return request.get();
        }
        return null;
    }

    public synchronized void setRequest(HttpServletRequest servletRequest) {

        if (request != null) {
            request.set(servletRequest);
        }
    }

    public HttpServletResponse getResponse() {
        if (response != null) {
            return response.get();
        }
        return null;
    }

    public synchronized void setResponse(HttpServletResponse servletResponse) {
        if (response != null) {
            response.set(servletResponse);
        }
    }

    public synchronized void init() {
        if (initialized) {
            return;
        }
        initialized = true;
    }

    public synchronized void destroy () {
        request = null;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }
}
