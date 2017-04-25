package com.dookay.coral.common.utils;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * xss工具类 防止sql注入
 * @since : 2016年11月8日
 * @author : kezhan
 * @version : v0.0.1
 */
public class XssUtil {

	private static Logger logger = Logger.getLogger(XssUtil.class);
	
	/**
	 * 过滤xss攻击 form提交处理拦截
	 * @param request
	 * @return
	 * @since : 2016年11月8日
	 * @author : kezhan
	 */
	public static boolean filterXss(HttpServletRequest request) {
		boolean result = true;
		// 过滤掉一些可能引起攻击的代码
		Map<String, String[]> paramMap = request.getParameterMap();
		XssHTMLUtil xssHTMLUtil = new XssHTMLUtil();
		for (Iterator<String> iter = paramMap.keySet().iterator(); iter.hasNext();) {
			String key = iter.next();
			Object obj = paramMap.get(key);
			if (obj != null && (obj instanceof String[])) {
				String[] paraValues = (String[]) obj;
				for (int i = 0; i < paraValues.length; i++) {
					String originalValue = paraValues[i];
					paraValues[i] = xssHTMLUtil.filter(paraValues[i]);
					if (originalValue != null && !originalValue.equals(paraValues[i])) {
						logger.info(
								"xss:[key=" + key + ",originalValue=" + originalValue + ",value="
										+ paraValues[i]);
						result = false;
					}
				}
			}
		}
		return result;
	}

}
