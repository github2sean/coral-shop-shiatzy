package com.dookay.coral.shop.content.query;

import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.content.domain.SubscribeDomain;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;

/**
 * 订阅的Query
 * @author : luxor
 * @since : 2017年06月14日
 * @version : v0.0.1
 */
@Data
public class SubscribeQuery extends Query {
	private String email;
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(SubscribeDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		if(valid(email)){
			criteria.andEqualTo("email",email);
		}
		//todo 写查询逻辑
		return queryCriteria;
	}

}
