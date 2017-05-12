package com.dookay.coral.shop.order.service.impl;

import com.dookay.coral.shop.goods.domain.GoodsItemDomain;
import com.dookay.coral.shop.goods.query.GoodsItemQuery;
import com.dookay.coral.shop.goods.service.IGoodsItemService;
import com.dookay.coral.shop.order.domain.OrderItemDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.order.mapper.ReturnRequestItemMapper;
import com.dookay.coral.shop.order.domain.ReturnRequestItemDomain;
import com.dookay.coral.shop.order.service.IReturnRequestItemService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 订单退货申请明细的业务实现类
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("returnRequestItemService")
public class ReturnRequestItemServiceImpl extends BaseServiceImpl<ReturnRequestItemDomain> implements IReturnRequestItemService {
	
	@Autowired
	private ReturnRequestItemMapper returnRequestItemMapper;
	@Autowired
	private IGoodsItemService goodsItemService;

	@Override
	public void withGoodsItem(List<ReturnRequestItemDomain> returnOrderItemList) {
		List<Long> ids = returnOrderItemList.stream().map(ReturnRequestItemDomain::getItemId).collect(Collectors.toList());
		GoodsItemQuery query = new GoodsItemQuery();
		query.setIds(ids);
		List<GoodsItemDomain> returnRequestItemDomainList = goodsItemService.getList(query);
		for (ReturnRequestItemDomain returnRequestItemDomain:returnOrderItemList){
			GoodsItemDomain goodsItemDomain = returnRequestItemDomainList.stream()
					.filter(x-> Objects.equals(x.getId(), returnRequestItemDomain.getItemId())).findFirst().orElse(new GoodsItemDomain());
			returnRequestItemDomain.setGoodsItemDomain(goodsItemDomain);
		}
	}
}
