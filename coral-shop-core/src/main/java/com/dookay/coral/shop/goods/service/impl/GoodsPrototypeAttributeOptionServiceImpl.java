package com.dookay.coral.shop.goods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.goods.mapper.GoodsPrototypeAttributeOptionMapper;
import com.dookay.coral.shop.goods.domain.GoodsPrototypeAttributeOptionDomain;
import com.dookay.coral.shop.goods.service.IGoodsPrototypeAttributeOptionService;

/**
 * 原型属性选项的业务实现类
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("goodsPrototypeAttributeOptionService")
public class GoodsPrototypeAttributeOptionServiceImpl extends BaseServiceImpl<GoodsPrototypeAttributeOptionDomain> implements IGoodsPrototypeAttributeOptionService {
	
	@Autowired
	private GoodsPrototypeAttributeOptionMapper goodsPrototypeAttributeOptionMapper;
	  
}
