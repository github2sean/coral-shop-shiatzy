package com.dookay.coral.common.model;

/**
 * 文件上传结果类
 */
public class FineUploaderResultModel {
	private boolean isSuccess;
	private String message;
	private String filePath;


	public boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
