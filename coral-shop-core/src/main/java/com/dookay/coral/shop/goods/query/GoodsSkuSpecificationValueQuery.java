package com.dookay.coral.shop.goods.query;

import com.dookay.coral.common.persistence.Query;

import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.goods.domain.GoodsSkuSpecificationValueDomain;
import tk.mybatis.mapper.entity.Example;

/**
 * 商品sku规格值的Query
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
public class GoodsSkuSpecificationValueQuery extends Query {
	
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(GoodsSkuSpecificationValueDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		
		//todo 写查询逻辑
		return queryCriteria;
	}

}
