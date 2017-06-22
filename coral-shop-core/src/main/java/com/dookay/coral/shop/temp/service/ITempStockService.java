package com.dookay.coral.shop.temp.service;

import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.shop.temp.domain.TempStockDomain;

/**
 * 临时库存的业务层接口
 * @author : luxor
 * @since : 2017年06月14日
 * @version : v0.0.1
 */
public interface ITempStockService extends IBaseService<TempStockDomain> {


    Integer getStockBySizeAndColor(String goodsNo,Long colorId,Long sizeId);

}
