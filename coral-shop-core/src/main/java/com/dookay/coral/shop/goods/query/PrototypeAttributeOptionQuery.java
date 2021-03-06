package com.dookay.coral.shop.goods.query;

import com.dookay.coral.common.persistence.Query;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.goods.domain.PrototypeAttributeOptionDomain;

import java.util.List;

/**
 * 原型属性选项的Query
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
public class PrototypeAttributeOptionQuery extends Query {

	private Long prototypeAttributeId;

	private List<Long> prototypeAttributeIds;

	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(PrototypeAttributeOptionDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();

		if(valid(prototypeAttributeId)){
			criteria.andEqualTo(prototypeAttributeId);
		}

		if (valid(prototypeAttributeIds)){
			criteria.andIn("prototypeAttributeId",prototypeAttributeIds);
		}

		//todo 写查询逻辑
		return queryCriteria;
	}

}
