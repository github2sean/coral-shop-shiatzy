package com.dookay.coral.shop.goods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.goods.mapper.GoodsPrototypeSpecificationOptionMapper;
import com.dookay.coral.shop.goods.domain.GoodsPrototypeSpecificationOptionDomain;
import com.dookay.coral.shop.goods.service.IGoodsPrototypeSpecificationOptionService;

/**
 * 原型规格选项的业务实现类
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("goodsPrototypeSpecificationOptionService")
public class GoodsPrototypeSpecificationOptionServiceImpl extends BaseServiceImpl<GoodsPrototypeSpecificationOptionDomain> implements IGoodsPrototypeSpecificationOptionService {
	
	@Autowired
	private GoodsPrototypeSpecificationOptionMapper goodsPrototypeSpecificationOptionMapper;
	  
}
