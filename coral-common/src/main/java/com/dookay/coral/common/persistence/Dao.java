package com.dookay.coral.common.persistence;

import com.dookay.coral.common.persistence.criteria.QueryCriteria;

import java.util.List;

/**
 * 通用数据访问接口
 * @author Luxor
 * @version v0.0.1
 * @since 2016/11/17
 */
public interface Dao<T> {
    /*列表查询*/
    QueryResult selectByCriteria(List<String> fields, QueryCriteria queryCriteria);
    QueryResult selectByCriteria(QueryCriteria queryCriteria);
    QueryResult selectPageByCriteria(List<String> fields, QueryCriteria queryCriteria);
    QueryResult selectPageByCriteria(QueryCriteria queryCriteria);

    /*统计查询*/
    int countByCriteria(QueryCriteria queryCriteria);

    /*主键查询*/
    QueryResult selectByPrimaryKey(Object key);
    QueryResult selectByPrimaryKey(String[] fields, Object key);

    /*插入*/
    int insert(T record);
    int insertSelective(T record);
    void asyncInsert(T record);
    int insertList(List<T> recordList);

    /*更新*/
    int updateByCriteria(T record, QueryCriteria queryCriteria);
    int updateByPrimaryKey(T record);

    /*删除*/
    int deleteByPrimaryKey(T record);
    int deleteByCriteria(QueryCriteria queryCriteria);
}
