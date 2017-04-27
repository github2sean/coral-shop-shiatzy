package com.dookay.coral.shop.goods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.goods.mapper.PrototypeSpecificationMapper;
import com.dookay.coral.shop.goods.domain.PrototypeSpecificationDomain;
import com.dookay.coral.shop.goods.service.IPrototypeSpecificationService;

/**
 * 原型规格的业务实现类
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("prototypeSpecificationService")
public class PrototypeSpecificationServiceImpl extends BaseServiceImpl<PrototypeSpecificationDomain> implements IPrototypeSpecificationService {
	
	@Autowired
	private PrototypeSpecificationMapper prototypeSpecificationMapper;
	  
}
