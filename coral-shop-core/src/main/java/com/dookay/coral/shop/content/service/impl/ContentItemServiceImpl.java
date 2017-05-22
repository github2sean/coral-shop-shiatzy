package com.dookay.coral.shop.content.service.impl;

import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.shop.content.query.ContentItemQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.content.mapper.ContentItemMapper;
import com.dookay.coral.shop.content.domain.ContentItemDomain;
import com.dookay.coral.shop.content.service.IContentItemService;

import java.util.List;

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

	@Override
	public ContentItemDomain getContent(Long id) {
		ContentItemQuery query=new ContentItemQuery();
		query.setContentId(id);
		return getOne(query);
	}

	@Override
	public ContentItemDomain getContent(ContentItemQuery contentItemQuery) {
		return super.getFirst(contentItemQuery);
	}

	@Override
	public List<ContentItemDomain> getContentList(ContentItemQuery contentItemQuery) {
		return  super.getList(contentItemQuery);
	}

	@Override
	public PageList<ContentItemDomain> getContentPageList(ContentItemQuery contentItemQuery) {
		return super.getPageList(contentItemQuery);
	}

	@Override
	public void registerContent(ContentItemDomain contentItemDomain) {
		if(contentItemDomain.getCreatorId()==null ||contentItemDomain.getCreatorId()==0)
		{
			throw new ServiceException("分类不能为空");
		}
		if(contentItemDomain.getCreatorId() ==null ||contentItemDomain.getCreatorId()==0)
		{
			throw new ServiceException("创建人不能为空");
		}
		if(!StringUtils.isNotBlank(contentItemDomain.getThumb()))
		{
			throw new ServiceException("图片不能为空");
		}
		super.create(contentItemDomain);
	}

	@Override
	public ContentItemDomain createContent(ContentItemDomain contentItemDomain) {
		if(contentItemDomain.getCreatorId()==null ||contentItemDomain.getCreatorId()==0)
		{
			throw new ServiceException("分类不能为空");
		}
		if(contentItemDomain.getCreatorId() ==null ||contentItemDomain.getCreatorId()==0)
		{
			throw new ServiceException("创建人不能为空");
		}
		if(!StringUtils.isNotBlank(contentItemDomain.getThumb()))
		{
			throw new ServiceException("图片不能为空");
		}
		super.create(contentItemDomain);
		return contentItemDomain;
	}

	@Override
	public void updateContent(ContentItemDomain contentItemDomain) {
      super.update(contentItemDomain);
	}

	@Override
	public void deleteContent(ContentItemDomain contentItemDomain) {
		super.delete(contentItemDomain.getId());
	}
}
