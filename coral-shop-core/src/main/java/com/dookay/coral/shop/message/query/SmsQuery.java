package com.dookay.coral.shop.message.query;

import com.dookay.coral.common.persistence.Query;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.message.domain.SmsDomain;

/**
 * 短信的Query
 * @author : luxor
 * @since : 2017年06月13日
 * @version : v0.0.1
 */
public class SmsQuery extends Query {
	
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(SmsDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		
		//todo 写查询逻辑
		return queryCriteria;
	}

}