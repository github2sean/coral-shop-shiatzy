package com.dookay.coral.shop.goods.query;


import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.goods.domain.GoodsColorDomain;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 商品颜色的Query
 * @author : luxor
 * @since : 2017年05月06日
 * @version : v0.0.1
 */
@Data
public class GoodsColorQuery extends Query {
	private List<Long> ids;
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(GoodsColorDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		if(valid(ids)){
			criteria.andIn("id",ids);
		}
		//todo 写查询逻辑
		return queryCriteria;
	}

}
