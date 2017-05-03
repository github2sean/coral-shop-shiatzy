package com.dookay.coral.shop.order.query;

import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.criteria.Criteria;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.order.domain.ReservationItemDomain;
import tk.mybatis.mapper.entity.Example;

/**
 * 预约单明细的Query
 * @author : luxor
 * @since : 2017年05月03日
 * @version : v0.0.1
 */
public class ReservationItemQuery extends Query {
	
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(ReservationItemDomain.class);
		Example.Criteria criteria =queryCriteria.createCriteria();
		
		//todo 写查询逻辑
		return queryCriteria;
	}

}
