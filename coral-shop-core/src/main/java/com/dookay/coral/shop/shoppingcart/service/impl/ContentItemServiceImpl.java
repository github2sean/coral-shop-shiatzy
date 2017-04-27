package com.dookay.coral.shop.shoppingcart.service.impl;

import com.dookay.coral.shop.shoppingcart.domain.ContentItemDomain;
import com.dookay.coral.shop.shoppingcart.mapper.ContentItemMapper;
import com.dookay.coral.shop.shoppingcart.service.IContentItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.service.impl.BaseServiceImpl;

/**
 * 内容的业务实现类
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("contentItemService")
public class ContentItemServiceImpl extends BaseServiceImpl<ContentItemDomain> implements IContentItemService {
	
	@Autowired
	private ContentItemMapper contentItemMapper;
	  
}
