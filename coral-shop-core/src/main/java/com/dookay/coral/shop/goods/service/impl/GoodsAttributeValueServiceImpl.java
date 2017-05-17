package com.dookay.coral.shop.goods.service.impl;

import com.dookay.coral.shop.goods.query.GoodsAttributeValueQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.goods.mapper.GoodsAttributeValueMapper;
import com.dookay.coral.shop.goods.domain.GoodsAttributeValueDomain;
import com.dookay.coral.shop.goods.service.IGoodsAttributeValueService;

import java.util.List;

/**
 * 商品属性值的业务实现类
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("goodsAttributeValueService")
public class GoodsAttributeValueServiceImpl extends BaseServiceImpl<GoodsAttributeValueDomain> implements IGoodsAttributeValueService {
	
	@Autowired
	private GoodsAttributeValueMapper goodsAttributeValueMapper;

	@Override
	public GoodsAttributeValueDomain goodsAttributeValueDomain(Long id) {
		GoodsAttributeValueQuery  query =new GoodsAttributeValueQuery();
		query.setId(id);
		return getOne(query);
	}
}
