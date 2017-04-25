package com.dookay.coral.host.user.query;

import com.dookay.coral.host.user.domain.AccountBindingDomain;
import com.dookay.coral.common.persistence.Query;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;

/**
 * 账户第三方扩展的Query
 * @author : stone
 * @since : 2017年03月02日
 * @version : v0.0.1
 */
public class AccountBindingQuery extends Query {
	
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(AccountBindingDomain.class);
	 Example.Criteria criteria = queryCriteria.createCriteria();
		
		//todo 写查询逻辑
		return queryCriteria;
	}

}
