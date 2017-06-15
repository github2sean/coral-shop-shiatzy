package com.dookay.coral.shop.temp.query;


import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.shop.temp.domain.TempStockDomain;
import lombok.Data;
import tk.mybatis.mapper.entity.Example;

/**
 * 临时库存的Query
 * @author : luxor
 * @since : 2017年06月14日
 * @version : v0.0.1
 */
@Data
public class TempStockQuery extends Query {

	private String productNo;
	private String 	color;
	private String size;
	
	@Override
	public QueryCriteria toCriteria() {
		QueryCriteria queryCriteria = new QueryCriteria(TempStockDomain.class);
		Example.Criteria criteria = queryCriteria.createCriteria();
		if(valid(productNo)){
			criteria.andEqualTo("productNo",productNo);
		}
		if(valid(color)){
			criteria.andEqualTo("color",color);
		}
		if(valid(size)){
			criteria.andEqualTo("size",size);
		}

		//todo 写查询逻辑
		return queryCriteria;
	}

}
