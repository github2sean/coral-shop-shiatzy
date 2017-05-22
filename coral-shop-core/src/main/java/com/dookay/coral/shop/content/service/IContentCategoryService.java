package com.dookay.coral.shop.content.service;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.shop.content.domain.ContentCategoryDomain;
import com.dookay.coral.shop.content.query.ContentCategoryQuery;

import java.util.List;

/**
 * 内容分类的业务层接口
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
public interface IContentCategoryService extends IBaseService<ContentCategoryDomain> {
    ContentCategoryDomain  getContentCategory(Long id);

    ContentCategoryDomain  getContentCategory(ContentCategoryQuery contentCategoryQuery);

    List<ContentCategoryDomain> getContentCategoryList(ContentCategoryQuery contentCategoryQuery);

    PageList<ContentCategoryDomain> getContentCategoryPageList(ContentCategoryQuery contentCategoryQuery);

    void  registerContentCategory(ContentCategoryDomain contentCategoryDomain);

    ContentCategoryDomain createContentCategory(ContentCategoryDomain contentCategoryDomain);//添加

    void updateContentCategory(ContentCategoryDomain contentCategoryDomain);//修改

    void deleteContentCategory(ContentCategoryDomain contentCategoryDomain);//删除

}
