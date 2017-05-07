package com.dookay.coral.shop.goods.service.impl;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.domain.GoodsItemDomain;
import com.dookay.coral.shop.goods.query.GoodsQuery;
import com.dookay.coral.shop.goods.query.SkuQuery;
import com.dookay.coral.shop.goods.service.IGoodsItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.goods.mapper.SkuMapper;
import com.dookay.coral.shop.goods.domain.SkuDomain;
import com.dookay.coral.shop.goods.service.ISkuService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 商品sku的业务实现类
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("skuService")
public class SkuServiceImpl extends BaseServiceImpl<SkuDomain> implements ISkuService {
	
	@Autowired
	private SkuMapper skuMapper;

	@Autowired
	private IGoodsItemService goodsItemService;
	@Override
	public List<SkuDomain> getSkuByGoodsId(Long id) {
		SkuQuery query = new SkuQuery();
		query.setGoodsId(id);
		return getList(query);
	}


	public void withGoodsItem(PageList<SkuDomain> skuDomainList){
		this.withGoodsItem(skuDomainList.getList());
	}

	public void withGoodsItem(List<SkuDomain> skuDomainList){
		List<Long> ids = skuDomainList.stream().map(SkuDomain::getGoodsId).collect(Collectors.toList());
		GoodsQuery query = new GoodsQuery();
		query.setIds(ids);
		List<GoodsItemDomain> goodsDomainList = goodsItemService.getList(query);
		for (SkuDomain skuDomain:skuDomainList){
			GoodsItemDomain goodsDomain = goodsDomainList.stream()
					.filter(x-> Objects.equals(x.getId(), skuDomain.getItemId())).findFirst().orElse(null);
			skuDomain.setGoodsItem(goodsDomain);
		}
	}

	public void  withGoodsItem(SkuDomain skuDomain){
		GoodsItemDomain goodsDomain = goodsItemService.get(skuDomain.getItemId());
		skuDomain.setGoodsItem(goodsDomain);
	}
}
