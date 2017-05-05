package com.dookay.coral.shop.promotion.service;

import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.shop.promotion.domain.CouponDomain;

/**
 * 优惠券的业务层接口
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
public interface ICouponService extends IBaseService<CouponDomain> {

   CouponDomain checkCoupon(String couponCode);

}
