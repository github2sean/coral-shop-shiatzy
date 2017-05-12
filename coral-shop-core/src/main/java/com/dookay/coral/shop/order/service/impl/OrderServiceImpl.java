package com.dookay.coral.shop.order.service.impl;

import com.dookay.coral.shop.goods.domain.GoodsItemDomain;
import com.dookay.coral.shop.goods.query.GoodsItemQuery;
import com.dookay.coral.shop.goods.service.IGoodsItemService;
import com.dookay.coral.shop.order.domain.OrderItemDomain;
import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.order.mapper.OrderMapper;
import com.dookay.coral.shop.order.domain.OrderDomain;
import com.dookay.coral.shop.order.service.IOrderService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 商品sku规格值的业务实现类
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("orderService")
public class OrderServiceImpl extends BaseServiceImpl<OrderDomain> implements IOrderService {
	
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private IGoodsItemService goodsItemService;

	@Override
	public void withGoodItme(List<OrderItemDomain> cartList) {
		List<Long> ids = cartList.stream().map(OrderItemDomain::getItemId).collect(Collectors.toList());
		GoodsItemQuery query = new GoodsItemQuery();
		query.setIds(ids);
		List<GoodsItemDomain> goodsItemDomainList = goodsItemService.getList(query);
		for (OrderItemDomain orderItemDomain:cartList){
			GoodsItemDomain goodsItemDomain = goodsItemDomainList.stream()
					.filter(x-> Objects.equals(x.getId(), orderItemDomain.getItemId())).findFirst().orElse(new GoodsItemDomain());
			orderItemDomain.setGoodsItemDomain(goodsItemDomain);
		}
	}
}
