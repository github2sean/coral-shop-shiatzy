package com.dookay.coral.shop.goods.service;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.shop.goods.domain.GoodsCategoryDomain;
import com.dookay.coral.shop.goods.query.GoodsCategoryQuery;

import java.util.List;

/**
 * 商品分类的业务层接口
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
public interface IGoodsCategoryService extends IBaseService<GoodsCategoryDomain> {

    void deleteCategory(GoodsCategoryDomain goodsCategoryDomain);

    PageList<GoodsCategoryDomain> pageListCategory(GoodsCategoryQuery goodsCategoryQuery);

    List<GoodsCategoryDomain> listCategory(GoodsCategoryQuery goodsCategoryQuery);

    /**
     * 根据父id列出商品分类
     * @param parentId
     * @return
     */
    List<GoodsCategoryDomain> listCategoryByParentId(Long parentId,Boolean isBack);

    GoodsCategoryDomain getCategory(Long categoryId);

}
