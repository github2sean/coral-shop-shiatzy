package com.dookay.coral.shop.goods.service.impl;

import com.dookay.coral.common.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.dookay.coral.shop.goods.mapper.GoodsColorMapper;
import com.dookay.coral.shop.goods.domain.GoodsColorDomain;
import com.dookay.coral.shop.goods.service.IGoodsColorService;

/**
 * 商品颜色的业务实现类
 * @author : luxor
 * @since : 2017年05月06日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("goodsColorService")
public class GoodsColorServiceImpl extends BaseServiceImpl<GoodsColorDomain> implements IGoodsColorService {
	
	@Autowired
	private GoodsColorMapper goodsColorMapper;
	  
}
