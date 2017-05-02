package com.dookay.coral.host.security.query;

import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.host.security.domain.PermissionDomain;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 系统模块信息表的Query
 * 
 * @author : luxor
 * @since : 2016年11月19日
 * @version : v0.0.1
 */
public class PermissionQuery extends Query {

	/* 权限id */
	private Long id;
	/* 是否可用 0:true 1:false */
	private Integer available;
	/* 模块id */
	private Long moduleId;
	/* 父级id */
	private Long parentId;
	/* 菜单类型 */
	private String type;
	/*id集合*/
	private List<Long> idList;
	/*url*/
	private String url;
	/*权限字符串*/
	private String permission;

	public PermissionQuery() {
		super();
	}

	public PermissionQuery(Long id, Integer available, boolean desc, String orderBy) {
		super();
		this.id = id;
		this.available = available;
		this.setDesc(desc);
		this.setOrderBy(orderBy);
	}
	
	public PermissionQuery(List<Long> idList, Integer available, boolean desc, String orderBy) {
		super();
		this.idList = idList;
		this.available = available;
		this.setDesc(desc);
		this.setOrderBy(orderBy);
	}
	
	public PermissionQuery(Long moduleId, Long parentId,  String type, Integer available, boolean desc, String orderBy) {
		super();
		this.moduleId = moduleId;
		this.parentId = parentId;
		this.type = type;
		this.available = available;
		this.setDesc(desc);
		this.setOrderBy(orderBy);
	}


	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(PermissionDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();

		// todo 写查询逻辑
		if (valid(id))
			criteria.andEqualTo("id", id);
		if (valid(available))
			criteria.andEqualTo("available", available);
		if (valid(moduleId))
			criteria.andEqualTo("moduleId", moduleId);
		if (valid(type))
			criteria.andEqualTo("type", type);
		if (valid(parentId))
			criteria.andEqualTo("parentId", parentId);
		if (valid(idList))
			criteria.andIn("id", idList);
		if (valid(url))
			criteria.andEqualTo("url", url);
		if (valid(permission))
			criteria.andEqualTo("permission", permission);
		
		return queryCriteria;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Long> getIdList() {
		return idList;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

}
