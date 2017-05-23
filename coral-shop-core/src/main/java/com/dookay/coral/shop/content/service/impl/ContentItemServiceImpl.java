package com.dookay.coral.shop.content.service.impl;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.shop.content.domain.ContentCategoryDomain;
import com.dookay.coral.shop.content.query.ContentCategoryQuery;
import com.dookay.coral.shop.content.query.ContentItemQuery;
import com.dookay.coral.shop.content.service.IContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.content.mapper.ContentItemMapper;
import com.dookay.coral.shop.content.domain.ContentItemDomain;
import com.dookay.coral.shop.content.service.IContentItemService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    @Autowired
	private IContentItemService contentItemService;
    @Autowired
	private IContentCategoryService contentCategoryService;
	@Override
	public void withContent(PageList<ContentItemDomain> contentItemDomainList) {
		this.withContent(contentItemDomainList.getList());
	}

	@Override
	public void withContent(List<ContentItemDomain> contentItemDomainList) {
		List<Long> ids = contentItemDomainList.stream().map(ContentItemDomain::getCategoryId).collect(Collectors.toList()); //先获取数据库中所有的分类ID

		ContentCategoryQuery query = new ContentCategoryQuery();
		query.setIds(ids);//存入query

		List<ContentCategoryDomain> contentCategoryDomainList = contentCategoryService.getList(query); //获取分类对象

		for (ContentItemDomain contentItemDomain:contentItemDomainList){
             //获取分类对象的第一个数据
			ContentCategoryDomain contentCategoryDomain = contentCategoryDomainList.stream().filter(x-> Objects.equals(x.getId(), contentItemDomain.getCategoryId())).findFirst().orElse(null);
			//赋给ContentItemDomainC里的ContentCategoryDomain对象
			contentItemDomain.setCategory(contentCategoryDomain);
		}
	}

	@Override
	public void withContent(ContentItemDomain contentItemDomain) {
		ContentCategoryDomain contentCategoryDomain=contentCategoryService.get(contentItemDomain.getCategoryId());
		contentItemDomain.setCategory(contentCategoryDomain);
	}

	@Override
	public int countContentByCategoryId(Long categoryId) {
		ContentItemQuery query = new ContentItemQuery();
		query.setContentId(categoryId);
		return count(query);
	}
}
