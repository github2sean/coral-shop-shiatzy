package com.dookay.coral.common.model;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件上传参数处理类
 */
public class FineUploaderParamModel {
	private final String _formName = "qqfilename";

	private String module;
	private int uploadType;
	private String fileName;

	public FineUploaderParamModel(HttpServletRequest request) {
		Map<String, String[]> map = request.getParameterMap();
		if (map.containsKey("module")) {
			this.module = map.get("module")[0];
		}
		
		UploadTypeEnum typeEnum = UploadTypeEnum.Document;

		if (map.containsKey("type")) {
			int t = Integer.parseInt(map.get("type")[0]);
			for (UploadTypeEnum typeEnum2 : UploadTypeEnum.values()) {
				if (typeEnum2.getValue() == t) {
					typeEnum = typeEnum2;
				}
			}
		}

		uploadType = typeEnum.getValue();

		if (map.containsKey(_formName)) {
			fileName = map.get(_formName)[0];
		}

	}

	public String getModule() {
		return module;
	}

	public int getUploadType() {
		return uploadType;
	}

	public String getFormName() {
		return _formName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public void setUploadType(int uploadType) {
		this.uploadType = uploadType;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
