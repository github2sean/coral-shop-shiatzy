package com.dookay.coral.shop.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.order.mapper.OrderReturnRequestItemMapper;
import com.dookay.coral.shop.order.domain.OrderReturnRequestItemDomain;
import com.dookay.coral.shop.order.service.IOrderReturnRequestItemService;

/**
 * 订单退货申请明细的业务实现类
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("orderReturnRequestItemService")
public class OrderReturnRequestItemServiceImpl extends BaseServiceImpl<OrderReturnRequestItemDomain> implements IOrderReturnRequestItemService {
	
	@Autowired
	private OrderReturnRequestItemMapper orderReturnRequestItemMapper;
	  
}
