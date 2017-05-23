package com.dookay.coral.shop.content.service;

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


    List<ContentCategoryDomain> listCategory(ContentCategoryQuery contentCategoryQuery);
    /**
     * 根据父id列出商品分类
     * @param parentId
     * @return
     */
    List<ContentCategoryDomain> listCategoryByParentId(Long parentId);

    ContentCategoryDomain getCategory(Long categoryId);
}
