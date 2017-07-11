package com.dookay.coral.shop.order.service;

import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.shop.order.domain.OrderDomain;
import com.dookay.coral.shop.order.domain.OrderItemDomain;
import com.dookay.coral.shop.order.domain.ReturnRequestItemDomain;

import java.util.List;

/**
 * 商品sku规格值的业务层接口
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
public interface IOrderService extends IBaseService<OrderDomain> {

    void withGoodsItem(List<OrderItemDomain> cartList);

    /**
     * 获取门店信息
     * @param orderDomain
     */
    void withStore(OrderDomain orderDomain);
    void returnWithGoodItem(List<ReturnRequestItemDomain> requestItemDomainList);

    OrderDomain getOrder(String orderNo);

    /**
     * 更新库存
     * @param orderDomain
     */
    void updateSkuStock(OrderDomain orderDomain);

    void updateOrderStatus(OrderDomain orderDomain);

    void subCouponNum(OrderDomain orderDomain);

    List<OrderDomain> getUnpaidOrder();
}
