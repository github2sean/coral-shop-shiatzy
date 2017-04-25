package com.dookay.coral.shop.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.order.mapper.OrderShoppingCartItemMapper;
import com.dookay.coral.shop.order.domain.OrderShoppingCartItemDomain;
import com.dookay.coral.shop.order.service.IOrderShoppingCartItemService;

/**
 * 购物车的业务实现类
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("orderShoppingCartItemService")
public class OrderShoppingCartItemServiceImpl extends BaseServiceImpl<OrderShoppingCartItemDomain> implements IOrderShoppingCartItemService {
	
	@Autowired
	private OrderShoppingCartItemMapper orderShoppingCartItemMapper;
	  
}
