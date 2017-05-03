package com.dookay.coral.shop.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.store.mapper.StoreMapper;
import com.dookay.coral.shop.store.domain.StoreDomain;
import com.dookay.coral.shop.store.service.IStoreService;

/**
 * 店铺的业务实现类
 * @author : luxor
 * @since : 2017年05月03日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("storeService")
public class StoreServiceImpl extends BaseServiceImpl<StoreDomain> implements IStoreService {
	
	@Autowired
	private StoreMapper storeMapper;
	  
}
