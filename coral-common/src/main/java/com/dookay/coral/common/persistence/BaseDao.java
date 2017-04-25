package com.dookay.coral.common.persistence;

import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.common.persistence.mybatis.CommonMapper;
import com.dookay.coral.common.persistence.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2016/11/17
 */

public  class BaseDao<T> extends DaoTemplate<T> implements Dao<T> {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private Mapper<T> commonMapper;

    @Override
    protected List<Map<String, Object>> _selectByCriteria(QueryCriteria criteria) {
        return commonMapper.selectByCriteria(criteria);
    }

    @Override
    protected int _countByCriteria(QueryCriteria criteria) {
        return commonMapper.selectCountByCondition(criteria);
    }

    @Override
    protected Map<String, Object> _selectByPrimaryKey(Object key) {
        T entity = commonMapper.selectByPrimaryKey(key);
        return JsonUtils.objToMap(entity);
    }

    @Override
    protected long _insert(T record) {
        return commonMapper.insert(record);
    }

    @Override
    protected int _updateByCriteria(T record, QueryCriteria criteria) {
        return commonMapper.updateByCondition(record,criteria);
    }

    @Override
    protected int _updateByPrimaryKey(T record) {
        return commonMapper.updateByPrimaryKey(record);
    }

    @Override
    protected int _deleteByPrimaryKey(T record) {
        return commonMapper.deleteByPrimaryKey(record);
    }

    @Override
    protected int _deleteByCriteria(QueryCriteria criteria) {
        return commonMapper.deleteByCondition(criteria);
    }

}
