package com.dookay.coral.host.security.enums;


import com.dookay.coral.common.enums.IEnum;

/**
 * 管理员角色状态 枚举
 * @since : 2016年11月22日
 * @author : kezhan
 * @version : v0.0.1
 */
public enum RoleAvailableType implements IEnum {
	
	ON(0, "可用"), 
	OFF(1, "关闭");

	private int value;
	private String description;

	RoleAvailableType(int value, String description) {
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
