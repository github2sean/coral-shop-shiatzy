package com.dookay.coral.shop.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.order.mapper.ReturnRequestMapper;
import com.dookay.coral.shop.order.domain.ReturnRequestDomain;
import com.dookay.coral.shop.order.service.IReturnRequestService;

/**
 * 订单退货申请的业务实现类
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("returnRequestService")
public class ReturnRequestServiceImpl extends BaseServiceImpl<ReturnRequestDomain> implements IReturnRequestService {
	
	@Autowired
	private ReturnRequestMapper returnRequestMapper;
	  
}
