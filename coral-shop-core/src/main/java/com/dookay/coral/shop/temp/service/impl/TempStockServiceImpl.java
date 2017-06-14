package com.dookay.coral.shop.temp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyd.common.persistence.Mapper;
import com.lyd.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.temp.mapper.TempStockMapper;
import com.dookay.coral.shop.temp.domain.TempStockDomain;
import com.dookay.coral.shop.temp.service.ITempStockService;

/**
 * 临时库存的业务实现类
 * @author : luxor
 * @since : 2017年06月14日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("tempStockService")
public class TempStockServiceImpl extends BaseServiceImpl<TempStockDomain> implements ITempStockService {
	
	@Autowired
	private TempStockMapper tempStockMapper;
	  
}
