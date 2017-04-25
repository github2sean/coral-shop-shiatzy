package com.dookay.coral.security.filter.authc;

import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.common.web.ResultCodes;
import com.dookay.coral.common.web.render.JsonRender;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

/**
 * 基于权限的鉴权过滤器
 * @author Luxor
 * @version v0.0.1
 * @since 2017/3/26
 */
public class PermissionAuthorizationFilter extends AuthorizationFilter {
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        Subject subject = getSubject(request, response);

        if (subject.getPrincipal() == null) {
            if (JsonUtils.isAjaxRequest(httpRequest)) {
                JsonResult result = new JsonResult(ResultCodes.ERROR, "您尚未登录或登录时间过长,请重新登录!");
                JsonRender render = new JsonRender(result);
                render.render();
            } else {
                saveRequestAndRedirectToLogin(request, response);
            }
        } else {
            if (JsonUtils.isAjaxRequest(httpRequest)) {
                JsonResult result = new JsonResult(ResultCodes.ERROR, "您没有足够的权限执行该操作");
                JsonRender render = new JsonRender(result);
                render.render();
            } else {
                String unauthorizedUrl = getUnauthorizedUrl();
                if (StringUtils.hasText(unauthorizedUrl)) {
                    WebUtils.issueRedirect(request, response, unauthorizedUrl);
                } else {
                    WebUtils.toHttp(response).sendError(401);
                }
            }
        }
        return false;
    }

    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws IOException {

        Subject subject = this.getSubject(request, response);
        String[] perms = ((String[])mappedValue);

        boolean isPermitted = true;
        if(perms != null && perms.length > 0) {
            if(perms.length == 1) {
                if(!subject.isPermitted(perms[0])) {
                    isPermitted = false;
                }
            } else if(!subject.isPermittedAll(perms)) {
                isPermitted = false;
            }
        }

        return isPermitted;
    }

}
