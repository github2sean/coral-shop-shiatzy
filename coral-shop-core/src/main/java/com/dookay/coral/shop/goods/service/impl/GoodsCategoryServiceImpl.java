package com.dookay.coral.shop.goods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.goods.mapper.GoodsCategoryMapper;
import com.dookay.coral.shop.goods.domain.GoodsCategoryDomain;
import com.dookay.coral.shop.goods.service.IGoodsCategoryService;

/**
 * 商品分类的业务实现类
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("goodsCategoryService")
public class GoodsCategoryServiceImpl extends BaseServiceImpl<GoodsCategoryDomain> implements IGoodsCategoryService {
	
	@Autowired
	private GoodsCategoryMapper goodsCategoryMapper;
	  
}
