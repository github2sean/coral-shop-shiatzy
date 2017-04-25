package com.dookay.coral.common.json.model;

import java.io.Serializable;

/**
 * json 返回模型对象
 * @since : 2016年11月9日
 * @author : kezhan
 * @version : v0.0.1
 */
public class ResultModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 应用状态码
	 */
	private int status = 0;

	/**
	 * 返回消息
	 */
	private String msg;

	/**
	 * 业务状态码
	 */
	private int code = 0;

	/**
	 * 返回对象数据
	 */
	private Object data;

	public ResultModel() {
		super();
	}

	/**
	 * 返回状态模型
	 * @param status
	 * @param msg
	 * @param code
	 */
	public ResultModel(int status, String msg, int code) {
		super();
		this.status = status;
		this.msg = msg;
		this.code = code;
	}

	/**
	 * 返回带数据对象模型
	 * @param status
	 * @param msg
	 * @param code
	 * @param data
	 */
	public ResultModel(int status, String msg, int code, Object data) {
		super();
		this.status = status;
		this.msg = msg;
		this.code = code;
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
