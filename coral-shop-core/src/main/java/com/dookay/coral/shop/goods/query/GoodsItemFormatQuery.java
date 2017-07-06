package com.dookay.coral.shop.goods.query;

import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.criteria.Criteria;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.goods.domain.GoodsItemFormatDomain;
import tk.mybatis.mapper.entity.Example;

/**
 * 商品项目参数的Query
 * @author : luxor
 * @since : 2017年07月06日
 * @version : v0.0.1
 */
public class GoodsItemFormatQuery extends Query {
	
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(GoodsItemFormatDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();

		//todo 写查询逻辑
		return queryCriteria;
	}

}
