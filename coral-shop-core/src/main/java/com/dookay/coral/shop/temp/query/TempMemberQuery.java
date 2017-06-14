package com.dookay.coral.shop.temp.query;


import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.temp.domain.TempMemberDomain;
import tk.mybatis.mapper.entity.Example;

/**
 * 临时会员的Query
 * @author : luxor
 * @since : 2017年06月14日
 * @version : v0.0.1
 */
public class TempMemberQuery extends Query {
	
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(TempMemberDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		
		//todo 写查询逻辑
		return queryCriteria;
	}

}
