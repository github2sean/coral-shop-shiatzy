package com.dookay.coral.shop.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.order.mapper.ShoppingCartItemMapper;
import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;
import com.dookay.coral.shop.order.service.IShoppingCartItemService;

/**
 * 购物车的业务实现类
 * @author : luxor
 * @since : 2017年05月03日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("shoppingCartItemService")
public class ShoppingCartItemServiceImpl extends BaseServiceImpl<ShoppingCartItemDomain> implements IShoppingCartItemService {
	
	@Autowired
	private ShoppingCartItemMapper shoppingCartItemMapper;
	  
}
