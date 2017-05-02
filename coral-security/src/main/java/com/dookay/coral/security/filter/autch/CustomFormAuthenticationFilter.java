package com.dookay.coral.security.filter.autch;

import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.common.web.ResultCodes;
import com.dookay.coral.common.web.render.JsonRender;
import com.dookay.coral.security.user.AdminContext;
import com.dookay.coral.security.user.LoginSuccessResponse;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 基于几点修改：
 * 1、onLoginFailure 时 把异常添加到request attribute中 而不是异常类名
 * 2、登录成功时：成功页面重定向：
 * 2.1、如果前一个页面是登录页面，-->2.3
 * 2.2、如果有SavedRequest 则返回到SavedRequest
 * 2.3、否则根据当前登录的用户决定返回到管理员首页/前台首页
 * 
 * @since : 2016年11月17日
 * @author : kezhan
 * @version : v0.0.1
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

	/* @Autowired
	    UserService userService;*/

	@Autowired
	private AdminContext adminContext;

	@Override
	protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
		request.setAttribute(getFailureKeyAttribute(), ae);
	}

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (JsonUtils.isAjaxRequest(httpRequest)) {
			LoginSuccessResponse loginSuccessResponse = new LoginSuccessResponse();
			loginSuccessResponse.setAdminModel(adminContext.getCurrent().getAdminModel());
			loginSuccessResponse.setModuleModels(adminContext.getCurrent().getModuleModels());
			loginSuccessResponse.setPermissions(adminContext.getCurrent().getPermissions());
			loginSuccessResponse.setRoles(adminContext.getCurrent().getRoles());
			JsonRender render = new JsonRender(loginSuccessResponse);
			render.render();
			return false;
		}
		this.issueSuccessRedirect(request, response);
		return false;
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (JsonUtils.isAjaxRequest(httpRequest)) {
			JsonResult result = new JsonResult(ResultCodes.ERROR, e.getMessage());
			JsonRender render = new JsonRender(result);
			render.render();
		}else{
			this.setFailureAttribute(request, e);
		}

		return true;
	}


	/**
	 * 默认的成功地址
	 */
	private String defaultSuccessUrl;
	/**
	 * 管理员默认的成功地址
	 */
	private String adminDefaultSuccessUrl;

	public void setDefaultSuccessUrl(String defaultSuccessUrl) {
		this.defaultSuccessUrl = defaultSuccessUrl;
	}

	public void setAdminDefaultSuccessUrl(String adminDefaultSuccessUrl) {
		this.adminDefaultSuccessUrl = adminDefaultSuccessUrl;
	}

	public String getDefaultSuccessUrl() {
		return defaultSuccessUrl;
	}

	public String getAdminDefaultSuccessUrl() {
		return adminDefaultSuccessUrl;
	}

	/**
	 * 根据用户选择成功地址
	 *
	 * @return
	 */
	@Override
	public String getSuccessUrl() {
		/*String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userService.findByUsername(username);
		if (user != null && Boolean.TRUE.equals(user.getAdmin())) {
		    return getAdminDefaultSuccessUrl();
		}*/
		return getDefaultSuccessUrl();
	}

}
