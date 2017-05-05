package com.dookay.coral.shop.goods.query;

import com.dookay.coral.common.persistence.Query;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.goods.domain.GoodsPrototypeDomain;

/**
 * 商品原型的Query
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
public class GoodsPrototypeQuery extends Query {

	private Long goodsId;
	private String name;
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(GoodsPrototypeDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		if(valid(name)){
			String nameLike = "%"+name+"%";
			criteria.andLike("name",nameLike);
		}
		//todo 写查询逻辑
		return queryCriteria;
	}

}
