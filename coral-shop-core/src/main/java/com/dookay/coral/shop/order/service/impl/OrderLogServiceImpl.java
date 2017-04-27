package com.dookay.coral.shop.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.order.mapper.OrderLogMapper;
import com.dookay.coral.shop.order.domain.OrderLogDomain;
import com.dookay.coral.shop.order.service.IOrderLogService;

/**
 * 订单日志的业务实现类
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("orderLogService")
public class OrderLogServiceImpl extends BaseServiceImpl<OrderLogDomain> implements IOrderLogService {
	
	@Autowired
	private OrderLogMapper orderLogMapper;
	  
}
