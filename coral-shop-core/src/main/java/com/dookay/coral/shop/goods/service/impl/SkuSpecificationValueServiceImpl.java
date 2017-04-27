package com.dookay.coral.shop.goods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.goods.mapper.SkuSpecificationValueMapper;
import com.dookay.coral.shop.goods.domain.SkuSpecificationValueDomain;
import com.dookay.coral.shop.goods.service.ISkuSpecificationValueService;

import java.util.List;

/**
 * 商品sku规格值的业务实现类
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("skuSpecificationValueService")
public class SkuSpecificationValueServiceImpl extends BaseServiceImpl<SkuSpecificationValueDomain> implements ISkuSpecificationValueService {
	
	@Autowired
	private SkuSpecificationValueMapper skuSpecificationValueMapper;

	@Override
	public List<SkuSpecificationValueDomain> listSkuSpecificationValueBySkuId(Long skuId) {
		return null;
	}
}
