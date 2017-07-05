package com.dookay.coral.shop.goods.service.impl;

import com.dookay.coral.common.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.dookay.coral.shop.goods.mapper.GoodsSkinMapper;
import com.dookay.coral.shop.goods.domain.GoodsSkinDomain;
import com.dookay.coral.shop.goods.service.IGoodsSkinService;

/**
 * 商品材质的业务实现类
 * @author : luxor
 * @since : 2017年07月05日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("goodsSkinService")
public class GoodsSkinServiceImpl extends BaseServiceImpl<GoodsSkinDomain> implements IGoodsSkinService {
	
	@Autowired
	private GoodsSkinMapper goodsSkinMapper;
	  
}
