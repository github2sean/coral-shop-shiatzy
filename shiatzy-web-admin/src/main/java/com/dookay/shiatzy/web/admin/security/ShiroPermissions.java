package com.dookay.shiatzy.web.admin.security;


import com.dookay.coral.host.security.enums.PermissionAvailableType;
import com.dookay.coral.host.security.enums.PermissionType;

import java.lang.annotation.*;


/**
 * Shiro 权限管理 自定义注解
 * 此注解用于权限字符串反射生成处理
 * @author : kezhan
 * @since : 2017年3月2日
 * @version : v0.0.1
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ShiroPermissions {

	/**
	 * 菜单名称
	 * @return
	 * @author : kezhan	
	 * @since : 2016年12月21日
	 */
	String name() default "";

	/**
	 * 菜单类型
	 * 默认 menu 类型
	 * @return
	 * @author : kezhan	
	 * @since : 2016年12月21日
	 */
	PermissionType type() default PermissionType.MENU;
	
	/**
	 * 父级权限字符串
	 * @return
	 * @author : kezhan	
	 * @since : 2016年12月21日
	 */
	String parentPermissions() default "";
	
	/**
	 * 模块标签
	 * @return
	 * @author : kezhan	
	 * @since : 2016年12月21日
	 */
	String moduleLabel() default "";
	
	/**
	 * 权限是否可用
	 * 默认可用
	 * @return
	 * @author : kezhan	
	 * @since : 2016年12月21日
	 */
	PermissionAvailableType available() default PermissionAvailableType.ON;
	
}
