package com.dookay.coral.shop.message.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.message.mapper.EmailHistoryMapper;
import com.dookay.coral.shop.message.domain.EmailHistoryDomain;
import com.dookay.coral.shop.message.service.IEmailHistoryService;

/**
 * 邮件历史的业务实现类
 * @author : luxor
 * @since : 2017年06月13日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("emailHistoryService")
public class EmailHistoryServiceImpl extends BaseServiceImpl<EmailHistoryDomain> implements IEmailHistoryService {
	
	@Autowired
	private EmailHistoryMapper emailHistoryMapper;
	  
}
