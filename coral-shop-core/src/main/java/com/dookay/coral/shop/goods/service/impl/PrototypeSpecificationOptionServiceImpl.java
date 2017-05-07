package com.dookay.coral.shop.goods.service.impl;

import com.dookay.coral.shop.goods.domain.PrototypeAttributeOptionDomain;
import com.dookay.coral.shop.goods.query.PrototypeAttributeOptionQuery;
import com.dookay.coral.shop.goods.query.PrototypeSpecificationOptionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.goods.mapper.PrototypeSpecificationOptionMapper;
import com.dookay.coral.shop.goods.domain.PrototypeSpecificationOptionDomain;
import com.dookay.coral.shop.goods.service.IPrototypeSpecificationOptionService;

import java.util.List;

/**
 * 原型规格选项的业务实现类
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("prototypeSpecificationOptionService")
public class PrototypeSpecificationOptionServiceImpl extends BaseServiceImpl<PrototypeSpecificationOptionDomain> implements IPrototypeSpecificationOptionService {
	
	@Autowired
	private PrototypeSpecificationOptionMapper prototypeSpecificationOptionMapper;

	@Override
	public List getListBySpecificationId(Long id) {
		PrototypeSpecificationOptionQuery query = new PrototypeSpecificationOptionQuery();
		query.setSpecificationId(id);
		return getList(query);
	}
}
