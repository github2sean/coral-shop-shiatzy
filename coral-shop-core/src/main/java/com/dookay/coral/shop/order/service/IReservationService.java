package com.dookay.coral.shop.order.service;

import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.order.domain.ReservationDomain;
import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;
import com.dookay.coral.shop.store.domain.StoreDomain;

import java.util.List;

/**
 * 预约单的业务层接口
 * @author : luxor
 * @since : 2017年05月03日
 * @version : v0.0.1
 */
public interface IReservationService extends IBaseService<ReservationDomain> {

    Long submit(List<ShoppingCartItemDomain> cartList, CustomerDomain customer,StoreDomain store);
}
