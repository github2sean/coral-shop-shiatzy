package com.dookay.coral.shop.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.store.mapper.StoreCityMapper;
import com.dookay.coral.shop.store.domain.StoreCityDomain;
import com.dookay.coral.shop.store.service.IStoreCityService;

/**
 * 店铺城市的业务实现类
 * @author : luxor
 * @since : 2017年05月03日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("storeCityService")
public class StoreCityServiceImpl extends BaseServiceImpl<StoreCityDomain> implements IStoreCityService {
	
	@Autowired
	private StoreCityMapper storeCityMapper;
	  
}
