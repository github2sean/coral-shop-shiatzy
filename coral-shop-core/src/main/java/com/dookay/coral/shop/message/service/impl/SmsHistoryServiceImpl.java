package com.dookay.coral.shop.message.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.message.mapper.SmsHistoryMapper;
import com.dookay.coral.shop.message.domain.SmsHistoryDomain;
import com.dookay.coral.shop.message.service.ISmsHistoryService;

/**
 * 短信历史的业务实现类
 * @author : luxor
 * @since : 2017年06月13日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("smsHistoryService")
public class SmsHistoryServiceImpl extends BaseServiceImpl<SmsHistoryDomain> implements ISmsHistoryService {
	
	@Autowired
	private SmsHistoryMapper smsHistoryMapper;
	  
}
