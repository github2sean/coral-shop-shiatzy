package com.dookay.coral.host.security.enums;


import com.dookay.coral.common.enums.IEnum;

/**
 * 权限资源 状态 枚举
 * @since : 2016年11月22日
 * @author : kezhan
 * @version : v0.0.1
 */
public enum PermissionAvailableType implements IEnum {

	ON(0, "可用"), 
	OFF(1, "不可用");

	private int value;
	private String description;

	PermissionAvailableType(int value, String description) {
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
