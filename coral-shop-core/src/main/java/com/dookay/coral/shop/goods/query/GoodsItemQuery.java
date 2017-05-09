package com.dookay.coral.shop.goods.query;

import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.shop.goods.domain.GoodsItemDomain;

import java.util.List;

/**
 * 商品项目的Query
 * @author : luxor
 * @since : 2017年05月06日
 * @version : v0.0.1
 */
@Data
public class GoodsItemQuery extends Query {
	private Long goodsId;
	private List<Long> goodsIds;
	private Integer isValid;
	private List<Long> ids;
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(GoodsItemDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		if(valid(goodsId)){
			criteria.andEqualTo("goodsId" ,goodsId);
		}
		if(valid(isValid)){
			criteria.andEqualTo("isValid",isValid);

		}
		if(valid(ids)){
			criteria.andIn("id",ids);
		}
		if(valid(goodsIds)){
			criteria.andIn("goodsId",goodsIds);
		}
		//todo 写查询逻辑
		return queryCriteria;
	}

}
