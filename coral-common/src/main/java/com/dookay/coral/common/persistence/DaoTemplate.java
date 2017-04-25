package com.dookay.coral.common.persistence;

import com.dookay.coral.common.persistence.criteria.QueryCriteria;

import java.util.List;
import java.util.Map;

/**
 * 通用Dao模板类，实现了数据层核心逻辑
 * @author Luxor
 * @version v0.0.1
 * @since 2016/11/18
 */
public abstract class DaoTemplate<T> implements Dao<T>{

    protected abstract List<Map<String, Object>> _selectByCriteria(QueryCriteria criteria);
    protected abstract int _countByCriteria(QueryCriteria criteria);
    protected abstract Map<String, Object> _selectByPrimaryKey(Object key);
    protected abstract long _insert(T record);
    protected abstract int _updateByCriteria(T record, QueryCriteria criteria);
    protected abstract int _updateByPrimaryKey(T record);
    protected abstract int _deleteByPrimaryKey(T record);
    protected abstract int _deleteByCriteria(QueryCriteria criteria);

    @Override
    public QueryResult selectByCriteria(List<String> fields, QueryCriteria queryCriteria) {
        queryCriteria.selectProperties(fields.toArray(new String[0]));
        List<Map<String,Object>> value =  _selectByCriteria(queryCriteria);
        QueryResult queryResult = new QueryResult();
        queryResult.setResultList(value);
        return queryResult;
    }

    @Override
    public QueryResult selectByCriteria(QueryCriteria queryCriteria) {
        return null;

    }

    @Override
    public QueryResult selectPageByCriteria(List<String> fields, QueryCriteria queryCriteria) {
        return null;
    }

    @Override
    public QueryResult selectPageByCriteria(QueryCriteria queryCriteria) {
        return null;
    }

    @Override
    public int countByCriteria(QueryCriteria queryCriteria) {
        return 0;
    }

    @Override
    public QueryResult selectByPrimaryKey(Object key) {
        return null;
    }

    @Override
    public QueryResult selectByPrimaryKey(String[] fields, Object key) {
        return null;
    }

    @Override
    public int insert(T record) {
        return 0;
    }

    @Override
    public int insertSelective(T record) {
        return 0;
    }

    @Override
    public void asyncInsert(T record) {

    }

    @Override
    public int insertList(List<T> recordList) {
        return 0;
    }

    @Override
    public int updateByCriteria(T record, QueryCriteria queryCriteria) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(T record) {
        return 0;
    }

    @Override
    public int deleteByPrimaryKey(T record) {
        return 0;
    }

    @Override
    public int deleteByCriteria(QueryCriteria queryCriteria) {
        return 0;
    }
}
