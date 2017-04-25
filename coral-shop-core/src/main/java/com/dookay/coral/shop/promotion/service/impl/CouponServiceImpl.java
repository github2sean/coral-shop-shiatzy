package com.dookay.coral.shop.promotion.service.impl;

import com.dookay.coral.shop.promotion.query.CouponQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.promotion.mapper.CouponMapper;
import com.dookay.coral.shop.promotion.domain.CouponDomain;
import com.dookay.coral.shop.promotion.service.ICouponService;

/**
 * 优惠券的业务实现类
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("couponService")
public class CouponServiceImpl extends BaseServiceImpl<CouponDomain> implements ICouponService {
	
	@Autowired
	private CouponMapper couponMapper;

}
