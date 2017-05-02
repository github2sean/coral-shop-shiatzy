package com.dookay.coral.common.persistence.mybatis.mapper.criteria;

import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.SelectProvider;
import com.dookay.coral.common.persistence.mybatis.provider.CriteriaProvider;
import java.util.List;
import java.util.Map;

/**
 * 通用Mapper接口,Condition查询
 *
 * @param <T> 不能为空
 * @author liuzh
 */
public interface SelectByCriteriaMapper<T> {

    /**
     * 根据Condition条件进行查询
     *
     * @param condition
     * @return
     */
    @SelectProvider(type = CriteriaProvider.class, method = "dynamicSQL")
    @ResultType(value = List.class)
    List<Map<String, Object>> selectByCriteria(Object condition);


}