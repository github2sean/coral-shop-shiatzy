package com.dookay.coral.adapter.wechat.kit;

/**
 * wechat Api kit
 * 
 * @author : kezhan
 * @since : 2016年12月12日
 * @version : v0.0.1
 */
public class WechatApiUrlKit {

	// 获取微信用户信息 (GET)
	public final static String user_info_url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	// 刷新token 用户授权后
	public final static String refresh_token_url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";

	// 获取用户列表 （实时用户关注总数） GET
	public final static String user_list_url = "https://api.weixin.qq.com/cgi-bin/user/get";

	// 获取用户增减数据 最多七天数据 这里只获取前一天数据 POST
	public final static String user_summary_url = "https://api.weixin.qq.com/datacube/getusersummary";

	// 获取用户昨日关注总数 最多七天数据 这里只获取前一天数据 POST
	public final static String user_cumulate_url = "https://api.weixin.qq.com/datacube/getusercumulate";

	// 网页oauth2.0 回调 回去code 这里使用snsapiBase 模式
	public final static String oauth_snsapiBase_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";

	// 网页oauth2.0 回调 回去code  snsapi_userinfo 模式
	public final static String oauth_snsapi_userinfo_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

	// 网页oauth2.0 通过code 获取accesstoken openId
	public final static String oauth_code_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	// 菜单创建（POST） 限100（次/天）
	public final static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	// 删除菜单（GET）
	public final static String menu_delete_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

	// 生成临时二维码(604800秒有效时间)无限生成
	public final static String qr_scene_url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";

	// 生成二维码最多生成10万个
	public final static String qr_limit_scene_url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";

	// 通过ticket换取二维码
	public final static String showqrcode_url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";

	// 设置所属行业
	public final static String industry_url = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=ACCESS_TOKEN";

	// 获得模板ID
	public final static String add_template_url = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=ACCESS_TOKEN";

	// 发送模板消息
	public final static String send_template_url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

	// js_ticket 权限签名
	public final static String api_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=TYPE";

	/**
	 * PC 扫码登录 OAuth2.0
	 */
	public final static String qr_connect_url = "https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_login&state=STATE#wechat_redirect";

	/**
	 * PC 登录后获取accessToken
	 */
	public final static String oauth_access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	
	/**
	 * PC登录后获取用户信息(UnionID机制)
	 */
	public final static String oauth_userinfo_url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
}



