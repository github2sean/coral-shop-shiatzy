package com.dookay.coral.shop.goods.query;

import com.dookay.coral.common.persistence.Query;

import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.goods.domain.GoodsAttributeValueDomain;
import tk.mybatis.mapper.entity.Example;

/**
 * 商品属性值的Query
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
public class GoodsAttributeValueQuery extends Query {
	
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(GoodsAttributeValueDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		
		//todo 写查询逻辑
		return queryCriteria;
	}

}
