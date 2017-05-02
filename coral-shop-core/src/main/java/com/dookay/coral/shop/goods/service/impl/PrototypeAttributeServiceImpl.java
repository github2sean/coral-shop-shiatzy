package com.dookay.coral.shop.goods.service.impl;

import com.dookay.coral.shop.goods.query.PrototypeAttributeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.goods.mapper.PrototypeAttributeMapper;
import com.dookay.coral.shop.goods.domain.PrototypeAttributeDomain;
import com.dookay.coral.shop.goods.service.IPrototypeAttributeService;

/**
 * 原型属性的业务实现类
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("prototypeAttributeService")
public class PrototypeAttributeServiceImpl extends BaseServiceImpl<PrototypeAttributeDomain> implements IPrototypeAttributeService {
	
	@Autowired
	private PrototypeAttributeMapper prototypeAttributeMapper;

	@Override
	public PrototypeAttributeDomain getAttributeByPrototypeId(Long id) {

		PrototypeAttributeQuery query = new PrototypeAttributeQuery() ;
		query.setPrototypeId(id);
		return getOne(query);
	}
}
