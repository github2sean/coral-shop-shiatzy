package com.dookay.coral.host.security.service;


import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.host.security.domain.PermissionDomain;

import java.util.Set;

/**
 * 系统模块信息表的业务层接口
 * @author : luxor
 * @since : 2016年11月19日
 * @version : v0.0.1
 */
public interface IPermissionService extends IBaseService<PermissionDomain> {
	
	/**
	 * 根据权限ID获取权限标识
	 * @param permissionsId  权限ID列表
	 * @return
	 * @since : 2016年11月21日
	 * @author : kezhan
	 */
	Set<PermissionDomain> getPermissionsId(Set<Long> permissionsId);

}
