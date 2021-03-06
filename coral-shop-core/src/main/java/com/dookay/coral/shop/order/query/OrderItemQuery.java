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

	private Integer returnNum;

	private String orderNo;


	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(OrderItemDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		if(valid(orderId)){
			criteria.andEqualTo("orderId",orderId);
		}
		if (valid(returnNum)){
			criteria.andGreaterThan("num",returnNum);
		}
		if (valid(orderNo)){
			criteria.andEqualTo("orderNo",orderNo);
		}
		//todo 写查询逻辑
		return queryCriteria;
	}

}
