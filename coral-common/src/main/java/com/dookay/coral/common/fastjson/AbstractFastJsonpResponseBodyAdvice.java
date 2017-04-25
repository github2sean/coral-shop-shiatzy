package com.dookay.coral.common.fastjson;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.dookay.coral.common.json.JsonUtils;

/**
 * fastjson实现jsonp 主要处理ResponseBody数据返回
 * @since : 2016年11月8日
 * @author : kezhan
 * @version : v0.0.1
 */
public abstract class AbstractFastJsonpResponseBodyAdvice implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter returnType,	Class<? extends HttpMessageConverter<?>> converterType) {
		return FastJsonHttpMessageConverter.class.isAssignableFrom(converterType);
	}

	@Override
	public Object beforeBodyWrite(Object resultModel, MethodParameter returnType, MediaType contentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, 
					ServerHttpResponse response) {
		HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
		if (JsonUtils.isJsonp(servletRequest)) {
			response.getHeaders().setContentType(getContentType(contentType, request, response));
			return new JsonpModel(JsonUtils.getJsonpFunctionName(servletRequest),resultModel);
		}
		return resultModel;
	}
	
	protected MediaType getContentType(MediaType contentType, ServerHttpRequest request, ServerHttpResponse response) {
		return new MediaType("application", "json");
	}

}
