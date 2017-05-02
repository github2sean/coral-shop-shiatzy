package com.dookay.coral.security.filter.jcaptcha;

import com.dookay.coral.common.web.jcaptcha.JCaptcha;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 验证码过滤器
 * @author : kezhan
 * @since : 2016年11月23日
 * @version : v0.0.1
 */
public class JCaptchaValidateFilter extends AccessControlFilter {

	/*是否开启jcaptcha*/
	private boolean jcaptchaEbabled = true;
	
	/*前台传入的验证码*/
	private String jcaptchaParam = "jcaptchaCode";

	/*验证码错误跳转链接*/
	private String jcapatchaErrorUrl;
	
    public void setJcaptchaEbabled(boolean jcaptchaEbabled) {
        this.jcaptchaEbabled = jcaptchaEbabled;
    }
    
    public void setJcaptchaParam(String jcaptchaParam) {
        this.jcaptchaParam = jcaptchaParam;
    }
    
    public void setJcapatchaErrorUrl(String jcapatchaErrorUrl) {
        this.jcapatchaErrorUrl = jcapatchaErrorUrl;
    }

    public String getJcapatchaErrorUrl() {
        return jcapatchaErrorUrl;
    }
    
    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        request.setAttribute("jcaptchaEbabled", jcaptchaEbabled);
        return super.onPreHandle(request, response, mappedValue);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        //验证码禁用 或不是表单提交 允许访问
        if (jcaptchaEbabled == false || !"post".equals(httpServletRequest.getMethod().toLowerCase())) {
            return true;
        }
        return JCaptcha.validateResponse(httpServletRequest, httpServletRequest.getParameter(jcaptchaParam));
    }


    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
    	getSubject(request, response).logout();
		saveRequestAndRedirectToLogin(request, response);
        return false;
    }
    
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
    	WebUtils.issueRedirect(request, response, jcapatchaErrorUrl);
    }
 
}
