package com.dookay.coral.common.exception;

/**
 * constraintViolations异常处理
 * @author : kezhan
 * @since : 2016年12月6日
 * @version : v0.0.1
 */
public class ConstraintViolationsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 业务状态码
	 */
	private int code;

	/**
	 * 业务对象
	 */
	private Object object;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	
	public ConstraintViolationsException(int code, Object object) {
		this.object = object;
		this.code = code;
	}

}
