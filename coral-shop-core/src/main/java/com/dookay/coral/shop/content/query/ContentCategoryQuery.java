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
	private Long parentId;
	private List<Long> ids;
	private Integer level;
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
			criteria.andEqualTo("parentId",parentId);
		}
		if(valid(ids))
		{
			criteria.andIn("id",ids);
		}
		if(valid(level)){
			criteria.andEqualTo("level",level);
		}
		//todo 写查询逻辑
		return queryCriteria;
	}

}
