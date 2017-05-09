package com.dookay.coral.shop.store.query;

import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.criteria.Criteria;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.store.domain.StoreCountryDomain;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;

/**
 * 店铺国家的Query
 * @author : luxor
 * @since : 2017年05月03日
 * @version : v0.0.1
 */
@Data
public class StoreCountryQuery extends Query {
	private String name;
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(StoreCountryDomain.class);
		Example.Criteria criteria =queryCriteria.createCriteria();


		if (valid(name)){
			criteria.andLike("name","%"+name+"%");
		}
		//todo 写查询逻辑
		return queryCriteria;
	}

}
