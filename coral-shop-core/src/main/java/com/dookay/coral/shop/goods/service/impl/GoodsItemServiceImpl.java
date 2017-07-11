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
	@Autowired
	private IPrototypeSpecificationOptionService prototypeSpecificationOptionService;
	@Autowired
	private IPrototypeSpecificationService prototypeSpecificationService;
	@Autowired
	private ISkuService skuService;



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

	public Boolean isExistGoodItem(GoodsItemQuery query){
		return getFirst(query)!=null?true:false;
	}
	public Boolean isExistGoods(GoodsQuery query){
		return goodsService.getFirst(query)!=null?true:false;
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
				GoodsDomain goodsDomain = goodsService.getFirst(goodsQuery);
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

					goodsService.create(goodsDomain);
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

					super.create(goodsItemDomain);

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
