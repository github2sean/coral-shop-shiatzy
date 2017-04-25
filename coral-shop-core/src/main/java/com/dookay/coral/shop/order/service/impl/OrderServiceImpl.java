package com.dookay.coral.shop.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.order.mapper.OrderMapper;
import com.dookay.coral.shop.order.domain.OrderDomain;
import com.dookay.coral.shop.order.service.IOrderService;

/**
 * 商品sku规格值的业务实现类
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("orderService")
public class OrderServiceImpl extends BaseServiceImpl<OrderDomain> implements IOrderService {
	
	@Autowired
	private OrderMapper orderMapper;
	  
}
