package com.dookay.coral.shop.goods.service.impl;

import com.dookay.coral.common.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.dookay.coral.shop.goods.mapper.GoodsColorSeriesMapper;
import com.dookay.coral.shop.goods.domain.GoodsColorSeriesDomain;
import com.dookay.coral.shop.goods.service.IGoodsColorSeriesService;

/**
 * 商品颜色系列的业务实现类
 * @author : luxor
 * @since : 2017年07月06日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("goodsColorSeriesService")
public class GoodsColorSeriesServiceImpl extends BaseServiceImpl<GoodsColorSeriesDomain> implements IGoodsColorSeriesService {
	
	@Autowired
	private GoodsColorSeriesMapper goodsColorSeriesMapper;
	  
}
