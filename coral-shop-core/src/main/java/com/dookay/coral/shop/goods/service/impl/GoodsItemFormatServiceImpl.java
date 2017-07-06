package com.dookay.coral.shop.goods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.goods.mapper.GoodsItemFormatMapper;
import com.dookay.coral.shop.goods.domain.GoodsItemFormatDomain;
import com.dookay.coral.shop.goods.service.IGoodsItemFormatService;

/**
 * 商品项目参数的业务实现类
 * @author : luxor
 * @since : 2017年07月06日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("goodsItemFormatService")
public class GoodsItemFormatServiceImpl extends BaseServiceImpl<GoodsItemFormatDomain> implements IGoodsItemFormatService {
	
	@Autowired
	private GoodsItemFormatMapper goodsItemFormatMapper;
	  
}
