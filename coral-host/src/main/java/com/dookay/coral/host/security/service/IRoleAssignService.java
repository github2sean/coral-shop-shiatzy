package com.dookay.coral.host.security.service;



import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.host.security.domain.AdminDomain;
import com.dookay.coral.host.security.domain.RoleAssignDomain;

import java.util.Set;

/**
 * 系统角色授权表的业务层接口
 * @author : luxor
 * @since : 2016年11月19日
 * @version : v0.0.1
 */
public interface IRoleAssignService extends IBaseService<RoleAssignDomain> {
	
	/**
	 * 根据管理员ID获取角色ID列表
	 * @param adminId
	 * @return
	 * @since : 2016年11月21日
	 * @author : kezhan
	 */
	Set<Long> getRolesId(AdminDomain adminDomain);
}
