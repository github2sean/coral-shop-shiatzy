package com.dookay.coral.common.exception;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.json.model.ResultModel;

/**
 * 异常处理工具类
 * @since : 2016年11月8日
 * @author : kezhan
 * @version : v0.0.1
 */
public class ExceptionUtils implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 抛出异常 message
	 * @param message
	 * @since : 2016年11月9日
	 * @author : kezhan
	 */
	public static void throwBaseException(String message) {
		throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
	}

	/**
	 * 抛出异常 数据验证异常
	 * @param object
	 * @author : kezhan	
	 * @since : 2016年12月6日
	 *
	 */
	public static void throwConstraintViolationsException(Object object) {
		throw new ConstraintViolationsException(HttpStatus.INTERNAL_SERVER_ERROR.value(), object);
	}
	
	/**
	 * 抛出异常
	 * @param code
	 * @param message
	 * @since : 2016年11月9日
	 * @author : kezhan
	 */
	public static void throwBaseException(int code, String message) {
		throw new BaseException(code, message);
	}
	
	/**
	 * 抛出异常
	 * @param status
	 * @param message
	 * @since : 2016年11月9日
	 * @author : kezhan
	 */
	public static void throwException(int status, String message) {
		throw new BaseException(status, message);
	}
	
	/**
	 * 输出到客户端
	 * @param response
	 * @param code
	 * @param message
	 * @throws IOException
	 * @since : 2016年11月9日
	 * @author : kezhan
	 */
	public static void outputMessage(HttpServletResponse response, int code, String message) throws IOException {
		JsonUtils.outputMessage(response, JsonUtils.toJSONString(
				new ResultModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, code)));
	}

	/**
	 * 根据请求类型输出json或跳转到异常页面
	 * @param response
	 * @param handler
	 * @param code
	 * @param message
	 * @throws IOException
	 * @since : 2016年11月9日
	 * @author : kezhan
	 */
	public static void throwBaseException(HttpServletResponse response, Object handler, int code, String message) throws IOException {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			ResponseBody annotation = handlerMethod.getMethodAnnotation(ResponseBody.class);
			if (annotation != null) {
				ExceptionUtils.outputMessage(response, code, message);
				return;
			}
		}
		ExceptionUtils.throwBaseException(code, message);
	}

}
