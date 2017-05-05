package com.dookay.coral.shop.goods.extension;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.shop.goods.domain.GoodsCategoryDomain;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.query.GoodsCategoryQuery;
import com.dookay.coral.shop.goods.service.IGoodsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/5
 */
@Component
public class GoodsCategoryExtension {
    @Autowired
    private IGoodsCategoryService goodsCategoryService;

    public void withGoodsCategory(PageList<GoodsDomain> goodsDomainPageList){
        List<GoodsDomain> goodsDomainList = goodsDomainPageList.getList();
        this.withGoodsCategory(goodsDomainList);
    }

    public void withGoodsCategory(List<GoodsDomain> goodsDomainList){
        List<Long> categoryIds = goodsDomainList.stream().map(GoodsDomain::getCategoryId).collect(Collectors.toList());
        GoodsCategoryQuery goodsCategoryQuery = new GoodsCategoryQuery();
        goodsCategoryQuery.setIds(categoryIds);
        List<GoodsCategoryDomain> goodsCategoryDomainList = goodsCategoryService.getList(goodsCategoryQuery);
        for (GoodsDomain goodsDomain:goodsDomainList){
            GoodsCategoryDomain goodsCategoryDomain = goodsCategoryDomainList.stream()
                    .filter(x-> Objects.equals(x.getId(), goodsDomain.getCategoryId())).findFirst().orElse(null);
            goodsDomain.setGoodsCategory(goodsCategoryDomain);
        }
    }

    public void withGoodsCategory(GoodsDomain goodsDomain){
        GoodsCategoryDomain goodsCategoryDomain = goodsCategoryService.getCategory(goodsDomain.getCategoryId());
        goodsDomain.setGoodsCategory(goodsCategoryDomain);
    }
}
