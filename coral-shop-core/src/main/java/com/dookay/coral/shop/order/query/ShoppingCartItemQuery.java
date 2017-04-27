package com.dookay.coral.shop.order.query;

import com.dookay.coral.common.persistence.Query;
import lombok.Data;
import org.omg.CORBA.INTERNAL;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;

/**
 * 购物车的Query
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
public class ShoppingCartItemQuery extends Query {
	private Integer shoppingCartType;
	private Long customerId;
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(ShoppingCartItemDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		if(valid(customerId)){
			criteria.andEqualTo("customerId",customerId);
		}
		if(valid(shoppingCartType)){
			criteria.andEqualTo("shoppingCartType",shoppingCartType);
		}
		//todo 写查询逻辑
		return queryCriteria;
	}

}
