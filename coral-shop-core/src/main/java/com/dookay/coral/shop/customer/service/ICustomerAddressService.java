package com.dookay.coral.shop.customer.service;

import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.shop.customer.domain.CustomerAddressDomain;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.mapper.CustomerAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 客户地址的业务层接口
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
public interface ICustomerAddressService extends IBaseService<CustomerAddressDomain> {

    CustomerAddressDomain getAccount(Long id);

    void createByCustomer(CustomerDomain customerDomain, CustomerAddressDomain addressModel);

}
