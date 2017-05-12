package com.dookay.coral.shop.order.service;

import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.shop.order.domain.OrderDomain;
import com.dookay.coral.shop.order.domain.OrderItemDomain;

import java.util.List;

/**
 * 商品sku规格值的业务层接口
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
public interface IOrderService extends IBaseService<OrderDomain> {

    void withGoodItme(List<OrderItemDomain> cartList);

}
