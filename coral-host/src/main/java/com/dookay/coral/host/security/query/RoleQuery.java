package com.dookay.coral.host.security.query;


import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.common.utils.DateUtils;
import com.dookay.coral.host.security.domain.RoleDomain;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * 系统角色表的Query
 * @author : luxor
 * @since : 2016年11月19日
 * @version : v0.0.1
 */
public class RoleQuery extends Query {

	/*角色ID*/
	private Long id;
	/*是否可用 0:true 1:false*/
	private Integer available;

	/*角色名称*/
	private String name;
	/*创建时间*/
	private Date createDate;

	public RoleQuery() {
		super();
	}
	
	public RoleQuery(String name) {
		super();
		this.name = name;
	}

	public RoleQuery(Long id, Integer available) {
		super();
		this.id = id;
		this.available = available;
	}

	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(RoleDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();

		// todo 写查询逻辑
		if (valid(id))
			criteria.andEqualTo("id", id);
		if (valid(available))
			criteria.andEqualTo("available", available);
		if (valid(name))
			criteria.andEqualTo("name", name);
		if (valid(createDate)) {
			Date[] dayTime = DateUtils.getDayStartEnd(createDate);
			criteria.andGreaterThanOrEqualTo("createTime", dayTime[0]);
			criteria.andLessThanOrEqualTo("createTime", dayTime[1]);
		}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
