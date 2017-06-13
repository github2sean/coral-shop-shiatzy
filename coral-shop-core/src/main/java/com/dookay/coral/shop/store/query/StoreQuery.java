package com.dookay.coral.shop.store.query;

import com.dookay.coral.common.persistence.Query;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.store.domain.StoreDomain;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;

/**
 * 店铺的Query
 * @author : luxor
 * @since : 2017年05月03日
 * @version : v0.0.1
 */
@Data
public class StoreQuery extends Query {

	private String countryId;
	private String cityId;
	private String name;

	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(StoreDomain.class);
		Example.Criteria criteria =queryCriteria.createCriteria();

		if (valid(cityId)){
			criteria.andEqualTo("cityId",cityId);
		}
		if (valid(countryId)){
			criteria.andEqualTo("countryId",countryId);
		}
		if (valid(name)){
			criteria.andLike("name","%"+name+"%");
		}

		//todo 写查询逻辑
		return queryCriteria;
	}

}
