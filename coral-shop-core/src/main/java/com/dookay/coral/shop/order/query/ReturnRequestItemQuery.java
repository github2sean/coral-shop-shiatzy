package com.dookay.coral.shop.order.query;

import com.dookay.coral.common.persistence.Query;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.order.domain.ReturnRequestItemDomain;

/**
 * 订单退货申请明细的Query
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
public class ReturnRequestItemQuery extends Query {
	
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(ReturnRequestItemDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		
		//todo 写查询逻辑
		return queryCriteria;
	}

}