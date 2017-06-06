package com.dookay.coral.shop.order.query;

import com.dookay.coral.common.persistence.Query;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;

import java.util.List;

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
	private Long skuId;
	private String goodsName;
	private String goodsCode;
	private Double goodsPrice;
	private String skuSpecifications;
	private Long itemId;
	private List<Long> ids;

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
		if(valid(skuId)){
			criteria.andEqualTo("skuId",skuId);
		}
		if(valid(goodsName)){
			criteria.andEqualTo("goodsName",goodsName);
		}
		if(valid(goodsPrice)){
			criteria.andEqualTo("goodsPrice",goodsPrice);
		}
		if(valid(skuSpecifications)){
			criteria.andEqualTo("goodsName",skuSpecifications);
		}
		if(valid(goodsCode)){
			criteria.andEqualTo("goodsCode",goodsCode);
		}
		if(valid(itemId)){
			criteria.andEqualTo("itemId",itemId);
		}
		if(valid(ids)){
			criteria.andIn("id",ids);
		}
		//todo 写查询逻辑
		return queryCriteria;
	}

}
