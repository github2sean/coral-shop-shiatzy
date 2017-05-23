package com.dookay.coral.shop.content.service.impl;

import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.shop.content.enums.ContentCategoryLevel;
import com.dookay.coral.shop.content.query.ContentCategoryQuery;
import com.dookay.coral.shop.content.service.IContentItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<ContentCategoryDomain> listCategory(ContentCategoryQuery contentCategoryQuery) {

		List<ContentCategoryDomain> contentCategoryDomainList = super.getList(contentCategoryQuery);//获取对象集合

		for (ContentCategoryDomain contentCategoryDomain :contentCategoryDomainList){

			List<ContentCategoryDomain> childrenCategoryList = this.listCategoryByParentId(contentCategoryDomain.getId());

			contentCategoryDomain.setChildren(childrenCategoryList);

			if(contentCategoryDomain.getParentId() != null && contentCategoryDomain.getParentId()>0){

				ContentCategoryDomain parent = this.get(contentCategoryDomain.getParentId());

				contentCategoryDomain.setParent(parent);
			}
		}
		return contentCategoryDomainList;
	}

	@Override
	public List<ContentCategoryDomain> listCategoryByParentId(Long parentId) {

		ContentCategoryQuery contentCategoryQuery = new ContentCategoryQuery();

		contentCategoryQuery.setParentId(parentId);

		return super.getList(contentCategoryQuery);
	}

	@Override
	public ContentCategoryDomain getCategory(Long categoryId) {
		ContentCategoryDomain contentCategoryDomain = super.get(categoryId);
		List<ContentCategoryDomain> childrenCategoryList = this.listCategoryByParentId(contentCategoryDomain.getId());
		contentCategoryDomain.setChildren(childrenCategoryList);
		return  contentCategoryDomain;
	}
}
