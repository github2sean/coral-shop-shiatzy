package com.dookay.coral.shop.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.order.mapper.OrderReturnRequestMapper;
import com.dookay.coral.shop.order.domain.OrderReturnRequestDomain;
import com.dookay.coral.shop.order.service.IOrderReturnRequestService;

/**
 * 订单退货申请的业务实现类
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("orderReturnRequestService")
public class OrderReturnRequestServiceImpl extends BaseServiceImpl<OrderReturnRequestDomain> implements IOrderReturnRequestService {
	
	@Autowired
	private OrderReturnRequestMapper orderReturnRequestMapper;
	  
}
