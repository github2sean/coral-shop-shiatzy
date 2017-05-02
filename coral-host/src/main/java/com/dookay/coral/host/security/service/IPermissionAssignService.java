package com.dookay.coral.host.security.service;



import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.host.security.domain.PermissionAssignDomain;

import java.util.Set;

/**
 * 系统模块信息表的业务层接口
 * @author : luxor
 * @since : 2016年11月19日
 * @version : v0.0.1
 */
public interface IPermissionAssignService extends IBaseService<PermissionAssignDomain> {
	
	/**
	 * 根据角色ID列表 获取权限ID列表
	 * @param roles
	 * @return
	 * @since : 2016年11月21日
	 * @author : kezhan
	 */
	Set<Long> getPermissionsAssignId(Set<Long> roleIds);

}
