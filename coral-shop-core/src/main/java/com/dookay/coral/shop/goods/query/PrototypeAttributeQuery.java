package com.dookay.coral.shop.goods.query;

import com.dookay.coral.common.persistence.Query;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.goods.domain.PrototypeAttributeDomain;

/**
 * 原型属性的Query
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
public class PrototypeAttributeQuery extends Query {

	private Long prototypeId;

	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(PrototypeAttributeDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();

		if(valid(prototypeId)){
			criteria.andEqualTo("prototypeId",prototypeId);
		}

		//todo 写查询逻辑
		return queryCriteria;
	}

}
