package com.dookay.coral.shop.goods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.goods.mapper.PrototypeSpecificationOptionMapper;
import com.dookay.coral.shop.goods.domain.PrototypeSpecificationOptionDomain;
import com.dookay.coral.shop.goods.service.IPrototypeSpecificationOptionService;

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
	  
}
