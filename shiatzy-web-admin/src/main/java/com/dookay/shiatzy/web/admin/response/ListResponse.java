package com.dookay.shiatzy.web.admin.response;

import com.dookay.coral.common.persistence.Query;

import java.util.List;

/**
 * Created by 磊 on 2017/3/3.
 */
public class ListResponse<T> extends SuccessResponse {

    public ListResponse(List t, Query query){
        this.list=t;
        this.query=query;
    }

    private List<T>list;

    private Query query;

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }


    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
