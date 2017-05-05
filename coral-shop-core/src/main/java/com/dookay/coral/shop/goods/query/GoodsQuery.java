package com.dookay.coral.shop.goods.query;

import com.dookay.coral.common.persistence.Query;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 商品的Query
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Data
public class GoodsQuery extends Query {

	private  String name;
	private  Long categoryId;
	private  Long prototypeId;

	private List<Long> ids;
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(GoodsDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();

		if (valid(name)){
			criteria.andLike("name","%"+name+"%");
		}

		if (valid(categoryId)){
			criteria.andEqualTo("categoryId",categoryId);
		}

		if (valid(prototypeId)){
			criteria.andEqualTo("prototypeId",prototypeId);
		}
		if(valid(ids)){
			criteria.andIn("id",ids);
		}

		return queryCriteria;
	}

}
