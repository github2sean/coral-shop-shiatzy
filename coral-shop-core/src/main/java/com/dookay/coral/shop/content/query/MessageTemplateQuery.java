package com.dookay.coral.shop.content.query;

import com.dookay.coral.common.persistence.Query;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.content.domain.MessageTemplateDomain;

/**
 * 消息模板的Query
 * @author : luxor
 * @since : 2017年06月13日
 * @version : v0.0.1
 */
@Data
public class MessageTemplateQuery extends Query {

	private Integer code;
	private Integer type;
	private Integer isValid;
	private String title;


	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(MessageTemplateDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();

		if (valid(code)){
			criteria.andEqualTo("code",code);
		}
		if (valid(type)){
			criteria.andEqualTo("type",type);
		}
		if (valid(isValid)){
			criteria.andEqualTo("isValid",isValid);
		}
		if (valid(title)){
			criteria.andEqualTo("title",title);
		}

		//todo 写查询逻辑
		return queryCriteria;
	}

}
