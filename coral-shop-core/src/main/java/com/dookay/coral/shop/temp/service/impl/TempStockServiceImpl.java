package com.dookay.coral.shop.temp.service.impl;

import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.goods.domain.GoodsItemDomain;
import com.dookay.coral.shop.goods.domain.PrototypeSpecificationOptionDomain;
import com.dookay.coral.shop.goods.service.IGoodsItemService;
import com.dookay.coral.shop.goods.service.IGoodsService;
import com.dookay.coral.shop.goods.service.IPrototypeSpecificationOptionService;
import com.dookay.coral.shop.temp.query.TempStockQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.dookay.coral.shop.temp.mapper.TempStockMapper;
import com.dookay.coral.shop.temp.domain.TempStockDomain;
import com.dookay.coral.shop.temp.service.ITempStockService;

/**
 * 临时库存的业务实现类
 * @author : luxor
 * @since : 2017年06月14日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("tempStockService")
public class TempStockServiceImpl extends BaseServiceImpl<TempStockDomain> implements ITempStockService {
	
	@Autowired
	private TempStockMapper tempStockMapper;
	@Autowired
	private IGoodsService goodsService;
	@Autowired
	private IPrototypeSpecificationOptionService prototypeSpecificationOptionService;
	@Autowired
	private IGoodsItemService goodsItemService;

	@Override
	public Integer getStockBySizeAndColor(String goodsNo, Long colorId, Long sizeId) {

		if(StringUtils.isBlank(goodsNo)){
			throw new ServiceException("商品编号为空");
		}
		if(colorId==null){
			throw new ServiceException("商品颜色ID为空");
		}
		if(sizeId==null){
			throw new ServiceException("商品尺寸ID为空");
		}
		Integer num = 0;
		TempStockQuery query = new TempStockQuery();
		query.setProductNo(goodsNo);
		GoodsItemDomain goodsItemDomain = goodsItemService.get(colorId);
		if(goodsItemDomain==null){
			throw new ServiceException("无此颜色");
		}
		query.setColor(goodsItemDomain.getName());
		PrototypeSpecificationOptionDomain sizeDomain = prototypeSpecificationOptionService.get(sizeId);
		if(sizeDomain==null){
			throw new ServiceException("无此尺寸");
		}
		query.setSize(sizeDomain.getName());
		TempStockDomain tempStockDomain = getFirst(query);
		if(tempStockDomain!=null){
			num = tempStockDomain.getNum();
		}
		return num;
	}
}
