package com.dookay.coral.shop.goods.extension;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.shop.goods.domain.GoodsCategoryDomain;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.domain.SkuDomain;
import com.dookay.coral.shop.goods.query.GoodsCategoryQuery;
import com.dookay.coral.shop.goods.query.GoodsQuery;
import com.dookay.coral.shop.goods.service.IGoodsCategoryService;
import com.dookay.coral.shop.goods.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/5
 */
@Component
public class GoodsExtension{

    @Autowired
    private IGoodsService goodsService;

    public void withGoods(PageList<SkuDomain> skuDomainList){
        this.withGoods(skuDomainList.getList());
    }

    public void withGoods(List<SkuDomain> skuDomainList){
        List<Long> ids = skuDomainList.stream().map(SkuDomain::getGoodsId).collect(Collectors.toList());
        GoodsQuery query = new GoodsQuery();
        query.setIds(ids);
        List<GoodsDomain> goodsDomainList = goodsService.getList(query);
        for (SkuDomain skuDomain:skuDomainList){
            GoodsDomain goodsDomain = goodsDomainList.stream()
                    .filter(x-> Objects.equals(x.getId(), skuDomain.getGoodsId())).findFirst().orElse(null);
            skuDomain.setGoods(goodsDomain);
        }
    }

    public void  withGoods(SkuDomain skuDomain){
        GoodsDomain goodsDomain = goodsService.get(skuDomain.getGoodsId());
        skuDomain.setGoods(goodsDomain);
    }

}
