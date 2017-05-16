package com.dookay.coral.adapter.payment.alipay.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.4
 *修改日期：2016-03-08
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */
@Data
public class AlipayConfig {

	// 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
	private  String partner = "2088221294543871";
	
	// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
	private  String seller_id = partner;

	// MD5密钥，安全检验码，由数字和字母组成的32位字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
	private  String key = "odloe0g55bakkzvnooundk95klqwr0fq";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	private  String notify_url = "http://shiatzy-w.doolab.cn/alipay.wap.create.direct.pay.by.user-JAVA-UTF-8/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	private  String return_url = "http://shiatzy-w.doolab.cn/alipay.wap.create.direct.pay.by.user-JAVA-UTF-8/return_url.jsp";

	// 签名方式
	private  String sign_type = "MD5";
	
	// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	private  String log_path = "D:\\";
		
	// 字符编码格式 目前支持utf-8
	private   String input_charset = "utf-8";
		
	// 支付类型 ，无需修改
	private  String payment_type = "1";
		
	// 调用的接口名，无需修改
	private  String service = "alipay.wap.create.direct.pay.by.user";

}

