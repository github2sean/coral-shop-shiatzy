package com.dookay.coral.shop.content.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.content.mapper.ContentCategoryMapper;
import com.dookay.coral.shop.content.domain.ContentCategoryDomain;
import com.dookay.coral.shop.content.service.IContentCategoryService;

/**
 * 内容分类的业务实现类
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("contentCategoryService")
public class ContentCategoryServiceImpl extends BaseServiceImpl<ContentCategoryDomain> implements IContentCategoryService {
	
	@Autowired
	private ContentCategoryMapper contentCategoryMapper;
	  
}
