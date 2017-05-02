package com.dookay.coral.adapter.wechat.wx.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jfinal.kit.StrKit;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.kit.SignatureCheckKit;
import com.dookay.coral.common.exception.ExceptionUtils;

/**
 * 微信 Msg 拦截器 
 * 1：通过 WeChatMsgController.getApiConfig() 得到 ApiConfig
 * 		对象，并将其绑定到当前线程之上(利用了 ApiConfigKit 中的 ThreadLocal 对象) 
 * 2：响应开发者中心服务器配置 URL 与 Token 请求 
 * 3：签名检测
 * @author : kezhan
 * @since : 2017年1月4日
 * @version : v0.0.1
 */
public class WechatMsgInterceptor extends HandlerInterceptorAdapter {

	private static Logger log = Logger.getLogger(WechatMsgInterceptor.class);

	private ApiConfig apiConfig;

	public ApiConfig getApiConfig() {
		return apiConfig;
	}

	public void setApiConfig(ApiConfig apiConfig) {
		this.apiConfig = apiConfig;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		ApiConfigKit.setThreadLocalApiConfig(apiConfig);
		if (isConfigServerRequest(request)) { // 如果是服务器配置请求，则配置服务器并返回
			if (configServer(request, response)) {
				log.info("服务器配置请求,结束删除当前线程...");
				ApiConfigKit.removeThreadLocalApiConfig();
				return false;
			}
			if (checkSignature(request, response)) { // 签名检测
				return true;
			} else {
				log.info("签名验证失败，请确定是微信服务器在发送消息过来...");
				ExceptionUtils.throwBaseException("签名验证失败，请确定是微信服务器在发送消息过来...");
			}
		}
		return true;
	}

	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("controllro end...删除当前线程...");
		ApiConfigKit.removeThreadLocalApiConfig();// 结束后删除当前线程
	}

	/**
	 * **********************以下为签名验证工具包，占位私有不分割**********************
	 */

	/**
	 * 是否为开发者中心保存服务器配置的请求
	 * @param request
	 * @return
	 * @author : kezhan	
	 * @since : 2017年1月4日
	 */
	private boolean isConfigServerRequest(HttpServletRequest request) {
		return StrKit.notBlank(request.getParameter("echostr"));
	}

	/**
	 * 配置开发者中心微信服务器所需的 url 与 token
	 * @param request
	 * @param response
	 * @return true 为config server 请求，false 正式消息交互请求
	 * @author : kezhan	
	 * @since : 2017年1月4日
	 */
	public boolean configServer(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 通过 echostr 判断请求是否为配置微信服务器回调所需的 url 与 token
			String echostr = request.getParameter("echostr");
			String signature = request.getParameter("signature");
			String timestamp = request.getParameter("timestamp");
			String nonce = request.getParameter("nonce");
			if (SignatureCheckKit.me.checkSignature(signature, timestamp, nonce)) {
				response.getWriter().print(echostr);
				return true;
			} else {
				log.error("验证失败：configServer");
				return false;
			}
		} catch (Exception e) {
			log.error("验证异常：configServer" + e.getMessage());
			return false;
		}

	}

	/**
	 * 检测签名
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author : kezhan	
	 * @since : 2017年1月4日
	 */
	private boolean checkSignature(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		if (StrKit.isBlank(signature) || StrKit.isBlank(timestamp) || StrKit.isBlank(nonce)) {
			return false;
		}

		if (SignatureCheckKit.me.checkSignature(signature, timestamp, nonce)) {
			return true;
		} else {
			log.error(
					"check signature failure: " + " signature = "
							+ request.getParameter("signature") + " timestamp = "
							+ request.getParameter("timestamp") + " nonce = "
							+ request.getParameter("nonce"));

			return false;
		}
	}
}
