package com.dookay.coral.shop.goods.service.impl;

import com.dookay.coral.common.enums.ValidEnum;
import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.shop.goods.enums.GoodsCategoryLevel;
import com.dookay.coral.shop.goods.query.GoodsCategoryQuery;
import com.dookay.coral.shop.goods.service.IGoodsService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.goods.mapper.GoodsCategoryMapper;
import com.dookay.coral.shop.goods.domain.GoodsCategoryDomain;
import com.dookay.coral.shop.goods.service.IGoodsCategoryService;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类的业务实现类
 *
 * @author : luxor
 * @version : v0.0.1
 * @since : 2017年04月27日
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("goodsCategoryService")
public class GoodsCategoryServiceImpl extends BaseServiceImpl<GoodsCategoryDomain> implements IGoodsCategoryService {

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Autowired
    private IGoodsService goodsService;

    @Override
    public void deleteCategory(GoodsCategoryDomain goodsCategoryDomain) {
        //如果不是叶子分类，判断是否有子分类
        if (goodsCategoryDomain.getLevel() != GoodsCategoryLevel.LEVEL2.getValue()) {
            if (goodsCategoryDomain.getChildren() != null && goodsCategoryDomain.getChildren().size() > 0) {
                throw new ServiceException("请先删除所属子分类");
            }
        }
        //如果是叶子分类，判断是否有所属产品
        else {
            int count = goodsService.countGoodsByCategoryId(goodsCategoryDomain.getId());
            if (count > 0) {
                throw new ServiceException("请先删除所属商品");
            }
        }
        super.delete(goodsCategoryDomain.getId());
    }

    @Override
    public PageList<GoodsCategoryDomain> pageListCategory(GoodsCategoryQuery goodsCategoryQuery) {
        return null;
    }

    @Override
    public List<GoodsCategoryDomain> listCategory(GoodsCategoryQuery goodsCategoryQuery) {
        List<GoodsCategoryDomain> goodsCategoryDomainList = super.getList(goodsCategoryQuery);
        Boolean isBack = true;
        if (goodsCategoryQuery.getIsValid() != null && goodsCategoryQuery.getIsValid() == ValidEnum.YES.getValue()) {
            isBack = false;
        }
        for (GoodsCategoryDomain goodsCategoryDomain : goodsCategoryDomainList) {
            List<GoodsCategoryDomain> childrenCategoryList = this.listCategoryByParentId(goodsCategoryDomain.getId(), isBack);
            goodsCategoryDomain.setChildren(childrenCategoryList);
            if (goodsCategoryDomain.getParentId() != null && goodsCategoryDomain.getParentId() > 0) {
                GoodsCategoryDomain parent = this.get(goodsCategoryDomain.getParentId());
                goodsCategoryDomain.setParent(parent);
            }
        }
        return goodsCategoryDomainList;
    }

    @Override
    public List<GoodsCategoryDomain> listCategoryByParentId(Long parentId, Boolean isBack) {
        GoodsCategoryQuery goodsCategoryQuery = new GoodsCategoryQuery();
        goodsCategoryQuery.setOrderBy("rank");
        goodsCategoryQuery.setDesc(false);
        goodsCategoryQuery.setParentId(parentId);
        if (!isBack) {
            goodsCategoryQuery.setIsValid(ValidEnum.YES.getValue());
        }
        return super.getList(goodsCategoryQuery);
    }

    @Override
    public GoodsCategoryDomain getCategory(Long categoryId) {
        GoodsCategoryDomain goodsCategoryDomain = super.get(categoryId);
        List<GoodsCategoryDomain> childrenCategoryList = this.listCategoryByParentId(goodsCategoryDomain.getId(), false);
        goodsCategoryDomain.setChildren(childrenCategoryList);
        return goodsCategoryDomain;
    }
}
