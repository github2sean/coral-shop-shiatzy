package com.dookay.coral.security.filter.authc;

import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.common.web.ResultCodes;
import com.dookay.coral.common.web.render.JsonRender;
import com.dookay.coral.security.enums.Constants;
import com.dookay.coral.security.token.StatelessToken;
import com.dookay.coral.security.user.AdminContext;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 无状态登录过滤器
 * @author Luxor
 * @version v0.0.1
 * @since 2017/3/26
 */
public class StatelessAuthcFilter extends AccessControlFilter {

    //访问不允许，执行onAccessDenied
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    /**
     * 登录操作
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if(httpRequest.getServletPath().contains("index.html")){
            return true;
        }else   if(Objects.equals(httpRequest.getServletPath(), "/")){
            WebUtils.issueRedirect(request, response, "index.html");
            return true;
        }

        //1、客户端生成的消息摘要
        String clientDigest = request.getParameter(Constants.PARAM_DIGEST);
        //2、客户端传入的用户身份
        String username = request.getParameter(Constants.PARAM_USERNAME);
        //3、客户端请求的参数列表
        Map<String, String[]> params = new HashMap<String, String[]>(request.getParameterMap());
        params.remove(Constants.PARAM_DIGEST);

        //4、生成无状态Token
        StatelessToken token = new StatelessToken(username, params, clientDigest);

        try {
            //5、委托给Realm进行登录
            getSubject(request, response).login(token);
        } catch (Exception e) {
            e.printStackTrace();
            onLoginFail(request,response,e); //6、登录失败
            return false;
        }
        return true;
    }

    //登录失败时默认返回401状态码
    private void onLoginFail(ServletRequest request,ServletResponse response, Exception e) throws IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (JsonUtils.isAjaxRequest(httpRequest)) {
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpServletResponse.setContentType("text/html;charset=utf-8");
            OutputStream out = httpServletResponse.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(out);
            writer.write("没有权限");
            writer.close();//这行必须写，否则无法输出响应正文
        }else{
            redirectToLogin(request,response);
        }
    }

}
