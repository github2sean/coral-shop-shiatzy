package com.dookay.coral.shop.order.service;

import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.shop.order.domain.ReturnRequestItemDomain;

import java.util.List;

/**
 * 订单退货申请明细的业务层接口
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
public interface IReturnRequestItemService extends IBaseService<ReturnRequestItemDomain> {

    void withGoodsItem(List<ReturnRequestItemDomain> returnOrderItemList);

}
