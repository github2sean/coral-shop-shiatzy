package com.dookay.coral.shop.customer.query;

import com.dookay.coral.common.persistence.Query;

import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.customer.domain.CustomerAddressDomain;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 客户地址的Query
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@Data
public class CustomerAddressQuery extends Query {

	private Long customerId;

	private List<Long> customerIds;

	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(CustomerAddressDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();


		if(valid(customerId)){
			criteria.andEqualTo("customerId",customerId);
		}

		if(valid(customerIds)){
			criteria.andIn("customerId",customerIds);
		}

		//todo 写查询逻辑
		return queryCriteria;
	}

}
