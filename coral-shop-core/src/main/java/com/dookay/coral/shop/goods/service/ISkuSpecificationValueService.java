package com.dookay.coral.shop.goods.service;

import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.shop.goods.domain.SkuSpecificationValueDomain;

import java.util.List;

/**
 * 商品sku规格值的业务层接口
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
public interface ISkuSpecificationValueService extends IBaseService<SkuSpecificationValueDomain> {

    List<SkuSpecificationValueDomain>  listSkuSpecificationValueBySkuId(Long skuId);
}
