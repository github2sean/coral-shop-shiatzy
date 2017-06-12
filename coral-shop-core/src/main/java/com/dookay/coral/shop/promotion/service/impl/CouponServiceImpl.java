package com.dookay.coral.shop.promotion.service.impl;

import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.shop.promotion.query.CouponQuery;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.promotion.mapper.CouponMapper;
import com.dookay.coral.shop.promotion.domain.CouponDomain;
import com.dookay.coral.shop.promotion.service.ICouponService;

import java.util.Date;

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

	@Override
	public CouponDomain checkCoupon(String couponCode) {
		if(couponCode==null||"".equals(couponCode)){
			throw new ServiceException("请先输入优惠券码");
		}
		CouponQuery query = new CouponQuery();
		query.setCode(couponCode);
		CouponDomain couponDomain = getOne(query);
		if(couponDomain==null){
			throw new ServiceException("无此优惠券");
		}else{
			if(couponDomain.getIsValid()==0){
				throw new ServiceException("优惠券无效");
			}else{
				int num = couponDomain.getLeftTimes();
				Date startTime = couponDomain.getStartTime();
				Date endTime = couponDomain.getEndTime();
				Date nowTime = new Date();
				if(nowTime.after(endTime) || nowTime.before(startTime)){
					throw new ServiceException("优惠券失效");
				}
				if(couponDomain.getRuleType()!=0&&couponDomain.getRuleType()!=1&&num<=0){//0,1是无限次
					throw new ServiceException("优惠券使用次数不足");
				}
			}
		}
		return couponDomain;
	}
}
