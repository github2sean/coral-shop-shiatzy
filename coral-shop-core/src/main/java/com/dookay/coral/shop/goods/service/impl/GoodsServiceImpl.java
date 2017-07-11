package com.dookay.coral.shop.goods.service.impl;

import com.dookay.coral.common.enums.ValidEnum;
import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.persistence.criteria.QueryCriteria;
import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.common.utils.ExcelUtils;
import com.dookay.coral.shop.goods.domain.*;
import com.dookay.coral.shop.goods.model.CreateGoodModel;
import com.dookay.coral.shop.goods.query.*;
import com.dookay.coral.shop.goods.service.*;
import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;
import com.dookay.coral.shop.temp.domain.TempStockDomain;
import com.dookay.coral.shop.temp.query.TempMemberQuery;
import com.dookay.coral.shop.temp.query.TempStockQuery;
import com.dookay.coral.shop.temp.service.ITempStockService;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.goods.mapper.GoodsMapper;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collector;
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
		goodsQuery.setIsPublished(ValidEnum.YES.getValue());
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
		goodsDomain.setSizeIds(JsonUtils.toJSONString(sizeIds.stream().distinct().collect(Collectors.toList())));
		super.update(goodsDomain);
	}

	@Override
	public void withGoodsItemList(List<GoodsDomain> goodsDomainList) {
		List<Long> ids = goodsDomainList.stream().map(GoodsDomain::getId).collect(Collectors.toList());
		GoodsItemQuery query = new GoodsItemQuery();
		query.setIsValid(ValidEnum.YES.getValue());
		query.setGoodsIds(ids);
		List<GoodsItemDomain> goodsItemDomainList = goodsItemService.getList(query);
		for (GoodsDomain goodsDomain:goodsDomainList){
			List<GoodsItemDomain> goodsItemDomainList1 = goodsItemDomainList.stream()
					.filter(x-> Objects.equals(x.getGoodsId(), goodsDomain.getId())).collect(Collectors.toList());
			goodsDomain.setGoodsItemList(goodsItemDomainList1.stream().sorted(Comparator.comparing(GoodsItemDomain::getRank)).collect(Collectors.toList()));
		}
	}

	@Override
	public void withGoodsItemList(List<GoodsDomain> goodsDomainList,Integer onsale) {
		List<Long> ids = goodsDomainList.stream().map(GoodsDomain::getId).collect(Collectors.toList());
		GoodsItemQuery query = new GoodsItemQuery();
		query.setGoodsIds(ids);
		query.setIsSale(onsale);
		query.setIsValid(ValidEnum.YES.getValue());
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
		query.setIsValid(ValidEnum.YES.getValue());
		query.setGoodsId(goodsDomain.getId());
		List<GoodsItemDomain> goodsItemDomainList = goodsItemService.getList(query);
		goodsDomain.setGoodsItemList(goodsItemDomainList);
	}

	@Override
	public void withGoodsItemListAndQuantity(GoodsDomain goodsDomain, Long sizeId) {
		GoodsItemQuery query = new GoodsItemQuery();
		query.setIsValid(ValidEnum.YES.getValue());
		query.setGoodsId(goodsDomain.getId());
		List<GoodsItemDomain> goodsItemDomainList = goodsItemService.getList(query);
		for(GoodsItemDomain line:goodsItemDomainList){
			TempStockQuery query2 = new TempStockQuery();
			query2.setProductNo(line.getGoodsNo().split("\\s+")[0]);
			query2.setSize(line.getGoodsNo().split("\\s+")[1]);
			query2.setColor(line.getName());
			TempStockDomain tempStockDomain = tempStockService.getFirst(query2);

			if(tempStockDomain == null) {
				line.setQuantity(0);
			}else {
				int quantity = tempStockDomain.getNum();
				System.out.println("库存："+quantity);
				line.setQuantity(quantity);
			}
		}
		goodsDomain.setGoodsItemList(goodsItemDomainList);
	}

	@Override
	public void withGoodsItemListAndQuantityByColor(GoodsDomain goodsDomain,List<Long> sizeIds) {
		GoodsItemQuery query = new GoodsItemQuery();
		query.setGoodsId(goodsDomain.getId());
		query.setIsValid(ValidEnum.YES.getValue());
		List<GoodsItemDomain> goodsItemDomainList = goodsItemService.getList(query);

		for(GoodsItemDomain line:goodsItemDomainList){
			line.setSizeDomains(withStock(sizeIds,line));
		}
		goodsDomain.setGoodsItemList(goodsItemDomainList);
	}


	public List<PrototypeSpecificationOptionDomain> withStock(List<Long> sizeIds,GoodsItemDomain line){
		List<PrototypeSpecificationOptionDomain> ret  = new ArrayList<>();
		TempStockQuery query2 = new TempStockQuery();
		for(Long id:sizeIds){
			PrototypeSpecificationOptionDomain sizeDomain = prototypeSpecificationOptionService.get(id);
			query2.setProductNo(line.getGoodsNo().split("\\s+")[0]);
			query2.setSize(sizeDomain.getName());
			query2.setColor(line.getGoodsNo().split("\\s+")[1]);
			TempStockDomain tempStockDomain = tempStockService.getFirst(query2);
			System.out.println("query2:"+query2+" tempStockDomain:"+tempStockDomain);
			if(tempStockDomain == null) {
				sizeDomain.setStock(0L);
			}else {
				int quantity = tempStockDomain.getNum();
				System.out.println("库存："+quantity);
				sizeDomain.setStock(Long.parseLong(quantity+""));
			}
			ret.add(sizeDomain);
		}
		return ret;
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
		itemQuery.setIsValid(ValidEnum.YES.getValue());
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
		categoryQuery.setIsValid(ValidEnum.YES.getValue());
		categoryQuery.setIds(categoryIds);
		return goodsCategoryService.getList(categoryQuery);
	}

	public Boolean isExistGoodItem(GoodsItemQuery query){
		return goodsItemService.getFirst(query)!=null?true:false;
	}
	public Boolean isExistGoods(GoodsQuery query){
		return getFirst(query)!=null?true:false;
	}

	@Override
	@Transactional("transactionManager")
	public void importGoods(String fileName) {
		try {
			File file = new File(fileName);
			if(!file.exists()){
				throw new ServiceException("未读取到文件");
			}
			List<CreateGoodModel> modelList = ExcelUtils.importExcel(file,CreateGoodModel.class);
			Date createDate = new Date();
			for(CreateGoodModel row:modelList){
				GoodsQuery goodsQuery = new GoodsQuery();
				goodsQuery.setEqualName(row.getName());
				GoodsDomain goodsDomain = getFirst(goodsQuery);
				//不存在则创建商品
				if(goodsDomain==null){
					//根据原型和尺寸名查询出具体size
					List<String> sizeNames =  JsonUtils.toStringArray(row.getSizeIds());
					PrototypeSpecificationQuery specificationQuery = new PrototypeSpecificationQuery();
					specificationQuery.setPrototypeId(row.getPrototypeId());
					PrototypeSpecificationDomain specificationDomain =  prototypeSpecificationService.getFirst(specificationQuery);
					if(specificationDomain==null){
						continue;
					}
					PrototypeSpecificationOptionQuery optionQuery = new PrototypeSpecificationOptionQuery();
					optionQuery.setSpecificationId(specificationDomain.getId());
					optionQuery.setNames(sizeNames);
					List<PrototypeSpecificationOptionDomain> sizeDomainList = prototypeSpecificationOptionService.getList(optionQuery);
					List<Long> optionIds = sizeDomainList.stream().distinct().map(PrototypeSpecificationOptionDomain::getId).collect(Collectors.toList());

					goodsDomain = new GoodsDomain();
					goodsDomain.setName(row.getName());
					goodsDomain.setEnName(row.getEnName());
					goodsDomain.setPrice(row.getPrice());
					goodsDomain.setDisPrice(row.getDiscountPrice());
					goodsDomain.setCategoryIds(row.getCategoryIds());
					goodsDomain.setSizeIds(optionIds.toString());
					goodsDomain.setDetails(row.getDescription());
					goodsDomain.setEnDetails(row.getEnDescription());
					goodsDomain.setCode(row.getGoodsNo().split("\\t+")[0]);
					goodsDomain.setCreateTime(createDate);

					create(goodsDomain);
				}

				GoodsItemQuery itemQuery = new GoodsItemQuery();
				itemQuery.setGoodsId(goodsDomain.getId());
				itemQuery.setGoodsNo(row.getGoodsNo());
				//不存在则创建商品item
				if(isExistGoodItem(itemQuery)){
					GoodsItemDomain goodsItemDomain = new GoodsItemDomain();
					goodsItemDomain.setGoodsId(goodsDomain.getId());
					goodsItemDomain.setColorId(row.getColorId());
					goodsItemDomain.setFormat(row.getFormat());
					goodsItemDomain.setEnFormat(row.getEnFormat());
					goodsItemDomain.setPrice(row.getPrice());
					goodsItemDomain.setDiscountPrice(row.getDiscountPrice());
					goodsItemDomain.setGoodsNo(row.getGoodsNo());
					goodsItemDomain.setCreateTime(createDate);

					goodsItemService.create(goodsItemDomain);

					//创建sku
					List<Long> sizeIds = JsonUtils.toLongArray(goodsDomain.getSizeIds());
					for(Long id:sizeIds){
						SkuDomain sku = new SkuDomain();
						sku.setGoodsId(goodsDomain.getId());
						sku.setGoodsNo(row.getGoodsNo());
						sku.setItemId(goodsItemDomain.getId());
						PrototypeSpecificationOptionDomain sizeDomain = prototypeSpecificationOptionService.get(id);
						sku.setSize(sizeDomain.getName());
						sku.setSpecifications("{\"size\":"+id+"}");
						sku.setIsValid(1);
						sku.setIsPre(1);
						sku.setCreateTime(createDate);
						skuService.create(sku);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
