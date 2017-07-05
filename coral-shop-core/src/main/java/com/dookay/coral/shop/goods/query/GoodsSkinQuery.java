package com.dookay.coral.shop.goods.query;


import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.goods.domain.GoodsSkinDomain;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 商品材质的Query
 * @author : luxor
 * @since : 2017年07月05日
 * @version : v0.0.1
 */
@Data
public class GoodsSkinQuery extends Query {
	private List<Long> ids;
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(GoodsSkinDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		if(valid(ids)){
			criteria.andIn("id",ids);
		}
		//todo 写查询逻辑
		return queryCriteria;
	}

}
