package com.dookay.coral.common.utils;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * web工具类
 * @since : 2016年11月8日
 * @author : kezhan
 * @version : v0.0.1
 */
public class ServletUtil {

	
	/**
	 * 获取request中参数集合，如果有多个值的，获取的是数组
	 * @param request
	 * @param isParams
	 * @return
	 * @since : 2016年11月8日
	 * @author : kezhan
	 */
	public static Map<String, Object> getParameterMap(HttpServletRequest request, boolean isParams) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		Map<String, String[]> paramMap = request.getParameterMap();
		for (Iterator<String> iter = paramMap.keySet().iterator(); iter.hasNext();) {
			String key = iter.next();
			Object value = null;
			Object obj = paramMap.get(key);
			if (isParams) {
				value = obj;
			} else {
				if ((obj.getClass().isArray()) && Array.getLength(obj) >= 1) {
					value = Array.get(obj, 0);
				}
			}
			if (value != null) {
				if (value.getClass().isArray()) {
					int len = Array.getLength(value);
					for (int i = 0; i < len; i++) {
						Object temp = Array.get(value, i);
						Array.set(value, i,	temp != null ? temp.toString().trim() : null);
					}
				} else {
					value = value != null ? value.toString().trim() : null;
				}
			}
			parameterMap.put(key, value);
		}
		return parameterMap;
	}
}
