package com.dookay.coral.shop.customer.query;

import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.domain.TempMemberDomain;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;

/**
 * 客户的Query
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@Data
public class TempMemberQuery extends Query {

	private String mobile;

	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(TempMemberDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();

		if(valid(mobile)){
			criteria.andEqualTo("mobile",mobile);
		}

		//todo 写查询逻辑
		return queryCriteria;
	}

}
