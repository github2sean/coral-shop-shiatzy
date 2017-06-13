package com.dookay.coral.shop.index.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.index.mapper.IndexBlockGroupMapper;
import com.dookay.coral.shop.index.domain.IndexBlockGroupDomain;
import com.dookay.coral.shop.index.service.IIndexBlockGroupService;

/**
 * 首页区块分组的业务实现类
 * @author : luxor
 * @since : 2017年06月13日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("indexBlockGroupService")
public class IndexBlockGroupServiceImpl extends BaseServiceImpl<IndexBlockGroupDomain> implements IIndexBlockGroupService {
	
	@Autowired
	private IndexBlockGroupMapper indexBlockGroupMapper;
	  
}
