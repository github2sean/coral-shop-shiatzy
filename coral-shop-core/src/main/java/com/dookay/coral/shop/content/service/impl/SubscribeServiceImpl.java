package com.dookay.coral.shop.content.service.impl;

import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.shop.content.query.SubscribeQuery;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.content.mapper.SubscribeMapper;
import com.dookay.coral.shop.content.domain.SubscribeDomain;
import com.dookay.coral.shop.content.service.ISubscribeService;

import java.util.Date;

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

	@Override
	public void createSubscribe(String email) {
		SubscribeQuery subscribeQuery = new SubscribeQuery();
		subscribeQuery.setEmail(email);
		Integer count = super.count(subscribeQuery);
		if(count>0){
			throw new ServiceException("邮件地址已经存在");
		}

		SubscribeDomain subscribeDomain = new SubscribeDomain();
		subscribeDomain.setCreateTime(new Date());
		subscribeDomain.setEmail(email);
		super.create(subscribeDomain);
	}
}
