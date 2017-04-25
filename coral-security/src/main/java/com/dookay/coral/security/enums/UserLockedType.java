package com.dookay.coral.security.enums;


/**
 * 用户状态枚举
 * @since : 2016年11月21日
 * @author : kezhan
 * @version : v0.0.1
 */
public enum UserLockedType {

	ON(0, "正常"), 
	OFF(1, "锁定");

	private int value;
	private String description;

	UserLockedType(int value, String description) {
		this.value = value;
		this.description = description;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getDescription() {
		return description;
	}

}
