package com.dookay.coral.shop.goods.query;

import com.dookay.coral.common.persistence.Query;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.goods.domain.SkuDomain;

/**
 * 商品sku的Query
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
public class SkuQuery extends Query {

	private Long goodsId;
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(SkuDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();

		if (valid(goodsId)){
			criteria.andEqualTo("goodsId",goodsId);
		}
		//todo 写查询逻辑
		return queryCriteria;
	}

}
