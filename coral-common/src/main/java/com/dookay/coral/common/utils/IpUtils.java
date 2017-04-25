package com.dookay.coral.common.utils;

import com.dookay.coral.common.web.HttpContext;

import javax.servlet.http.HttpServletRequest;

/**
 * IP地址获取工具类
 * @since : 2016年11月21日
 * @author : kezhan
 * @version : v0.0.1
 */
public class IpUtils {

	private IpUtils() {
	}

	public static  String getIp(){
		HttpServletRequest request = HttpContext.current().getRequest();
		return getIp(request);
	}

	public static String getIp(HttpServletRequest request) {
		if (request == null) {
			return "unknown";
		}
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
