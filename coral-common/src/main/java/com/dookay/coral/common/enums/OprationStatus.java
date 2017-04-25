package com.dookay.coral.common.enums;

/**
 * 系统通用状态码枚举
 * @since : 2016年11月8日
 * @author : kezhan
 * @version : v0.0.1
 */
public enum OprationStatus {

	NORMAL(0, "正常"), LOGIN(10001, "请登录"), OVERDUE(10002, "令牌过期"), ERROR(10003, "系统异常"),
	AGAIN(200, "重新绑定用户信息");

	/**
	 * code消息码
	 */
	private int code;

	/**
	 * 返回消息
	 */
	private String msg;

	private OprationStatus(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
