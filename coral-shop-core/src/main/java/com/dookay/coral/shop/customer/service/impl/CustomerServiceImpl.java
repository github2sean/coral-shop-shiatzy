package com.dookay.coral.shop.customer.service.impl;

import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.common.web.validate.FieldMatch;
import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.host.user.query.AccountQuery;
import com.dookay.coral.host.user.service.impl.AccountServiceImpl;
import com.dookay.coral.shop.customer.query.CustomerQuery;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.standard.DateTimeContext;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.customer.mapper.CustomerMapper;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.service.ICustomerService;

import javax.transaction.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
	private AccountServiceImpl accountService;

	/**
	 * 注册客户
	 *
	 * @param customerDomain
	 * @param accountDomain
	 * @return
	 */
	@Override
	@org.springframework.transaction.annotation.Transactional("transactionManager")
	public CustomerDomain register(CustomerDomain customerDomain, AccountDomain accountDomain) {
		CustomerDomain insertCustomer= null;
		if(accountService.createAccount(accountDomain)!=null) {
			insertCustomer = new CustomerDomain();
			insertCustomer.setAccountId(accountDomain.getId());
			insertCustomer.setEmail(accountDomain.getEmail());
			Date creatDate = new Date();
			insertCustomer.setCreateTime(creatDate);
		}
		return createCustomer(insertCustomer);
	}


	@Override
	public CustomerDomain createCustomer(CustomerDomain customerDomain) {
		CustomerDomain customer = getAccount(customerDomain.getAccountId());
		if (customer != null) {
			throw new ServiceException("账户已存在");
		}
		super.create(customerDomain);
		return customerDomain;
	}

	@Override
	public CustomerDomain getAccount(Long accountId) {
		CustomerQuery query = new CustomerQuery();
		query.setAccountId(accountId);
		return super.getOne(query);
	}

}
