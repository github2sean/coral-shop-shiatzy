package com.dookay.coral.shop.content.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.content.mapper.SubscribeMapper;
import com.dookay.coral.shop.content.domain.SubscribeDomain;
import com.dookay.coral.shop.content.service.ISubscribeService;

/**
 * 订阅的业务实现类
 * @author : luxor
 * @since : 2017年06月14日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("subscribeService")
public class SubscribeServiceImpl extends BaseServiceImpl<SubscribeDomain> implements ISubscribeService {
	
	@Autowired
	private SubscribeMapper subscribeMapper;
	  
}
