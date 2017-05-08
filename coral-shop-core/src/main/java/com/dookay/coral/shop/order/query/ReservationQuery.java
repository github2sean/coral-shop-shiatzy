package com.dookay.coral.shop.order.query;

import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.criteria.Criteria;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.order.domain.ReservationDomain;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;

/**
 * 预约单的Query
 * @author : luxor
 * @since : 2017年05月03日
 * @version : v0.0.1
 */
@Data
public class ReservationQuery extends Query {

	private Long customerId;
	private String reservationNo;
	
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(ReservationDomain.class);
		Example.Criteria criteria =queryCriteria.createCriteria();


		if(valid(customerId)){
			criteria.andEqualTo("customerId",customerId);
		}

		if(valid(reservationNo)){
			criteria.andLike("reservationNo",""+reservationNo+"");
		}

		//todo 写查询逻辑
		return queryCriteria;
	}

}
