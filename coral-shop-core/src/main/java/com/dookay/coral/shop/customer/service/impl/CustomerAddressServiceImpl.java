package com.dookay.coral.shop.customer.service.impl;

import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.query.CustomerAddressQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.customer.mapper.CustomerAddressMapper;
import com.dookay.coral.shop.customer.domain.CustomerAddressDomain;
import com.dookay.coral.shop.customer.service.ICustomerAddressService;
import org.springframework.transaction.annotation.Transactional;

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

	@Override
	public CustomerAddressDomain getAccount(Long customerId) {

		CustomerAddressQuery query = new CustomerAddressQuery();
		query.setCustomerId(customerId);
		return super.getFirst(query);

	}

	@Override
	@Transactional("transactionManager")
	public void createByCustomer(CustomerDomain customerDomain, CustomerAddressDomain addressModel) {
		if (customerDomain.getId()==null){
			throw new ServiceException("用户Id为空");
		}
		addressModel.setCustomerId(customerDomain.getId());
		create(addressModel);
	}
}
