package com.dookay.coral.host.security.query;

import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.host.security.domain.AdminOrganizationDomain;
import com.dookay.coral.host.security.enums.AdminOrganizationStatus;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 管理员用户 组织关联表的Query
 * @author : kezhan
 * @since : 2017年01月17日
 * @version : v0.0.1
 */
public class AdminOrganizationQuery extends Query {

	private Long adminId;
	private Long organizationId;
	private Integer status;
	private List<Long> organizationIds;
	private List<Long> adminIds;
	/*是否默认公司:0 true 1 false 默认1 原因在与一个管理员因业务关系会出现对应多个分公司情况,查询时查询默认分公司*/
	private Integer defaultOrganization;

	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(AdminOrganizationDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		if(valid(adminIds)){
			criteria.andIn("adminId",adminIds);
		}
		//todo 写查询逻辑
		if (valid(adminId)) {
			criteria.andEqualTo("adminId", adminId);
		}
		if (valid(organizationId)) {
			criteria.andEqualTo("organizationId", organizationId);
		}
		if (valid(organizationIds)) {
            criteria.andIn("organizationId", organizationIds);
        }
		if (valid(status) && AdminOrganizationStatus.valueOf(status) != null) {
			criteria.andEqualTo("status", status);
		}
		if (valid(defaultOrganization)) {
			criteria.andEqualTo("defaultOrganization", defaultOrganization);
		}
		return queryCriteria;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Integer getDefaultOrganization() {
		return defaultOrganization;
	}

	public void setDefaultOrganization(Integer defaultOrganization) {
		this.defaultOrganization = defaultOrganization;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<Long> getOrganizationIds() {
		return organizationIds;
	}

	public void setOrganizationIds(List<Long> organizationIds) {
		this.organizationIds = organizationIds;
	}


	public List<Long> getAdminIds() {
		return adminIds;
	}

	public void setAdminIds(List<Long> adminIds) {
		this.adminIds = adminIds;
	}
}
