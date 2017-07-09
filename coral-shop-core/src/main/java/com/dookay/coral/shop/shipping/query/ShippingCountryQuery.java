package com.dookay.coral.shop.shipping.query;

import com.dookay.coral.common.persistence.Query;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.shipping.domain.ShippingCountryDomain;
import tk.mybatis.mapper.entity.Example;

/**
 * 配送国家的Query
 * @author : luxor
 * @since : 2017年05月03日
 * @version : v0.0.1
 */
public class ShippingCountryQuery extends Query {

	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(ShippingCountryDomain.class);
		Example.Criteria criteria =queryCriteria.createCriteria();
		
		//todo 写查询逻辑
		return queryCriteria;
	}

}
