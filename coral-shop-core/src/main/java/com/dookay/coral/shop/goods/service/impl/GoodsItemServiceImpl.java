package com.dookay.coral.shop.goods.service.impl;

import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.persistence.Query;
import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.common.utils.ExcelUtils;
import com.dookay.coral.shop.goods.domain.*;
import com.dookay.coral.shop.goods.model.CreateGoodModel;
import com.dookay.coral.shop.goods.query.*;
import com.dookay.coral.shop.goods.service.*;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.shop.goods.mapper.GoodsItemMapper;
import com.dookay.coral.shop.goods.domain.GoodsItemDomain;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 商品项目的业务实现类
 * @author : luxor
 * @since : 2017年05月06日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("goodsItemService")
public class GoodsItemServiceImpl extends BaseServiceImpl<GoodsItemDomain> implements IGoodsItemService {
	
	@Autowired
	private GoodsItemMapper goodsItemMapper;

	@Autowired
	private IGoodsService goodsService;
	@Autowired
	private IGoodsColorService goodsColorService;


	public void withGoods(PageList<GoodsItemDomain> goodsItemDomainList){
		this.withGoods(goodsItemDomainList.getList());
	}

	public void withGoods(List<GoodsItemDomain> goodsItemDomainList){
		List<Long> ids = goodsItemDomainList.stream().map(GoodsItemDomain::getGoodsId).collect(Collectors.toList());
		GoodsQuery query = new GoodsQuery();
		query.setIds(ids);
		List<GoodsDomain> goodsDomainList = goodsService.getList(query);
		for (GoodsItemDomain goodsItemDomain:goodsItemDomainList){
			GoodsDomain goodsDomain = goodsDomainList.stream()
					.filter(x-> Objects.equals(x.getId(), goodsItemDomain.getGoodsId())).findFirst().orElse(null);
			goodsItemDomain.setGoods(goodsDomain);
		}
	}

	public void  withGoods(GoodsItemDomain goodsItemDomain){
		GoodsDomain goodsDomain = goodsService.get(goodsItemDomain.getGoodsId());
		goodsItemDomain.setGoods(goodsDomain);
	}

	public void  withColor(GoodsItemDomain goodsItemDomain){
		GoodsColorDomain goodsColorDomain = goodsColorService.get(goodsItemDomain.getColorId());
		goodsItemDomain.setGoodsColor(goodsColorDomain);
		goodsItemDomain.setColorValue(goodsColorDomain.getColor());
	}

	@Override
	public void withColor(List<GoodsItemDomain> goodsItemDomainList) {
		List<Long> ids = goodsItemDomainList.stream().map(GoodsItemDomain::getColorId).collect(Collectors.toList());
		GoodsColorQuery query = new GoodsColorQuery();
		query.setIds(ids);
		List<GoodsColorDomain> goodsColorDomainList = goodsColorService.getList(query);
		for (GoodsItemDomain goodsItemDomain:goodsItemDomainList){
			GoodsColorDomain goodsDomain = goodsColorDomainList.stream()
					.filter(x-> Objects.equals(x.getId(), goodsItemDomain.getColorId())).findFirst().orElse(null);
			goodsItemDomain.setGoodsColor(goodsDomain);
		}
	}

	@Override
	public void updateColors(GoodsItemDomain domain) {
		GoodsDomain goodsDomain = new GoodsDomain();
		goodsDomain.setId(domain.getGoodsId());
		goodsService.updateColors(goodsDomain);
	}

}
