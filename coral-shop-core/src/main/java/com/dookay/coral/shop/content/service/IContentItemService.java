package com.dookay.coral.shop.content.service;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.shop.content.domain.ContentItemDomain;
import com.dookay.coral.shop.content.query.ContentItemQuery;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * 内容的业务层接口
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
public interface IContentItemService extends IBaseService<ContentItemDomain> {
    ContentItemDomain  getContent(Long id); //根据Id查询多想

    ContentItemDomain  getContent(ContentItemQuery contentItemQuery);//根据条件查询

    List<ContentItemDomain> getContentList(ContentItemQuery contentItemQuery);//List

    PageList<ContentItemDomain> getContentPageList(ContentItemQuery contentItemQuery);//查询所有内容并分页

    void  registerContent(ContentItemDomain contentItemDomain);

    ContentItemDomain createContent(ContentItemDomain contentItemDomain);//添加

    void updateContent(ContentItemDomain contentItemDomain);//修改

    void deleteContent(ContentItemDomain contentItemDomain);//删除

}
