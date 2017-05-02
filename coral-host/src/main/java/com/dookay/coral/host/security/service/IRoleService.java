package com.dookay.coral.host.security.service;
import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.host.security.domain.AdminDomain;
import com.dookay.coral.host.security.domain.RoleDomain;

import java.util.Set;

/**
 * 系统角色表的业务层接口
 * @author : luxor
 * @since : 2016年11月19日
 * @version : v0.0.1
 */
public interface IRoleService extends IBaseService<RoleDomain> {
	
	/**
	 * 根据角色ID获取角色
	 * @param roleIds
	 * @return
	 * @since : 2016年11月21日
	 * @author : kezhan
	 */
	Set<RoleDomain> getRoles(Set<Long> roleIds);

	/**
	 * 添加角色
	 * @param roleDomain
	 * @author : kezhan	
	 * @since : 2016年12月7日
	 *
	 */
	void createRole(RoleDomain roleDomain);
	
	/**
	 * 编辑角色
	 * @param roleDomain
	 * @author : kezhan	
	 * @since : 2016年12月7日
	 *
	 */
	void updateRole(RoleDomain roleDomain);
	
	/**
	 * 角色授权
	 * @param id 角色id
	 * @param clickId  一级菜单集合
	 * @param menuId 二级菜单集合
	 * @param attributeId 三级菜单集合
	 * @author : kezhan	
	 * @since : 2016年12月9日
	 */
	void rolePermissions(Long id, Set<Long> clickId, Set<Long> menuId, Set<Long> attributeId);

	RoleDomain getRole(AdminDomain adminDomain);
}
