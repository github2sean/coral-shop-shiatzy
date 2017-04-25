package com.dookay.coral.shop.goods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.goods.mapper.GoodsPrototypeAttributeMapper;
import com.dookay.coral.shop.goods.domain.GoodsPrototypeAttributeDomain;
import com.dookay.coral.shop.goods.service.IGoodsPrototypeAttributeService;

/**
 * 原型属性的业务实现类
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("goodsPrototypeAttributeService")
public class GoodsPrototypeAttributeServiceImpl extends BaseServiceImpl<GoodsPrototypeAttributeDomain> implements IGoodsPrototypeAttributeService {
	
	@Autowired
	private GoodsPrototypeAttributeMapper goodsPrototypeAttributeMapper;
	  
}
