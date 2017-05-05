package com.dookay.coral.shop.goods.query;

import com.dookay.coral.common.persistence.Query;
import lombok.Data;
import org.omg.CORBA.INTERNAL;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.goods.domain.GoodsCategoryDomain;

/**
 * 商品分类的Query
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
public class GoodsCategoryQuery extends Query {
	private Long parentId;

	private Integer isValid;

	private Integer level;
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(GoodsCategoryDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		if(valid(parentId)){
			criteria.andEqualTo("parentId",parentId);
		}
		if(valid(isValid)){
			criteria.andEqualTo("isValid",isValid);
		}
		if(valid(level)){
			criteria.andEqualTo("level",level);
		}
		//todo 写查询逻辑
		return queryCriteria;
	}

}
