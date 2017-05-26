package com.dookay.coral.shop.content.query;

import com.dookay.coral.common.persistence.Query;

import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.content.domain.ContentItemDomain;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * 内容的Query
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@Data
public class ContentItemQuery extends Query {
	private Long contentId;

	private Date createTime;

	private Long categoryId;

	private List<Long> creatorId;

	private String userTitle;
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(ContentItemDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		if(valid(createTime)){
			criteria.andEqualTo("createTime",createTime);
		}
		if(valid(contentId)){
			criteria.andEqualTo("id",contentId);
		}
        if(valid(categoryId))
		{
			criteria.andEqualTo("categoryId",categoryId);
		}
		if(valid(creatorId))
		{
			criteria.andIn("creatorId",creatorId);
		}
		if(valid(userTitle))
		{
			criteria.andLike("title",userTitle);
		}
		//todo 写查询逻辑
		return queryCriteria;
	}

}
