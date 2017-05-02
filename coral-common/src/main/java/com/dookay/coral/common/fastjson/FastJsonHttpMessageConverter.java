package com.dookay.coral.common.fastjson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.json.model.ResultModel;

/**
 * fastjson jsonp处理
 * @since : 2016年11月9日
 * @author : kezhan
 * @version : v0.0.1
 */
public class FastJsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

	private static String charsetName = JsonUtils.UTF8;
	
	private static Charset charset = Charset.forName(charsetName);

	public FastJsonHttpMessageConverter() {
		super(new MediaType("application", "json", charset),
				new MediaType("application", "*+json", charset));
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return true;
	}
	
	@Override
	protected ResultModel readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage)
					throws IOException, HttpMessageNotReadableException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream in = inputMessage.getBody();
		byte[] buf = new byte[1024];
		for (;;) {
			int len = in.read(buf);
			if (len == -1) {
				break;
			}
			
			if (len > 0) {
				baos.write(buf, 0, len);
			}
		}
		byte[] bytes = baos.toByteArray();
		return JSON.parseObject(bytes, 0, bytes.length, charset.newDecoder(), clazz);
	}
	
	@Override
	protected void writeInternal(Object resultModel, HttpOutputMessage outputMessage)
					throws IOException, HttpMessageNotWritableException {
		OutputStream out = outputMessage.getBody();
		// jsonp
		String text = JsonUtils.toJsonOrJsonp(resultModel);
		out.write(text.getBytes(charsetName));
	}

}
