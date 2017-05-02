package com.dookay.coral.shop.order.query;

import com.dookay.coral.common.persistence.Query;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.order.domain.OrderItemDomain;

/**
 * 订单明细的Query
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
public class OrderItemQuery extends Query {

	private Long orderId;


	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(OrderItemDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		if(valid(orderId)){
			criteria.andEqualTo("orderId",orderId);
		}
		//todo 写查询逻辑
		return queryCriteria;
	}

}
