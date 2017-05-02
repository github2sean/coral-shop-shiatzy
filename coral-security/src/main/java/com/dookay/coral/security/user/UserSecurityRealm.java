package com.dookay.coral.security.user;

import java.util.List;
import java.util.Set;

/**
 * 用户安全领域处理
 * @author : kezhan
 * @since : 2016年12月1日
 * @version : v0.0.1
 */
public abstract class UserSecurityRealm {
	
	/**
	 * 用户登录处理
	 * @param userName
	 * @param password
	 * @param ip
	 * @author : kezhan	
	 * @since : 2016年12月1日
	 *
	 */
	public abstract AdminModel login(String userName, String password, String ip) throws Exception;
	
	public abstract  AdminModel getAdmin(String userName);
	/**
	 * 获取用户角色
	 * @param adminModel
	 * @return
	 * @throws Exception
	 * @author : kezhan	
	 * @since : 2016年12月1日
	 *
	 */
	public abstract Set<String> getAdminRoles(AdminModel adminModel);
	
	/**
	 * 获取用户权限
	 * @param adminModel
	 * @return
	 * @throws Exception
	 * @author : kezhan	
	 * @since : 2016年12月1日
	 *
	 */
	public abstract Set<String> getAdminPermissions(AdminModel adminModel);
	
	/**
	 * 获取用户信息
	 * @return
	 * @author : kezhan	
	 * @since : 2016年12月1日
	 *
	 */
	public abstract AdminModel getAdminModel(AdminModel adminModel);
	
	/**
	 * 获取菜单模块
	 * @return
	 * @author : kezhan	
	 * @since : 2016年12月1日
	 *
	 */
	public abstract List<ModuleModel> getModuleModels(AdminModel adminModel);
	
}
