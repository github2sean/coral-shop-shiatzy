package com.dookay.coral.security.filter.authc;

import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/3/28
 */
public class URLPermissionsFilter extends PermissionsAuthorizationFilter {
    /**
     *@param mappedValue 指的是在声明url时指定的权限字符串，如/User/create.do=perms[User:create].我们要动态产生这个权限字符串，所以这个配置对我们没用
     */
    public boolean isAccessAllowed(ServletRequest request,
                                   ServletResponse response, Object mappedValue) throws IOException {
        return super.isAccessAllowed(request, response, buildPermissions(request));
    }

    /**
     * 根据请求URL产生权限字符串，这里只产生，而比对的事交给Realm
     * @param request
     * @return
     */
    protected String[] buildPermissions(ServletRequest request) {
        String[] perms = new String[1];
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getServletPath();
        perms[0] = path;//path直接作为权限字符串
        /*String regex = "/(.*?)/(.*?)\\.(.*)";
        if(url.matches(regex)){
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(url);
            String controller =  matcher.group(1);
            String action = matcher.group(2);

        }*/
        return perms;
    }
}
