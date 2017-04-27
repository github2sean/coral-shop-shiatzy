package com.dookay.coral.shop.goods.query;

import com.dookay.coral.common.persistence.Query;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.goods.domain.PrototypeSpecificationDomain;

/**
 * 原型规格的Query
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
public class PrototypeSpecificationQuery extends Query {
	
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(PrototypeSpecificationDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		
		//todo 写查询逻辑
		return queryCriteria;
	}

}
