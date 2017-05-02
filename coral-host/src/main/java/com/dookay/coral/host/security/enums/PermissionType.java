package com.dookay.coral.host.security.enums;


import com.dookay.coral.common.enums.IEnum;

/**
 * 系统资源类型枚举
 * @author : kezhan
 * @since : 2017年2月7日
 * @version : v0.0.1
 */
public enum PermissionType implements IEnum {

	CLICK("click"), 
	MENU("menu"),
	BUTTON("button"), 
	ATTRIBUTE("attribute");
	
	private String type;
	
	PermissionType(String type) {
		this.type = type;
	}
	
	/**
	 * 资源类型
	 * @return
	 * @author : kezhan	
	 * @since : 2016年12月13日
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
