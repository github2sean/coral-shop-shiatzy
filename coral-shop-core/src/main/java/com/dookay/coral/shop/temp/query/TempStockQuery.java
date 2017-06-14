package com.dookay.coral.shop.temp.query;


import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.temp.domain.TempStockDomain;
import tk.mybatis.mapper.entity.Example;

/**
 * 临时库存的Query
 * @author : luxor
 * @since : 2017年06月14日
 * @version : v0.0.1
 */
public class TempStockQuery extends Query {
	
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(TempStockDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		
		//todo 写查询逻辑
		return queryCriteria;
	}

}
