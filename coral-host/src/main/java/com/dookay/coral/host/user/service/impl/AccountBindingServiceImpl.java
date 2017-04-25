package com.dookay.coral.host.user.service.impl;

import com.dookay.coral.host.user.domain.AccountBindingDomain;
import com.dookay.coral.host.user.mapper.AccountBindingMapper;
import com.dookay.coral.host.user.service.IAccountBindingService;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 账户第三方扩展的业务实现类
 * @author : stone
 * @since : 2017年03月02日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("accountBindingService")
public class AccountBindingServiceImpl extends BaseServiceImpl<AccountBindingDomain> implements IAccountBindingService {
	
	@Autowired
	private AccountBindingMapper accountBindingMapper;
	  
}
