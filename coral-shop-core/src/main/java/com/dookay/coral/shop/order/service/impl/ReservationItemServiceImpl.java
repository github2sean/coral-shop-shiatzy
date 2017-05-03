package com.dookay.coral.shop.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.order.mapper.ReservationItemMapper;
import com.dookay.coral.shop.order.domain.ReservationItemDomain;
import com.dookay.coral.shop.order.service.IReservationItemService;

/**
 * 预约单明细的业务实现类
 * @author : luxor
 * @since : 2017年05月03日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("reservationItemService")
public class ReservationItemServiceImpl extends BaseServiceImpl<ReservationItemDomain> implements IReservationItemService {
	
	@Autowired
	private ReservationItemMapper reservationItemMapper;
	  
}
