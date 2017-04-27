package com.dookay.coral.shop.goods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.goods.mapper.GoodsPrototypeMapper;
import com.dookay.coral.shop.goods.domain.GoodsPrototypeDomain;
import com.dookay.coral.shop.goods.service.IGoodsPrototypeService;

/**
 * 商品原型的业务实现类
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("goodsPrototypeService")
public class GoodsPrototypeServiceImpl extends BaseServiceImpl<GoodsPrototypeDomain> implements IGoodsPrototypeService {
	
	@Autowired
	private GoodsPrototypeMapper goodsPrototypeMapper;
	  
}
