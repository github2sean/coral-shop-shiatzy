package com.dookay.shiatzy.web.admin.response;

import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.pager.PageList;

/**
 * Created by 磊 on 2017/3/3.
 */
public class PageListResponse<T> extends SuccessResponse {

    public PageListResponse(PageList t, Query query){
        this.pageList=t;
        this.query=query;
    }

    private PageList<T> pageList;

    private Query query;

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public PageList<T> getPageList() {
        return pageList;
    }

    public void setPageList(PageList<T> pageList) {
        this.pageList = pageList;
    }
}
