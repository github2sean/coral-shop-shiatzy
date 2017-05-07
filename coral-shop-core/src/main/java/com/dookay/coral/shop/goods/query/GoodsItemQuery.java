package com.dookay.coral.shop.goods.query;

import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.shop.goods.domain.GoodsItemDomain;

/**
 * 商品项目的Query
 * @author : luxor
 * @since : 2017年05月06日
 * @version : v0.0.1
 */
public class GoodsItemQuery extends Query {
	
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(GoodsItemDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		
		//todo 写查询逻辑
		return queryCriteria;
	}

}
