package com.dookay.coral.shop.goods.query;

import com.dookay.coral.common.persistence.Query;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.goods.domain.PrototypeSpecificationOptionDomain;

import java.util.List;

/**
 * 原型规格选项的Query
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
public class PrototypeSpecificationOptionQuery extends Query {
	private List<Long> ids;
	private Long specificationId;
	private String name;
	private List<String> names;
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(PrototypeSpecificationOptionDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();

		if(valid(specificationId)){
			criteria.andEqualTo("specificationId",specificationId);
		}
		if(valid(ids)){
			criteria.andIn("id",ids);
		}
		if(valid(names)){
			criteria.andIn("name",names);
		}
		if(valid(name))
		{
			criteria.andEqualTo("name",name);
		}
		//todo 写查询逻辑
		return queryCriteria;
	}

}
