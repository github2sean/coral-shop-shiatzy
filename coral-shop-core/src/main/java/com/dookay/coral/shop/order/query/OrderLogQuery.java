package com.dookay.coral.shop.order.query;

import com.dookay.coral.common.persistence.Query;

import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.order.domain.OrderLogDomain;
import tk.mybatis.mapper.entity.Example;

/**
 * 订单日志的Query
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
public class OrderLogQuery extends Query {
	
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(OrderLogDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		
		//todo 写查询逻辑
		return queryCriteria;
	}

}
