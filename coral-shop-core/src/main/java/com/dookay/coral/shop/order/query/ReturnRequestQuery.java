package com.dookay.coral.shop.order.query;

import com.dookay.coral.common.persistence.Query;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.order.domain.ReturnRequestDomain;

/**
 * 订单退货申请的Query
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
public class ReturnRequestQuery extends Query {

	private Long customerId;
	private String orderNo;

	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(ReturnRequestDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();

		if(valid(customerId)){
			criteria.andEqualTo("customerId",customerId);
		}
		if (valid(orderNo)){
			criteria.andLike("orderNo","%"+orderNo+"%");
		}

		//todo 写查询逻辑
		return queryCriteria;
	}

}
