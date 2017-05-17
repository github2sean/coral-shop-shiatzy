package com.dookay.coral.shop.goods.query;

import com.dookay.coral.common.persistence.Query;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.goods.domain.GoodsAttributeValueDomain;

import java.util.List;

/**
 * 商品属性值的Query
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
public class GoodsAttributeValueQuery extends Query {
    private Long  id;

    private List<Long> ids;
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(GoodsAttributeValueDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		if(valid(id)){
			criteria.andEqualTo("id",id);
		}
		if(valid(ids)){
			criteria.andIn("id",ids);
		}
		//todo 写查询逻辑
		return queryCriteria;
	}

}
