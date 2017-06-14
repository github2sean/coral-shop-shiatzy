package com.dookay.coral.shop.index.query;

import com.dookay.coral.common.persistence.Query;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.index.domain.IndexBlockDomain;

/**
 * 首页区块的Query
 * @author : luxor
 * @since : 2017年06月13日
 * @version : v0.0.1
 */
@Data
public class IndexBlockQuery extends Query {

	private Long groupId;
	private Integer isValid;
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(IndexBlockDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();

		if(valid(groupId)){
			criteria.andEqualTo("groupId",groupId);
		}
		if(valid(isValid)){
			criteria.andEqualTo("isValid",isValid);
		}

		//todo 写查询逻辑
		return queryCriteria;
	}

}
