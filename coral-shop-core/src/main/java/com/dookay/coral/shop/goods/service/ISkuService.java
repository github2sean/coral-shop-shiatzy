package com.dookay.coral.shop.goods.service;

import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.shop.goods.domain.SkuDomain;

/**
 * 商品sku的业务层接口
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
public interface ISkuService extends IBaseService<SkuDomain> {

    SkuDomain getSkuByGoodsId(Long id);


}
