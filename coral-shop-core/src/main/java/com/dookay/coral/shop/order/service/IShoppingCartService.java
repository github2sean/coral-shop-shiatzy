package com.dookay.coral.shop.order.service;

import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.goods.domain.SkuDomain;
import com.dookay.coral.shop.order.domain.OrderDomain;
import com.dookay.coral.shop.order.domain.ReservationItemDomain;
import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;

import java.util.List;

/**
 * 购物车的业务层接口
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
public interface IShoppingCartService extends IBaseService<ShoppingCartItemDomain> {

    void removeFromCart(Long id);

    /**
     * 加入购物车/心愿单
     * 限制最多8个商品
     * @param customerDomain
     * @param skuDomain
     * @param shoppingCartType
     * @param num
     */
    void addToCart(CustomerDomain customerDomain, SkuDomain skuDomain, Integer shoppingCartType, int num);

    void updateShoppingCartItem(CustomerDomain customerDomain, Long shoppingCartItemId, int num);

    List<ShoppingCartItemDomain> listShoppingCartItemByCustomerId(Long customerId, Integer shoppingCartType);

    Boolean removeFromWish(CustomerDomain customerDomain, SkuDomain skuDomain);

    ShoppingCartItemDomain isExistInWish(CustomerDomain customerDomain, SkuDomain skuDomain);

    ShoppingCartItemDomain isExistInCart(CustomerDomain customerDomain, SkuDomain skuDomain,Integer cartType);

    OrderDomain createOrder(CustomerDomain customerDomain, List<ShoppingCartItemDomain> cartList);

    void wishToCart(CustomerDomain customerDomain, Long shoppingCartItemId);

    void wishToBoutique(CustomerDomain customerDomain, Long shoppingCartItemId);

    void withGoodsItem(List<ShoppingCartItemDomain> shoppingCartItemDomainList);
    void withReservationItem(List<ReservationItemDomain> reservationItemDomainList);

    SkuDomain getSkubySizeAndItem(Long itemId ,Long sizeId);
    void withSku(List<ShoppingCartItemDomain> shoppingCartItemDomainList);

    void withSizeDomain(List<ShoppingCartItemDomain> cartList);

}
