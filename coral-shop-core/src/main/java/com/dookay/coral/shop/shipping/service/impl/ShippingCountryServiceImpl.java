package com.dookay.coral.shop.shipping.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.shipping.mapper.ShippingCountryMapper;
import com.dookay.coral.shop.shipping.domain.ShippingCountryDomain;
import com.dookay.coral.shop.shipping.service.IShippingCountryService;

/**
 * 配送国家的业务实现类
 * @author : luxor
 * @since : 2017年05月03日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("shippingCountryService")
public class ShippingCountryServiceImpl extends BaseServiceImpl<ShippingCountryDomain> implements IShippingCountryService {
	
	@Autowired
	private ShippingCountryMapper shippingCountryMapper;
	  
}
