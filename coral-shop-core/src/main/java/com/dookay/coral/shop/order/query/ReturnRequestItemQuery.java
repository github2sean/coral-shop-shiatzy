package com.dookay.coral.shop.order.query;

import com.dookay.coral.common.persistence.Query;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.order.domain.ReturnRequestItemDomain;

/**
 * 订单退货申请明细的Query
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
public class ReturnRequestItemQuery extends Query {

	private Long returnRequestId;

	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(ReturnRequestItemDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();

		if(valid(returnRequestId)){
			criteria.andEqualTo("returnRequestId",returnRequestId);
		}

		//todo 写查询逻辑
		return queryCriteria;
	}

}
