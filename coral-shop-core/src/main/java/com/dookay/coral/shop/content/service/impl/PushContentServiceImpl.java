package com.dookay.coral.shop.content.service.impl;

import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.content.domain.ContentCategoryDomain;
import com.dookay.coral.shop.content.domain.PushContentDomain;
import com.dookay.coral.shop.content.mapper.ContentCategoryMapper;
import com.dookay.coral.shop.content.mapper.PushContentMapper;
import com.dookay.coral.shop.content.query.ContentCategoryQuery;
import com.dookay.coral.shop.content.service.IContentCategoryService;
import com.dookay.coral.shop.content.service.IPushContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 内容分类的业务实现类
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("pushContentService")
public class PushContentServiceImpl extends BaseServiceImpl<PushContentDomain> implements IPushContentService {
	
	@Autowired
	private PushContentMapper pushContentMapper;

}
