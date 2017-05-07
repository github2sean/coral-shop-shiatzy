package com.dookay.coral.shop.goods.service.impl;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.goods.domain.GoodsColorDomain;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.domain.GoodsItemDomain;
import com.dookay.coral.shop.goods.query.GoodsQuery;
import com.dookay.coral.shop.goods.service.IGoodsColorService;
import com.dookay.coral.shop.goods.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.shop.goods.mapper.GoodsItemMapper;
import com.dookay.coral.shop.goods.domain.GoodsItemDomain;
import com.dookay.coral.shop.goods.service.IGoodsItemService;

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
}
