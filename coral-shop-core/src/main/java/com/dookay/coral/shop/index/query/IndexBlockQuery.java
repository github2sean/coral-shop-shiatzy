package com.dookay.coral.shop.index.query;

import com.dookay.coral.common.persistence.Query;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.index.domain.IndexBlockDomain;

/**
 * 首页区块的Query
 * @author : luxor
 * @since : 2017年06月13日
 * @version : v0.0.1
 */
public class IndexBlockQuery extends Query {
	
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(IndexBlockDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		
		//todo 写查询逻辑
		return queryCriteria;
	}

}