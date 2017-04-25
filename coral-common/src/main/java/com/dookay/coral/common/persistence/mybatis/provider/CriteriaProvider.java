package com.dookay.coral.common.persistence.mybatis.provider;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;
import tk.mybatis.mapper.provider.ExampleProvider;

public class CriteriaProvider extends MapperTemplate {

    private ExampleProvider exampleProvider;

    public CriteriaProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
        exampleProvider = new ExampleProvider(mapperClass, mapperHelper);
    }

    public String selectByCriteria(MappedStatement ms) {
        return exampleProvider.selectByExample(ms);
    }

    public String selectSliceByCriteria(MappedStatement ms){
        Class<?> entityClass = getEntityClass(ms);
        //将返回值修改为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append("<if test=\"distinct\">distinct</if>");
        //支持查询指定列
        sql.append(SqlHelper.exampleSelectColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.exampleWhereClause());
        sql.append(SqlHelper.exampleOrderBy(entityClass));
        sql.append(limit());
        return sql.toString();
    }

    private String limit(){
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"limit != null\">");
        sql.append("limit ${offset},${limit}");
        sql.append("</if>");
        return sql.toString();
    }
}
