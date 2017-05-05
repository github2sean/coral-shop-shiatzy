package com.dookay.coral.shop.store.query;

import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.criteria.Criteria;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.store.domain.StoreCityDomain;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;

/**
 * 店铺城市的Query
 * @author : luxor
 * @since : 2017年05月03日
 * @version : v0.0.1
 */
@Data
public class StoreCityQuery extends Query {

	private String countryId;

	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(StoreCityDomain.class);
		Example.Criteria criteria =queryCriteria.createCriteria();

		if(valid(countryId)){
			criteria.andEqualTo("countryId",countryId);
		}

		//todo 写查询逻辑
		return queryCriteria;
	}

}
