package com.dookay.coral.shop.message.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.message.mapper.SmsMapper;
import com.dookay.coral.shop.message.domain.SmsDomain;
import com.dookay.coral.shop.message.service.ISmsService;

/**
 * 短信的业务实现类
 * @author : luxor
 * @since : 2017年06月13日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("smsService")
public class SmsServiceImpl extends BaseServiceImpl<SmsDomain> implements ISmsService {
	
	@Autowired
	private SmsMapper smsMapper;
	  
}
