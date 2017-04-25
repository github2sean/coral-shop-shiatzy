package com.dookay.coral.common.persistence.criteria;

import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.Example;

import java.util.Map;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2016/11/18
 */
public  class Criteria {

    private Example.Criteria criteria;
    public Criteria(Example.Criteria innerCriteria){
        criteria = innerCriteria;
    }

    public Example.Criteria getCriteria() {
        return criteria;
    }
}
