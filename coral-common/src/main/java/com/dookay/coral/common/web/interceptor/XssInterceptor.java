package com.dookay.coral.common.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.dookay.coral.common.utils.ServletUtil;
import com.dookay.coral.common.utils.XssUtil;

/**
 * xss攻击处理拦截器
 * @since : 2016年11月9日
 * @author : kezhan
 * @version : v0.0.1
 */
public class XssInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(XssInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.error("xss拦截器start");
		logger.error("请求参数:" + JSON.toJSONString(ServletUtil.getParameterMap(request, true)));
		boolean valid = XssUtil.filterXss(request);
		logger.error("请求参数(xss处理之后):"
				+ JSON.toJSONString(ServletUtil.getParameterMap(request, true)));
		logger.error("xss拦截器end");
		if (!valid) {
			//ExceptionUtils.throwBaseException("请求参数包含非法字符!"); 异常处理
		}
		return super.preHandle(request, response, handler);
	}

}
