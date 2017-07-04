package com.dookay.coral.shop.customer.service.impl;

import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.common.web.validate.FieldMatch;
import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.host.user.service.IAccountService;
import com.dookay.coral.shop.customer.domain.CustomerAddressDomain;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.mapper.CustomerMapper;
import com.dookay.coral.shop.customer.query.CustomerQuery;
import com.dookay.coral.shop.customer.service.ICustomerAddressService;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.temp.domain.TempMemberDomain;
import com.dookay.coral.shop.temp.query.TempMemberQuery;
import com.dookay.coral.shop.temp.service.ITempMemberService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.customer.mapper.CustomerMapper;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.service.ICustomerService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
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
	private CustomerMapper customerMapper;

	@Autowired
	private IAccountService accountService;

	@Autowired
	private ICustomerAddressService customerAddressService;

	@Autowired
	private ITempMemberService tempMemberService;


	/**
	 * 注册客户
	 *
	 * @param customerDomain
	 * @param accountDomain
	 * @return
	 */
	@Override
	@Transactional("transactionManager")
	public CustomerDomain register(CustomerDomain customerDomain, AccountDomain accountDomain) {
		CustomerDomain insertCustomer= null;
		if(accountService.createAccount(accountDomain)!=null) {
			insertCustomer = new CustomerDomain();
			insertCustomer.setAccountId(accountDomain.getId());
			insertCustomer.setEmail(accountDomain.getEmail());
			insertCustomer.setCreateTime(accountDomain.getCreateTime());
		}
		return createCustomer(insertCustomer);
	}


	@Override
	@Transactional("transactionManager")
	public CustomerDomain createCustomer(CustomerDomain customerDomain) {
		CustomerDomain getCustomer =null;
		try{
			CustomerDomain customer = getAccount(customerDomain.getAccountId());
			if (customer != null) {
				throw new ServiceException("账户已存在");
			}
			create(customerDomain);
			getCustomer = getAccount(customerDomain.getAccountId());
			if(getCustomer!=null){
				/*CustomerAddressDomain customerAddressDomain = new CustomerAddressDomain();
				customerAddressDomain.setCustomerId(getCustomer.getId());
				customerAddressService.create(customerAddressDomain);*/
			}else {
				throw new ServiceException("创建用户失败");
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return getCustomer;
	}


	@Override
	public CustomerDomain getAccount(Long accountId) {
		CustomerQuery query = new CustomerQuery();
		query.setAccountId(accountId);
		return super.getFirst(query);
	}

	@Override
	@Transactional("transactionManager")
	public Boolean updateCustomer(AccountDomain updateAccount, CustomerDomain updateCustomer, CustomerAddressDomain updaCustomerAddress) {
		System.out.print("ac"+updateAccount+"\ncu:"+updateCustomer+"\ncua:"+updaCustomerAddress);
		Boolean isSuccess = true;
		try {
			accountService.update(updateAccount);
			update(updateCustomer);
			customerAddressService.update(updaCustomerAddress);
		}catch (Exception e) {
			throw new ServiceException("修改失败");
		}
		return isSuccess;
	}

	@Override
	@Transactional("transactionManager")
	public CustomerDomain validVip(CustomerDomain customerDomain,String phoneNumber) {
		if(StringUtils.isBlank(phoneNumber)||customerDomain==null){
			throw new ServiceException("输入为空");
		}
		TempMemberQuery tempMemberQuery = new TempMemberQuery();
		List<String> cardType = new ArrayList<>();
		cardType.add("CN-A");
		cardType.add("CN-B");
		cardType.add("CN-C");
		cardType.add("CN-D");
		tempMemberQuery.setMobile(phoneNumber);
		tempMemberQuery.setCardType(cardType);
		TempMemberDomain tempMemberDomain = tempMemberService.getFirst(tempMemberQuery);
		if(tempMemberDomain==null){
			throw new ServiceException("验证会员失败");
		}else{
			/*String record  = customerDomain.getPhone();
			if(StringUtils.isNotBlank(record)&&record.length()>2){
				record = record.substring(2,record.length());
			}
			if(phoneNumber.equals(record)){*/
				customerDomain.setIsArtClubMember(1);
				customerDomain.setTempMemberDomain(tempMemberDomain);
				customerDomain.setValidMobile(tempMemberDomain.getMobile());
				update(customerDomain);
			/*}else {
				throw new ServiceException("验证会员失败");
			}*/
		}
		return customerDomain;
	}

	@Override
	public void forbid(Long id) {
		AccountDomain accountDomain = accountService.get(id);
		accountDomain.setIsValid(0);
		accountService.update(accountDomain);
	}

	@Override
	public void enable(Long id) {
		AccountDomain accountDomain = accountService.get(id);
		accountDomain.setIsValid(1);
		accountService.update(accountDomain);
	}

}
