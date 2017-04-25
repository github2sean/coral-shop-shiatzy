package com.dookay.coral.shop.goods.query;

import com.dookay.coral.common.persistence.Query;

import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.goods.domain.GoodsPrototypeAttributeDomain;
import tk.mybatis.mapper.entity.Example;

/**
 * 原型属性的Query
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
public class GoodsPrototypeAttributeQuery extends Query {
	
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(GoodsPrototypeAttributeDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		
		//todo 写查询逻辑
		return queryCriteria;
	}

}
