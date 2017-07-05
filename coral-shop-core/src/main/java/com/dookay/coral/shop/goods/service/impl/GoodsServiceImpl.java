package com.dookay.coral.shop.goods.service.impl;

import com.dookay.coral.common.enums.ValidEnum;
import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.shop.goods.domain.*;
import com.dookay.coral.shop.goods.query.*;
import com.dookay.coral.shop.goods.service.*;
import com.dookay.coral.shop.temp.domain.TempStockDomain;
import com.dookay.coral.shop.temp.query.TempMemberQuery;
import com.dookay.coral.shop.temp.query.TempStockQuery;
import com.dookay.coral.shop.temp.service.ITempStockService;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.goods.mapper.GoodsMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
	@Autowired
	private IGoodsItemService goodsItemService;
	@Autowired
	private ISkuService skuService;
	@Autowired
	private ITempStockService tempStockService;
	@Autowired
	private IGoodsColorService goodsColorService;
	@Autowired
	private IGoodsCategoryService goodsCategoryService;

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

	@Override
	public void updateColors(GoodsDomain goodsDomain) {
		GoodsItemQuery goodsItemQuery = new GoodsItemQuery();
		goodsItemQuery.setGoodsId(goodsDomain.getId());
		goodsItemQuery.setIsValid(ValidEnum.YES.getValue());
		List<GoodsItemDomain> goodsItemDomainList = goodsItemService.getList(goodsItemQuery);
		List<Long> colorIds = goodsItemDomainList.stream().map(x->x.getColorId()).collect(Collectors.toList());
		goodsDomain.setColorIds(JsonUtils.toJSONString(colorIds));
		super.update(goodsDomain);
	}

	@Override
	public void updateSizes(GoodsDomain goodsDomain) {
		SkuQuery query = new SkuQuery();
		query.setGoodsId(goodsDomain.getId());
		query.setIsValid(ValidEnum.YES.getValue());
		List<SkuDomain> skuDomainList = skuService.getList(query);
		List<Long> sizeIds = new ArrayList<>();
		for (SkuDomain skuDomain :skuDomainList){
			JSONObject jasonObject = JSONObject.fromObject(skuDomain.getSpecifications());
			sizeIds.add(jasonObject.getLong("size"));
		}
		goodsDomain.setSizeIds(JsonUtils.toJSONString(sizeIds));
		super.update(goodsDomain);
	}

	@Override
	public void withGoodsItemList(List<GoodsDomain> goodsDomainList) {
		List<Long> ids = goodsDomainList.stream().map(GoodsDomain::getId).collect(Collectors.toList());
		GoodsItemQuery query = new GoodsItemQuery();
		query.setGoodsIds(ids);
		List<GoodsItemDomain> goodsItemDomainList = goodsItemService.getList(query);
		for (GoodsDomain goodsDomain:goodsDomainList){
			List<GoodsItemDomain> goodsItemDomainList1 = goodsItemDomainList.stream()
					.filter(x-> Objects.equals(x.getGoodsId(), goodsDomain.getId())).collect(Collectors.toList());
			goodsDomain.setGoodsItemList(goodsItemDomainList1);
		}
	}

	@Override
	public void withGoodsItemList(List<GoodsDomain> goodsDomainList,Integer onsale) {
		List<Long> ids = goodsDomainList.stream().map(GoodsDomain::getId).collect(Collectors.toList());
		GoodsItemQuery query = new GoodsItemQuery();
		query.setGoodsIds(ids);
		query.setIsSale(onsale);
		List<GoodsItemDomain> goodsItemDomainList = goodsItemService.getList(query);
		for (GoodsDomain goodsDomain:goodsDomainList){
			List<GoodsItemDomain> goodsItemDomainList1 = goodsItemDomainList.stream()
					.filter(x-> Objects.equals(x.getGoodsId(), goodsDomain.getId())).collect(Collectors.toList());
			goodsDomain.setGoodsItemList(goodsItemDomainList1);
		}
	}

	@Override
	public void withGoodsItemList(GoodsDomain goodsDomain) {
		GoodsItemQuery query = new GoodsItemQuery();
		query.setGoodsId(goodsDomain.getId());
		List<GoodsItemDomain> goodsItemDomainList = goodsItemService.getList(query);
		goodsDomain.setGoodsItemList(goodsItemDomainList);
	}

	@Override
	public void withGoodsItemListAndQuantity(GoodsDomain goodsDomain, Long sizeId) {
		GoodsItemQuery query = new GoodsItemQuery();
		query.setGoodsId(goodsDomain.getId());
		List<GoodsItemDomain> goodsItemDomainList = goodsItemService.getList(query);
		for(GoodsItemDomain line:goodsItemDomainList){
			SkuQuery skuQuery = new SkuQuery();
			skuQuery.setItemId(line.getId());
			skuQuery.setIsValid(ValidEnum.YES.getValue());

			List<SkuDomain> skuDomainList = skuService.getList(skuQuery);
			System.out.println("skuDomainList"+JsonUtils.toJSONString(skuDomainList));
			SkuDomain skuDomain =  skuDomainList.stream().filter(x-> JSONObject.fromObject(x.getSpecifications()).getLong("size")==sizeId).findFirst().orElse(null);
			if(skuDomain == null)
			{
				throw new ServiceException("此商品无库存");
			}
			int quantity = skuDomain.getQuantity();
			System.out.println("库存："+quantity);
			line.setQuantity(quantity);
		}
		goodsDomain.setGoodsItemList(goodsItemDomainList);
	}

	@Override
	public void withSizeDomain(List<GoodsDomain> goodsList) {
			for(GoodsDomain goods:goodsList){
				List<Long> sizeIds = JsonUtils.toLongArray(goods.getSizeIds());
				PrototypeSpecificationOptionQuery prototypeSpecificationOptionQuery = new PrototypeSpecificationOptionQuery();
				prototypeSpecificationOptionQuery.setIds(sizeIds);
				List<PrototypeSpecificationOptionDomain> sizeList = prototypeSpecificationOptionService.getList(prototypeSpecificationOptionQuery);
				goods.setSizeDomainList(sizeList);
				System.out.println("goodId:"+goods.getId()+" sizeList:"+sizeList);
			}
	}

	@Override
	public Long getTempStock(String goodsNo, String sizeValue, Long colorId) {
		TempStockQuery query = new TempStockQuery();
		query.setProductNo(goodsNo);
		query.setSize(sizeValue);
		query.setColor(goodsColorService.get(colorId).getName());
		TempStockDomain tempStockDomain = tempStockService.getFirst(query);
		return tempStockDomain==null?0L:Long.parseLong(tempStockDomain.getNum()+"");
	}

	@Override
	public Long getTempStock(String productNo, String color, String size) {
		TempStockQuery query = new TempStockQuery();
		query.setProductNo(productNo);
		query.setSize(size);
		query.setColor(color);
		TempStockDomain tempStockDomain = tempStockService.getFirst(query);
		return tempStockDomain==null?0L:Long.parseLong(tempStockDomain.getNum()+"");
	}

	@Override
	public void colorWithStock( List<GoodsColorDomain> goodsColorDomainList,List<Long> goodsId, List<Long> parmaId) {

		GoodsItemQuery itemQuery = new GoodsItemQuery();
		itemQuery.setGoodsIds(parmaId);
		itemQuery.setColorIds(parmaId);
		List<GoodsItemDomain> itemDomainList = goodsItemService.getList(itemQuery);

		List<Long> itemIds = new ArrayList<>();
		for(GoodsItemDomain goodsItemDomain :itemDomainList){
			itemIds.add(goodsItemDomain.getId());
		}

		SkuQuery query = new SkuQuery();
		query.setGoodsIds(goodsId);
		query.setItemIds(itemIds);
		List<SkuDomain> skuDomainList = skuService.getList(query);
		for(SkuDomain skuDomain:skuDomainList){

			//skuDomain.getItemId()==
		}

	}

	@Override
	public void sizeWithStock(List<PrototypeSpecificationOptionDomain> sizeDomainList,List<Long> goodsId, List<Long> parmaId) {

		SkuQuery query = new SkuQuery();
		query.setGoodsIds(goodsId);
		List<SkuDomain> skuDomainList = skuService.getList(query);
		for(SkuDomain skuDomain:skuDomainList){
			JSONObject jsonObject = JSONObject.fromObject(skuDomain.getSpecifications());
			Long sizeId = jsonObject.getLong("size");
			for(PrototypeSpecificationOptionDomain line:sizeDomainList){
				if(line.getId().equals(sizeId)){
					line.setStock(Long.parseLong(skuDomain.getQuantity()+""));
				}
			}
		}

	}

	@Override
	public List<GoodsCategoryDomain> getAll2Category(List<GoodsDomain> goodsList) {

		List<Long> categoryIds = new ArrayList<>();
		for (GoodsDomain line:goodsList){
			categoryIds.add(line.getCategoryId());
		}
		GoodsCategoryQuery categoryQuery = new GoodsCategoryQuery();
		categoryQuery.setIds(categoryIds);
		return goodsCategoryService.getList(categoryQuery);
	}

}
