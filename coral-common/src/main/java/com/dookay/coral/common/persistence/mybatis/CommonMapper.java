package com.dookay.coral.common.persistence.mybatis;

import com.dookay.coral.common.persistence.mybatis.mapper.criteria.SelectByCriteriaMapper;
import com.dookay.coral.common.persistence.mybatis.mapper.criteria.SelectSliceByCriteriaMapper;
import tk.mybatis.mapper.common.*;
import tk.mybatis.mapper.common.special.InsertListMapper;


/**
 * mybatis通用Mapper接口
 * @since : 2016年11月9日
 * @author : luxor
 * @version : v0.0.1
 */
public interface CommonMapper<T> extends
		BaseMapper<T>,
		ConditionMapper<T>,
		IdsMapper<T>,
		InsertListMapper<T>,
		RowBoundsMapper<T>,
		SelectByCriteriaMapper<T>,
		SelectSliceByCriteriaMapper<T>,
		Marker
{

}
