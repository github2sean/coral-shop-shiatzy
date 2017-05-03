package com.dookay.coral.shop.customer.service;

import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.shop.customer.domain.CustomerAddressDomain;
import com.dookay.coral.shop.customer.domain.CustomerDomain;

/**
 * 客户的业务层接口
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
public interface ICustomerService extends IBaseService<CustomerDomain> {
    /**
     * 注册客户
     * @param customerDomain
     * @param accountDomain
     * @return
     */
    CustomerDomain register(CustomerDomain customerDomain, AccountDomain accountDomain);

    CustomerDomain createCustomer(CustomerDomain customerDomain);

    CustomerDomain getAccount(Long accountId);

    Boolean updateCustomer(AccountDomain updateAccount, CustomerDomain updateCustomer, CustomerAddressDomain updaCustomerAddress);

    CustomerDomain validVip(CustomerDomain customerDomain,String phoneNumber);
}