package com.dookay.coral.shop.content.query;

import com.dookay.coral.common.persistence.Query;

import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.content.domain.ContentCategoryDomain;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 内容分类的Query
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@Data
public class ContentCategoryQuery extends Query {
	private Long id;
	private String  title;
	private List<Long> parentId;
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(ContentCategoryDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		if(valid(id))
		{
			criteria.andEqualTo("id",id);
		}
		if(valid(title))
		{
			criteria.andLike("title",title);

		}
		if(valid(parentId))
		{
			criteria.andIn("parentId",parentId);
		}
		//todo 写查询逻辑
		return queryCriteria;
	}

}
