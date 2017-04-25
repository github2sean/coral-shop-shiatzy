package com.dookay.coral.common.fastjson;

/**
 * jsonp对象
 * @since : 2016年11月9日
 * @author : kezhan
 * @version : v0.0.1
 */
public class JsonpModel {

	/**
	 * jsonp函数
	 */
	private String jsonpFunctionName;
	
	/**
	 * 返回结果
	 */
	private Object result;
	
	
	public JsonpModel(String jsonpFunctionName, Object result) {
		this.jsonpFunctionName = jsonpFunctionName;
		this.result = result;
	}

	public String getJsonpFunctionName() {
		return jsonpFunctionName;
	}

	public void setJsonpFunctionName(String jsonpFunctionName) {
		this.jsonpFunctionName = jsonpFunctionName;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
}
