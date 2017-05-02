package com.dookay.coral.host.security.query;

import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.host.security.domain.RoleAssignDomain;
import tk.mybatis.mapper.entity.Example;

/**
 * 系统角色授权表的Query
 * @author : luxor
 * @since : 2016年11月19日
 * @version : v0.0.1
 */
public class RoleAssignQuery extends Query {

	/*管理员id*/
	private Long adminId;
	private Long roleId;

	public RoleAssignQuery() {
		super();
	}

	public RoleAssignQuery(Long adminId) {
		super();
		this.adminId = adminId;
	}

	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(RoleAssignDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		
		// todo 写查询逻辑
		if(valid(adminId))
			criteria.andEqualTo("adminId", adminId);
		if(valid(roleId)){
			criteria.andEqualTo("roleId",roleId);
		}
		return queryCriteria;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}
