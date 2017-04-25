package com.dookay.coral.shop.order.query;

import com.dookay.coral.common.persistence.Query;

import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.order.domain.OrderDomain;
import tk.mybatis.mapper.entity.Example;

/**
 * 商品sku规格值的Query
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
public class OrderQuery extends Query {
	
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(OrderDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		
		//todo 写查询逻辑
		return queryCriteria;
	}

}
