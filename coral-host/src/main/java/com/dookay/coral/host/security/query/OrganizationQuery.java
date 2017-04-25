package com.dookay.coral.host.security.query;

import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.host.security.domain.OrganizationDomain;
import tk.mybatis.mapper.entity.Example;

/**
 * 部门公司组织表的Query
 * @author : luxor
 * @since : 2017年03月02日
 * @version : v0.0.1
 */
public class OrganizationQuery extends Query {
	
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(OrganizationDomain.class);
	 Example.Criteria criteria = queryCriteria.createCriteria();
		
		//todo 写查询逻辑
		return queryCriteria;
	}

}
