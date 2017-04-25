package com.dookay.coral.shop.goods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.goods.mapper.GoodsPrototypeSpecificationMapper;
import com.dookay.coral.shop.goods.domain.GoodsPrototypeSpecificationDomain;
import com.dookay.coral.shop.goods.service.IGoodsPrototypeSpecificationService;

/**
 * 原型规格的业务实现类
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("goodsPrototypeSpecificationService")
public class GoodsPrototypeSpecificationServiceImpl extends BaseServiceImpl<GoodsPrototypeSpecificationDomain> implements IGoodsPrototypeSpecificationService {
	
	@Autowired
	private GoodsPrototypeSpecificationMapper goodsPrototypeSpecificationMapper;
	  
}
