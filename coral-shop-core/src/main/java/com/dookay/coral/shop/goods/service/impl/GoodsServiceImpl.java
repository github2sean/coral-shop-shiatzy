package com.dookay.coral.shop.goods.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.shop.goods.domain.GoodsPrototypeDomain;
import com.dookay.coral.shop.goods.domain.PrototypeSpecificationDomain;
import com.dookay.coral.shop.goods.domain.PrototypeSpecificationOptionDomain;
import com.dookay.coral.shop.goods.query.GoodsPrototypeQuery;
import com.dookay.coral.shop.goods.query.GoodsQuery;
import com.dookay.coral.shop.goods.query.PrototypeSpecificationOptionQuery;
import com.dookay.coral.shop.goods.query.PrototypeSpecificationQuery;
import com.dookay.coral.shop.goods.service.IGoodsPrototypeService;
import com.dookay.coral.shop.goods.service.IPrototypeSpecificationOptionService;
import com.dookay.coral.shop.goods.service.IPrototypeSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.goods.mapper.GoodsMapper;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.service.IGoodsService;

import java.util.List;

/**
 * 商品的业务实现类
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("goodsService")
public class GoodsServiceImpl extends BaseServiceImpl<GoodsDomain> implements IGoodsService {
	
	@Autowired
	private GoodsMapper goodsMapper;

	@Autowired
	private IGoodsPrototypeService goodsPrototypeService;
	@Autowired
	private IPrototypeSpecificationService prototypeSpecificationService;
	@Autowired
	private IPrototypeSpecificationOptionService prototypeSpecificationOptionService;
	@Override
	public PageList<GoodsDomain> getGoodsList(GoodsQuery query) {
		return getPageList(query);
	}

	@Override
	public int countGoodsByCategoryId(Long categoryId) {
		GoodsQuery goodsQuery = new GoodsQuery();
		goodsQuery.setCategoryId(categoryId);
		return super.count(goodsQuery);
	}

	@Override
	public void withSpecificationList(GoodsDomain goodsDomain) {
		Long prototypeId = goodsDomain.getPrototypeId();
		PrototypeSpecificationQuery prototypeSpecificationQuery = new PrototypeSpecificationQuery();
		prototypeSpecificationQuery.setPrototypeId(prototypeId);
		List<PrototypeSpecificationDomain> prototypeSpecificationDomainList = prototypeSpecificationService.getList(prototypeSpecificationQuery);

		for (PrototypeSpecificationDomain prototypeSpecificationDomain : prototypeSpecificationDomainList){
			List<PrototypeSpecificationOptionDomain> prototypeSpecificationOptionList =
					prototypeSpecificationOptionService.getListBySpecificationId(prototypeSpecificationDomain.getId());
			prototypeSpecificationDomain.setPrototypeSpecificationOptionList(prototypeSpecificationOptionList);
		}
		goodsDomain.setSpecificationList(prototypeSpecificationDomainList);
	}

}
