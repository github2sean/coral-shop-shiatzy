package com.dookay.coral.shop.goods.query;

import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.criteria.Criteria;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.goods.domain.GoodsItemFormatDomain;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;

/**
 * 商品项目参数的Query
 * @author : luxor
 * @since : 2017年07月06日
 * @version : v0.0.1
 */
@Data
public class GoodsItemFormatQuery extends Query {

	private Long itemId;
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(GoodsItemFormatDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
if(valid(itemId)){
	criteria.andEqualTo("itemId",itemId);
}
		//todo 写查询逻辑
		return queryCriteria;
	}

}
