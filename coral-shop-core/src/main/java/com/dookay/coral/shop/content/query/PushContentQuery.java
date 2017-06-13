package com.dookay.coral.shop.content.query;

import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.content.domain.ContentItemDomain;
import com.dookay.coral.shop.content.domain.PushContentDomain;
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
public class PushContentQuery extends Query {

	private Integer isValid;

	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(PushContentDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();

		if(valid(isValid)){
			criteria.andEqualTo("isValid",isValid);
		}

		//todo 写查询逻辑
		return queryCriteria;
	}

}
