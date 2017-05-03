package com.dookay.coral.shop.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.order.mapper.ReservationMapper;
import com.dookay.coral.shop.order.domain.ReservationDomain;
import com.dookay.coral.shop.order.service.IReservationService;

/**
 * 预约单的业务实现类
 * @author : luxor
 * @since : 2017年05月03日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("reservationService")
public class ReservationServiceImpl extends BaseServiceImpl<ReservationDomain> implements IReservationService {
	
	@Autowired
	private ReservationMapper reservationMapper;
	  
}
