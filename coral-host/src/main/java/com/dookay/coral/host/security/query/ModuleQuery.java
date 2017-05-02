package com.dookay.coral.host.security.query;

import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.host.security.domain.ModuleDomain;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 系统模块信息表的Query
 * 
 * @author : luxor
 * @since : 2016年11月19日
 * @version : v0.0.1
 */
public class ModuleQuery extends Query {

	/* id */
	private List<Long> idList;
	/* 模块标签 */
	private String label;

	public ModuleQuery() {
		super();
	}

	public ModuleQuery(List<Long> idList, boolean desc, String orderBy) {
		super();
		this.idList = idList;
		this.setDesc(desc);
		this.setOrderBy(orderBy);

	}

	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(ModuleDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();

		// todo 写查询逻辑
		if (valid(idList))
			criteria.andIn("id", idList);
		if (valid(idList))
			criteria.andEqualTo("label", label);
		
		return queryCriteria;
	}

	public List<Long> getIdList() {
		return idList;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	

}
