package com.dookay.coral.shop.index.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.index.mapper.IndexBlockMapper;
import com.dookay.coral.shop.index.domain.IndexBlockDomain;
import com.dookay.coral.shop.index.service.IIndexBlockService;

/**
 * 首页区块的业务实现类
 * @author : luxor
 * @since : 2017年06月13日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("indexBlockService")
public class IndexBlockServiceImpl extends BaseServiceImpl<IndexBlockDomain> implements IIndexBlockService {
	
	@Autowired
	private IndexBlockMapper indexBlockMapper;
	  
}
