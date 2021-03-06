package com.dookay.coral.shop.goods.service.impl;

import com.dookay.coral.shop.goods.query.PrototypeAttributeOptionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.goods.mapper.PrototypeAttributeOptionMapper;
import com.dookay.coral.shop.goods.domain.PrototypeAttributeOptionDomain;
import com.dookay.coral.shop.goods.service.IPrototypeAttributeOptionService;

import java.util.List;

/**
 * 原型属性选项的业务实现类
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("prototypeAttributeOptionService")
public class PrototypeAttributeOptionServiceImpl extends BaseServiceImpl<PrototypeAttributeOptionDomain> implements IPrototypeAttributeOptionService {
	
	@Autowired
	private PrototypeAttributeOptionMapper prototypeAttributeOptionMapper;

	@Override
	public List getListByAttributeId(Long id) {
		PrototypeAttributeOptionQuery query = new PrototypeAttributeOptionQuery();
		query.setPrototypeAttributeId(id);
		return getList(query);
	}
}
