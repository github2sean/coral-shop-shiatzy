package com.dookay.coral.common.json;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dookay.coral.common.enums.OprationStatus;
import com.dookay.coral.common.fastjson.JsonpModel;
import com.dookay.coral.common.json.model.ResultModel;


/**
 * json处理工具类
 * @since : 2016年11月9日
 * @author : kezhan
 * @version : v0.0.1
 */
public class JsonUtils {

	/**
	 * jsonp函数名称
	 */
	public static final String[] jsonpFunctionNames = { "callback", "jsonp" };

	/**
	 * contentType
	 */
	public static final String mediaType = "text/json; charset=UTF-8";

	/**
	 * 编码集
	 */
	public final static String UTF8 = "UTF-8";

	/**
	 * fastjson
	 */
	public final static SerializerFeature[] features = { SerializerFeature.WriteNullBooleanAsFalse,
			SerializerFeature.QuoteFieldNames, SerializerFeature.WriteDateUseDateFormat,
			SerializerFeature.WriteNullStringAsEmpty };

	/**
	 *  判断是否是一个jsonp请求
	 * @param request
	 * @return
	 * @since : 2016年11月9日
	 * @author : kezhan
	 */
	public static boolean isJsonp(HttpServletRequest request) {
		for (String jsonpFunctionName : jsonpFunctionNames) {
			if (request.getParameter(jsonpFunctionName) != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取jsonp名称
	 * @param request
	 * @return
	 * @since : 2016年11月9日
	 * @author : kezhan
	 */
	public static String getJsonpFunctionName(HttpServletRequest request) {
		for (String jsonpFunctionName : jsonpFunctionNames) {
			if (request.getParameter(jsonpFunctionName) != null) {
				return request.getParameter(jsonpFunctionName);
			}
		}
		return null;
	}

	/**
	 * 转jsonp
	 * @param resultModel
	 * @return
	 * @since : 2016年11月9日
	 * @author : kezhan
	 */
	public static String toJsonOrJsonp(Object resultModel) {
		String jsonpFunctionName = null;
		if (resultModel instanceof JsonpModel) {
			jsonpFunctionName = ((JsonpModel) resultModel).getJsonpFunctionName();
			resultModel = ((JsonpModel) resultModel).getResult();
		}
		String text = toJSONString(resultModel);
		return StringUtils.isBlank(jsonpFunctionName) ? text
				: String.format("%s(%s)", jsonpFunctionName, text);
	}
	
	public static void outputMessage(HttpServletRequest request, HttpServletResponse response, String message) throws IOException {
		Object resultModel = JsonUtils.toErrorResult(message);
		if (JsonUtils.isJsonp(request)) {
			resultModel = new JsonpModel(JsonUtils.getJsonpFunctionName(request), resultModel);
		}
		outputMessage(response, JsonUtils.toJsonOrJsonp(resultModel));
	}

	public static void outputMessage(HttpServletRequest request, HttpServletResponse response, Integer code, String message)
			throws IOException {
		Object resultModel = JsonUtils.toErrorResult(code, message);
		if (JsonUtils.isJsonp(request)) {
			resultModel = new JsonpModel(JsonUtils.getJsonpFunctionName(request), resultModel);
		}
		outputMessage(response, JsonUtils.toJsonOrJsonp(resultModel));
	}

	/**
	 * 输出到客户端
	 * @param response
	 * @param message
	 * @throws IOException
	 * @since : 2016年11月9日
	 * @author : kezhan
	 */
	public static void outputMessage(HttpServletResponse response, String message)
			throws IOException {
		response.setContentType("text/json; charset=UTF-8");
		response.setCharacterEncoding(UTF8);
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(message);
	}

	/**
	 * 对象转jsonstring
	 * @param object
	 * @return
	 * @since : 2016年11月9日
	 * @author : kezhan
	 */
	public static String toJSONString(Object object) {
		return toJSONString(object, false);
	}

	/**
	 * 对象转jsonstring
	 * @param object
	 * @param format
	 * @return
	 * @since : 2016年11月9日
	 * @author : kezhan
	 */
	public static String toJSONString(Object object, boolean format) {
		if (format) {
			return JSON.toJSONString(
					object, SerializerFeature.WriteNonStringKeyAsString,
					SerializerFeature.WriteNullStringAsEmpty,
					SerializerFeature.DisableCircularReferenceDetect,
					SerializerFeature.WriteMapNullValue, SerializerFeature.PrettyFormat);
		} else {
			return JSON.toJSONString(
					object, SerializerFeature.WriteNonStringKeyAsString,
					SerializerFeature.WriteNullStringAsEmpty,
					SerializerFeature.DisableCircularReferenceDetect,
					SerializerFeature.WriteMapNullValue);
		}
	}
	
	/**
	 * 判断是否是一个Ajax请求
	 * @param handler
	 * @return
	 * @since : 2016年11月9日
	 * @author : kezhan
	 */
	public static boolean isAjax(Object handler) {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			ResponseBody annotation = handlerMethod.getMethodAnnotation(ResponseBody.class);
			if (annotation != null) {
				return true;
			}
		}
		return false;
	}


	public static boolean isAjaxRequest( HttpServletRequest httpRequest){

		return Objects.equals(httpRequest.getHeader("X-Requested-With"), "XMLHttpRequest") ||
				httpRequest.getHeader("Accept").contains("application/json");
	}


	public static ResultModel toResult(Object obj) {
		return new ResultModel(HttpStatus.OK.value(), "ok", OprationStatus.NORMAL.getCode(), obj);
	}

	public static ResultModel toAgainResult(Object obj) {
		return new ResultModel(HttpStatus.OK.value(), "ok", OprationStatus.AGAIN.getCode(), obj);
	}

	public static ResultModel toResult(String message) {
		return new ResultModel(HttpStatus.OK.value(), message, OprationStatus.NORMAL.getCode());
	}

	public static ResultModel toErrorResult(String message) {
		return new ResultModel(HttpStatus.FORBIDDEN.value(), message, OprationStatus.ERROR.getCode());
	}

	public static ResultModel toErrorResult(int code, String message) {
		return new ResultModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, code);
	}

	public static ResultModel toResult(int code, Object obj) {
		return new ResultModel(HttpStatus.OK.value(), "ok", code, obj);
	}

	public static List<Long> toLongArray(String json){
		List<Long> longArrayList = new ArrayList<>();
		JSONArray jsonArray = JSONArray.fromObject(json);
		for (int i = 0; i < jsonArray.size(); i++) {
			longArrayList.add(jsonArray.getLong(i));
		}
		return longArrayList;
	}

	public static List<String> toStringArray(String json){
		List<String> stringArrayList = new ArrayList<>();
		JSONArray jsonArray = JSONArray.fromObject(json);
		for (int i = 0; i < jsonArray.size(); i++) {
			stringArrayList.add(jsonArray.getString(i));
		}
		return stringArrayList;
	}
}
