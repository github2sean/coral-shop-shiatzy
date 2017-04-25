package com.dookay.coral.shop.goods.query;

import com.dookay.coral.common.persistence.Query;

import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.goods.domain.GoodsPrototypeSpecificationDomain;
import tk.mybatis.mapper.entity.Example;

/**
 * 原型规格的Query
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
public class GoodsPrototypeSpecificationQuery extends Query {
	
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(GoodsPrototypeSpecificationDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		
		//todo 写查询逻辑
		return queryCriteria;
	}

}
