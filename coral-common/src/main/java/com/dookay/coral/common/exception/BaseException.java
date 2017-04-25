package com.dookay.coral.common.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * 顶级异常对象
 * @since : 2016年11月8日
 * @author : kezhan
 * @version : v0.0.1
 */
public class BaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 业务状态码
	 */
	private int code;

	/**
	 * 扩展参数
	 */
	private Map<Object, Object> extendMap;

	public BaseException(int code, String message) {
		super(message);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Map<Object, Object> getExtendMap() {
		return extendMap;
	}

	public void setExtendMap(Map<Object, Object> extendMap) {
		this.extendMap = extendMap;
	}

	public void put(Object key, Object obj) {
		if (this.extendMap == null) {
			this.extendMap = new HashMap<Object, Object>();
		}
		this.extendMap.put(key, obj);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Object key) {
		if (extendMap == null) {
			return null;
		}
		return (T) this.extendMap.get(key);
	}

}
