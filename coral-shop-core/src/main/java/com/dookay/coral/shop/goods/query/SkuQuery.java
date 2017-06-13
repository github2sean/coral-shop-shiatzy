package com.dookay.coral.shop.goods.query;

import com.dookay.coral.common.persistence.Query;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.goods.domain.SkuDomain;

import java.util.List;

/**
 * 商品sku的Query
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
public class SkuQuery extends Query {

	private Long goodsId;
	private Integer isValid;
	private Long itemId;

	private List<Long> goodsIds;
	private List<Long> itemIds;
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(SkuDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();

		if (valid(goodsId)){
			criteria.andEqualTo("goodsId",goodsId);
		}
		if (valid(itemId)){
			criteria.andEqualTo("itemId",itemId);
		}
		if (valid(itemId)){
			criteria.andIn("itemId",itemIds);
		}
		if(valid(isValid)){
			criteria.andEqualTo("isValid",isValid);
		}
		if (valid(goodsId)){
			criteria.andIn("goodsId",goodsIds);
		}
		//todo 写查询逻辑
		return queryCriteria;
	}

}
