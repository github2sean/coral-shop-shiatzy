package com.dookay.coral.shop.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.customer.mapper.CustomerMapper;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.service.ICustomerService;

/**
 * 客户的业务实现类
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("customerService")
public class CustomerServiceImpl extends BaseServiceImpl<CustomerDomain> implements ICustomerService {
	
	@Autowired
	private CustomerMapper customerMapper;
	  
}
