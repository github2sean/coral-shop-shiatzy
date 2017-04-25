package com.dookay.coral.shop.goods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.goods.mapper.GoodsSkuSpecificationValueMapper;
import com.dookay.coral.shop.goods.domain.GoodsSkuSpecificationValueDomain;
import com.dookay.coral.shop.goods.service.IGoodsSkuSpecificationValueService;

/**
 * 商品sku规格值的业务实现类
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("goodsSkuSpecificationValueService")
public class GoodsSkuSpecificationValueServiceImpl extends BaseServiceImpl<GoodsSkuSpecificationValueDomain> implements IGoodsSkuSpecificationValueService {
	
	@Autowired
	private GoodsSkuSpecificationValueMapper goodsSkuSpecificationValueMapper;
	  
}
