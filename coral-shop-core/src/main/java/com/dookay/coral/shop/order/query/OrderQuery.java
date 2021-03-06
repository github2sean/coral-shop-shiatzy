package com.dookay.coral.shop.order.query;

import com.dookay.coral.common.persistence.Query;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.order.domain.OrderDomain;

import java.util.Date;

/**
 * 商品sku规格值的Query
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
public class OrderQuery extends Query {

	private Long customerId;

	private Integer status;

	private Date orderTime;

	private String orderNo;

	private Integer paymentMethod;

	private Integer cod;

	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(OrderDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();

		if(valid(orderTime)){
			criteria.andEqualTo("orderTime",orderTime);
		}
		if(valid(customerId)){
			criteria.andEqualTo("customerId",customerId);
		}
		if(valid(status)){
			criteria.andEqualTo("status",status);
		}

		if(valid(orderNo)){
			criteria.andEqualTo("orderNo",orderNo);
		}

		if(valid(paymentMethod)){
			criteria.andEqualTo("paymentMethod",paymentMethod);
		}
		if(valid(cod)){
			criteria.andNotEqualTo("paymentMethod",cod);
		}

		//todo 写查询逻辑
		return queryCriteria;
	}

}
