package com.dookay.coral.shop.shoppingcart.query;

import com.dookay.coral.common.persistence.Query;

import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.shoppingcart.domain.ContentItemDomain;
import tk.mybatis.mapper.entity.Example;

/**
 * 内容的Query
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
public class ContentItemQuery extends Query {
	
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(ContentItemDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		
		//todo 写查询逻辑
		return queryCriteria;
	}

}
