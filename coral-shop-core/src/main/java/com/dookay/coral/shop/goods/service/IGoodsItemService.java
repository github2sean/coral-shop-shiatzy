package com.dookay.coral.shop.goods.service;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.shop.goods.domain.GoodsItemDomain;

import java.util.List;

/**
 * 商品项目的业务层接口
 * @author : luxor
 * @since : 2017年05月06日
 * @version : v0.0.1
 */
public interface IGoodsItemService extends IBaseService<GoodsItemDomain> {

    void withGoods(PageList<GoodsItemDomain> goodsItemDomainList);
    void withGoods(List<GoodsItemDomain> goodsItemDomainList);
    void withGoods(GoodsItemDomain goodsItemDomain);
    void withColor(GoodsItemDomain goodsItemDomain);
    void withColor(List<GoodsItemDomain> goodsItemDomainList);
}
