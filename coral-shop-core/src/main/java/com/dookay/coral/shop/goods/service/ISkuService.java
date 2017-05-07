package com.dookay.coral.shop.goods.service;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.domain.GoodsItemDomain;
import com.dookay.coral.shop.goods.domain.SkuDomain;
import com.dookay.coral.shop.goods.query.GoodsQuery;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 商品sku的业务层接口
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
public interface ISkuService extends IBaseService<SkuDomain> {

    List<SkuDomain> getSkuByGoodsId(Long id);
    void withGoodsItem(PageList<SkuDomain> goodsItemDomainList);
    void withGoodsItem(List<SkuDomain> goodsItemDomainList);
    void withGoodsItem(SkuDomain skuDomain);
}
