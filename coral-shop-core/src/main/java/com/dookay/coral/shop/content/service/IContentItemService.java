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
    void withContent(PageList<ContentItemDomain> contentItemDomainList);
    void withContent(List<ContentItemDomain> contentItemDomainList);
    void withContent(ContentItemDomain contentItemDomain);
    /**
     * 统计分类下商品数量
     * @param categoryId
     * @return
     */
    int countContentByCategoryId(Long categoryId);
}
