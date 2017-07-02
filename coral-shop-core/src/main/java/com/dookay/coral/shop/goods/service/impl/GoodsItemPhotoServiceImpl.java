package com.dookay.coral.shop.goods.service.impl;

import com.dookay.coral.common.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.dookay.coral.shop.goods.mapper.GoodsItemPhotoMapper;
import com.dookay.coral.shop.goods.domain.GoodsItemPhotoDomain;
import com.dookay.coral.shop.goods.service.IGoodsItemPhotoService;

/**
 * 商品项目照片的业务实现类
 * @author : luxor
 * @since : 2017年07月02日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("goodsItemPhotoService")
public class GoodsItemPhotoServiceImpl extends BaseServiceImpl<GoodsItemPhotoDomain> implements IGoodsItemPhotoService {
	
	@Autowired
	private GoodsItemPhotoMapper goodsItemPhotoMapper;
	  
}
