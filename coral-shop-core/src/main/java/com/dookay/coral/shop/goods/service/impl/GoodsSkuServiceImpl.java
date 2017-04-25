package com.dookay.coral.shop.goods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.goods.mapper.GoodsSkuMapper;
import com.dookay.coral.shop.goods.domain.GoodsSkuDomain;
import com.dookay.coral.shop.goods.service.IGoodsSkuService;

/**
 * 商品sku的业务实现类
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("goodsSkuService")
public class GoodsSkuServiceImpl extends BaseServiceImpl<GoodsSkuDomain> implements IGoodsSkuService {
	
	@Autowired
	private GoodsSkuMapper goodsSkuMapper;
	  
}
