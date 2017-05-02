package com.dookay.coral.shop.promotion.query;

import com.dookay.coral.common.persistence.Query;

import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.promotion.domain.CouponDomain;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;

/**
 * 优惠券的Query
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@Data
public class CouponQuery extends Query {

	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(CouponDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();

		//todo 写查询逻辑
		return queryCriteria;
	}
}
