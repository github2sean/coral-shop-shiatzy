package com.dookay.coral.shop.content.service.impl;

import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.shop.content.query.ContentCategoryQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.content.mapper.ContentCategoryMapper;
import com.dookay.coral.shop.content.domain.ContentCategoryDomain;
import com.dookay.coral.shop.content.service.IContentCategoryService;

import java.util.List;

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

	@Override
	public ContentCategoryDomain getContentCategory(Long id) {
		return super.get(id);
	}

	@Override
	public ContentCategoryDomain getContentCategory(ContentCategoryQuery contentCategoryQuery) {

		return super.getFirst(contentCategoryQuery);
	}

	@Override
	public List<ContentCategoryDomain> getContentCategoryList(ContentCategoryQuery contentCategoryQuery) {
		return super.getList(contentCategoryQuery);
	}

	@Override
	public PageList<ContentCategoryDomain> getContentCategoryPageList(ContentCategoryQuery contentCategoryQuery) {
		return super.getPageList(contentCategoryQuery);
	}

	@Override
	public void registerContentCategory(ContentCategoryDomain contentCategoryDomain) {
		if(!StringUtils.isNotBlank(contentCategoryDomain.getTitle()))
		{
			throw new ServiceException("标题不能为空");
		}
		if(contentCategoryDomain.getParentId()==null || contentCategoryDomain.getParentId()==0)
		{
			throw new ServiceException("父类不能为空");
		}
		super.create(contentCategoryDomain);
	}

	@Override
	public ContentCategoryDomain createContentCategory(ContentCategoryDomain contentCategoryDomain) {
		if(!StringUtils.isNotBlank(contentCategoryDomain.getTitle()))
		{
			throw new ServiceException("标题不能为空");
		}
		if(contentCategoryDomain.getParentId()==null || contentCategoryDomain.getParentId()==0)
		{
			throw new ServiceException("父类不能为空");
		}
		return super.create(contentCategoryDomain);
	}

	@Override
	public void updateContentCategory(ContentCategoryDomain contentCategoryDomain) {
		super.update(contentCategoryDomain);
	}

	@Override
	public void deleteContentCategory(ContentCategoryDomain contentCategoryDomain) {
		super.delete(contentCategoryDomain.getId());
	}
}
