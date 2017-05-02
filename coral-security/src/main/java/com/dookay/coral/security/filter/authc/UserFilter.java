package com.dookay.coral.security.filter.authc;

import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.common.web.ResultCodes;
import com.dookay.coral.common.web.render.JsonRender;
import com.dookay.coral.security.enums.Constants;
import com.dookay.coral.security.enums.UserLockedType;
import com.dookay.coral.security.user.AdminContext;
import com.dookay.coral.security.user.AdminModel;
import com.dookay.coral.security.user.UserSecurityRealm;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 用户过滤器
 * @since : 2016年11月16日
 * @author : kezhan
 * @version : v0.0.1
 */
public class UserFilter extends AccessControlFilter {

	@Autowired
	private UserSecurityRealm userSecurityRealm;
	@Autowired
	private AdminContext adminContext;
	
	/**
	 * login url
	 */
	private String loginUrl;

	/**
	 * 用户删除了后重定向的地址
	 */
	private String userNotfoundUrl;
	/**
	 * 用户锁定后重定向的地址
	 */
	private String userBlockedUrl;
	/**
	 * 未知错误
	 */
	private String userUnknownErrorUrl;

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getUserNotfoundUrl() {
		return userNotfoundUrl;
	}

	public void setUserNotfoundUrl(String userNotfoundUrl) {
		this.userNotfoundUrl = userNotfoundUrl;
	}

	public String getUserBlockedUrl() {
		return userBlockedUrl;
	}

	public void setUserBlockedUrl(String userBlockedUrl) {
		this.userBlockedUrl = userBlockedUrl;
	}

	public String getUserUnknownErrorUrl() {
		return userUnknownErrorUrl;
	}

	public void setUserUnknownErrorUrl(String userUnknownErrorUrl) {
		this.userUnknownErrorUrl = userUnknownErrorUrl;
	}

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		if (subject == null) {
			return true;
		}
		String username = (String) subject.getPrincipal(); //username 唯一
		if (StringUtils.isEmpty(username)) {
			return true;
		}
		if(adminContext.getCurrent().getAdminModel() == null) {
			redirectToLogin(request, response);
		}
		
		AdminModel adminModel = null;

		AdminModel model = new AdminModel();
		model.setUserName(username);
		adminModel = userSecurityRealm.getAdminModel(model);

		if (null == adminModel || !adminModel.getLocked().equals(UserLockedType.ON.getValue())) {
			redirectToLogin(request, response);
		} else {
			request.setAttribute(Constants.CURRENT_USER, adminModel);
			// druid监控需要
			((HttpServletRequest) request).getSession().setAttribute(Constants.CURRENT_USER, username);
		}
		return true;
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object object) throws Exception {
		AdminModel user = (AdminModel) request.getAttribute(Constants.CURRENT_USER);
		if (user == null) {
			return true;
		}
		if (user.getLocked() != UserLockedType.ON.getValue()) {
			getSubject(request, response).logout();
			redirectToLogin(request, response);
			return false;
		}
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

		getSubject(request, response).logout();
		redirectToLogin(request, response);
		return false;
	}

	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (JsonUtils.isAjaxRequest(httpRequest)) {
			JsonResult result = new JsonResult(ResultCodes.ERROR, "您尚未登录或登录时间过长,请重新登录!");
			JsonRender render = new JsonRender(result);
			render.render();
		} else {
			saveRequestAndRedirectToLogin(request, response);
		}
		WebUtils.issueRedirect(request, response, loginUrl);
	}

}
