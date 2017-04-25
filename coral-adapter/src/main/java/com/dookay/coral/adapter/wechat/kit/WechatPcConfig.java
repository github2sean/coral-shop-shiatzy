package com.dookay.coral.adapter.wechat.kit;


/**
 * 微信配置文件类 这里使用spring 注入对于的配置文件
 * 
 * @author : kezhan
 * @since : 2016年12月12日
 * @version : v0.0.1
 */
public class WechatPcConfig {

	/*声明线程 绑定当前 state 参数 用户微信回调 校验使用*/
	private static final ThreadLocal<String> tl = new ThreadLocal<String>();

	/* appid */
	private String appId;
	/* appSecret */
	private String appSecret;
	/*授权登录回调地址*/
	private String oauthLoginCallback;

	public WechatPcConfig() {

	}

	public WechatPcConfig(String appId, String appSecret, String token, String oauthLoginCallback) {
		this.appId = appId;
		this.appSecret = appSecret;
		this.oauthLoginCallback = oauthLoginCallback;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getOauthLoginCallback() {
		return oauthLoginCallback;
	}

	public void setOauthLoginCallback(String oauthLoginCallback) {
		this.oauthLoginCallback = oauthLoginCallback;
	}

	public static ThreadLocal<String> getThreadLocal() {
		return tl;
	}

	public static void setThreadLocal(String str) {
		tl.set(str);
	}

}
