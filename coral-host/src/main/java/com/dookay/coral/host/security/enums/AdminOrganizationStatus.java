package com.dookay.coral.host.security.enums;


import com.dookay.coral.common.enums.IEnum;

/**
 * 管理员 组织 关联状态 枚举
 * @author : kezhan
 * @since : 2017年1月17日
 * @version : v0.0.1
 */
public enum AdminOrganizationStatus implements IEnum {
	/**
	 * 状态
	 */
	NO(0, "正常"),
	OFF(1, "关闭");
	
	private int value;
	private String description;
	
	AdminOrganizationStatus(int value, String description) {
		this.value = value;
		this.description = description;
	}
	
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return description;
	}

	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return value;
	}

	public static AdminOrganizationStatus valueOf(Integer value) {
		AdminOrganizationStatus[] values = AdminOrganizationStatus.values();
		for (AdminOrganizationStatus item : values) {
			if (item.value == value) {
				return item;
			}
		}
		return null;
	}
}
