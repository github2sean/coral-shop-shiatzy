package com.dookay.coral.host.security.enums;


import com.dookay.coral.common.enums.IEnum;

/**
 * 管理员状态是否锁定枚举
 * @since : 2016年11月21日
 * @author : kezhan
 * @version : v0.0.1
 */
public enum AdminLockedType implements IEnum {

	ON(0, "正常"), 
	OFF(1, "锁定");

	private int value;
	private String description;

	AdminLockedType(int value, String description) {
		this.value = value;
		this.description = description;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public String getDescription() {
		return description;
	}

}
