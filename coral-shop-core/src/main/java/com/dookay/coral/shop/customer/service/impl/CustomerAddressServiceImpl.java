package com.dookay.coral.shop.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.customer.mapper.CustomerAddressMapper;
import com.dookay.coral.shop.customer.domain.CustomerAddressDomain;
import com.dookay.coral.shop.customer.service.ICustomerAddressService;

/**
 * 客户地址的业务实现类
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("customerAddressService")
public class CustomerAddressServiceImpl extends BaseServiceImpl<CustomerAddressDomain> implements ICustomerAddressService {
	
	@Autowired
	private CustomerAddressMapper customerAddressMapper;
	  
}
