package com.dookay.coral.common.persistence;

import com.dookay.coral.common.persistence.utils.JsonUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2016/11/17
 */
public class QueryResult implements Serializable {
    private List<Map<String, Object>> resultList;

    private Map<String, Object> resultMap;

    private Map<String, Object> page;

    public void setResultList(List<Map<String, Object>> resultList) {
        this.resultList = resultList;
    }

    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    public Map<String, Object> get(){
        if(resultMap != null && resultMap.size() > 0){
            return resultMap;
        }
        if(resultList != null && resultList.size() > 0){
            return resultList.get(0);
        }
        return resultMap;
    }

    public Map<String, Object> get(List<String> hiddenFields){
        Map<String, Object> temp = get();
        if(temp != null){
            hiddenFields.stream().filter(temp::containsKey).forEach(temp::remove);
        }
        return temp;
    }

    public List<Map<String, Object>> getList(){
        return resultList;
    }

    public List<Map<String, Object>> getList(List<String> hiddenFields){
        if(hiddenFields != null && resultList != null){
            for(String field : hiddenFields){
                resultList.stream().filter(map -> map.containsKey(field)).forEach(map -> {
                    map.remove(field);
                });
            }
        }
        return resultList;
    }

    public <T> T as(Class<T> beanClass){
        Map<String, Object> result = get();
        if(result != null){
            return JsonUtils.mapToObj(result, beanClass);
        }
        return null;
    }

    public <T> List<T> asList(Class<T> beanClass){
        if(resultList != null){
            return JsonUtils.mapListToObjList(resultList, beanClass);
        }
        return null;
    }

    public Map<String, Object> getPage() {
        return page;
    }

    public void setPage(Map<String, Object> page) {
        this.page = page;
    }

}

