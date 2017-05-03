package com.dookay.coral.shop.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.store.mapper.StoreCountryMapper;
import com.dookay.coral.shop.store.domain.StoreCountryDomain;
import com.dookay.coral.shop.store.service.IStoreCountryService;

/**
 * 店铺国家的业务实现类
 * @author : luxor
 * @since : 2017年05月03日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("storeCountryService")
public class StoreCountryServiceImpl extends BaseServiceImpl<StoreCountryDomain> implements IStoreCountryService {
	
	@Autowired
	private StoreCountryMapper storeCountryMapper;
	  
}
