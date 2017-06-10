package com.dookay.coral.shop.goods.service;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.common.web.validate.FieldMatch;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.query.GoodsQuery;

import java.util.List;

/**
 * 商品的业务层接口
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
public interface IGoodsService extends IBaseService<GoodsDomain> {

    PageList<GoodsDomain> getGoodsList(GoodsQuery query);

    /**
     * 统计分类下商品数量
     * @param categoryId
     * @return
     */
    int countGoodsByCategoryId(Long categoryId);

    void withSpecificationList(GoodsDomain goodsDomain);

    void updateColors(GoodsDomain goodsDomain);

    void updateSizes(GoodsDomain goodsDomain);

    void withGoodsItemList(List<GoodsDomain> goodsDomainList);
    void withGoodsItemList(List<GoodsDomain> goodsDomainList,Integer onSale);
    void withGoodsItemList(GoodsDomain goodsDomain);
    void withGoodsItemListAndQuantity(GoodsDomain goodsDomain, Long sizeId);

    void withSizeDomain(List<GoodsDomain> goodsList);

}
