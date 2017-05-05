package com.dookay.coral.shop.order.query;

import com.dookay.coral.common.persistence.Query;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.order.domain.OrderLogDomain;

/**
 * 订单日志的Query
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
public class OrderLogQuery extends Query {

	private Long orderId;

	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(OrderLogDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();


		if(valid(orderId)){
			criteria.andEqualTo("orderId",orderId);
		}

		//todo 写查询逻辑
		return queryCriteria;
	}

}
