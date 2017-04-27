package com.dookay.coral.shop.goods.query;

import com.dookay.coral.common.persistence.Query;

import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;

/**
 * 商品的Query
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@Data
public class GoodsQuery extends Query {

	private Integer pageIndex = 1;//当前页码
	private Integer pageSize = 20; //页面大小，默认20
	private Integer offset = 0; // 行偏移
	private Integer limit;    //获取最大数量
	private String orderBy;// 排序字段

	private  String name;
	private  Long categoryId;

	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(GoodsDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();


		if(valid(limit)){
			queryCriteria.setOffset(pageIndex);
			queryCriteria.setPageSize(pageSize);
			queryCriteria.setLimit(limit);
			queryCriteria.setOffset(offset);
		}

		if (valid(name)){
			criteria.andLike("name","%"+name+"%");
		}

		if (valid(categoryId)){
			criteria.andEqualTo("categoryId",categoryId);
		}

		//todo 写查询逻辑
		return queryCriteria;
	}

}
