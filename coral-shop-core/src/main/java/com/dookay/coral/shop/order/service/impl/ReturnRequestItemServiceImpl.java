package com.dookay.coral.shop.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.order.mapper.ReturnRequestItemMapper;
import com.dookay.coral.shop.order.domain.ReturnRequestItemDomain;
import com.dookay.coral.shop.order.service.IReturnRequestItemService;

/**
 * 订单退货申请明细的业务实现类
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("returnRequestItemService")
public class ReturnRequestItemServiceImpl extends BaseServiceImpl<ReturnRequestItemDomain> implements IReturnRequestItemService {
	
	@Autowired
	private ReturnRequestItemMapper returnRequestItemMapper;
	  
}
