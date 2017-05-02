package com.dookay.coral.host.security.query;

import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.host.security.domain.PermissionAssignDomain;
import tk.mybatis.mapper.entity.Example;

/**
 * 系统模块信息表的Query
 * @author : luxor
 * @since : 2016年11月19日
 * @version : v0.0.1
 */
public class PermissionAssignQuery extends Query {

	/*角色id*/
	private Long roleId;
	
	/*权限id*/
	private Long permissionId;

	public PermissionAssignQuery() {
		super();
	}
	
	public PermissionAssignQuery(Long roleId) {
		super();
		this.roleId = roleId;
	}
	
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(PermissionAssignDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		
		// todo 写查询逻辑
		if(valid(roleId))
			criteria.andEqualTo("roleId", roleId);
		if(valid(permissionId))
			criteria.andEqualTo("permissionId", permissionId);
		
		return queryCriteria;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

}
