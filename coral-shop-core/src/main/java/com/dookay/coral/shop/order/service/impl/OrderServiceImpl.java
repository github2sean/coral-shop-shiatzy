package com.dookay.coral.shop.order.service.impl;

import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.domain.GoodsItemDomain;
import com.dookay.coral.shop.goods.domain.SkuDomain;
import com.dookay.coral.shop.goods.query.GoodsItemQuery;
import com.dookay.coral.shop.goods.query.GoodsQuery;
import com.dookay.coral.shop.goods.service.IGoodsItemService;
import com.dookay.coral.shop.goods.service.IGoodsService;
import com.dookay.coral.shop.goods.service.ISkuService;
import com.dookay.coral.shop.order.domain.OrderItemDomain;
import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;
import com.dookay.coral.shop.order.enums.OrderStatusEnum;
import com.dookay.coral.shop.order.query.OrderItemQuery;
import com.dookay.coral.shop.order.query.OrderQuery;
import com.dookay.coral.shop.order.service.IOrderItemService;
import com.dookay.coral.shop.promotion.domain.CouponDomain;
import com.dookay.coral.shop.promotion.service.ICouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.order.mapper.OrderMapper;
import com.dookay.coral.shop.order.domain.OrderDomain;
import com.dookay.coral.shop.order.service.IOrderService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
	@Autowired
	private IOrderItemService orderItemService;
	@Autowired
	private ISkuService skuService;
	@Autowired
	private ICouponService couponService;
	@Autowired
	private IGoodsService goodsService;

	@Override
	public void withGoodItme(List<OrderItemDomain> cartList) {
		List<Long> ids = cartList.stream().map(OrderItemDomain::getItemId).collect(Collectors.toList());
		GoodsItemQuery query = new GoodsItemQuery();
		query.setIds(ids);
		List<GoodsItemDomain> goodsItemDomainList = goodsItemService.getList(query);
		for(GoodsItemDomain itemDomain:goodsItemDomainList){
			itemDomain.setGoods(goodsService.get(itemDomain.getGoodsId()));
		}
		for (OrderItemDomain orderItemDomain:cartList){
			GoodsItemDomain goodsItemDomain = goodsItemDomainList.stream()
					.filter(x-> Objects.equals(x.getId(), orderItemDomain.getItemId())).findFirst().orElse(new GoodsItemDomain());
			orderItemDomain.setGoodsItemDomain(goodsItemDomain);
		}
	}

	@Override
	public OrderDomain getOrder(String orderNo) {
		OrderQuery orderQuery = new OrderQuery();
		orderQuery.setOrderNo(orderNo);
		return super.getOne(orderQuery);
	}

	@Override
	public void updateSkuStock(OrderDomain orderDomain) {
		if(orderDomain==null){
			throw new ServiceException("订单为空");
		}
		OrderItemQuery query = new OrderItemQuery();
		query.setOrderId(orderDomain.getId());
		List<OrderItemDomain> orderItemDomainList = orderItemService.getList(query);
		for (OrderItemDomain orderItem :orderItemDomainList){
			SkuDomain skuDomain = skuService.get(orderItem.getSkuId());
			skuDomain.setQuantity(skuDomain.getQuantity()-orderItem.getNum());
			skuService.update(skuDomain);
		}
	}

	@Override
	@Transactional("transactionManager")
	public void updateOrderStatus(OrderDomain orderDomain){
		orderDomain.setPaidTime(new Date());
		orderDomain.setStatus(OrderStatusEnum.PAID.getValue());
		//优惠券次数减少
		if(orderDomain.getCouponId()!=null){
			CouponDomain couponDomain = couponService.get(orderDomain.getCouponId());
			switch (couponDomain.getRuleType()) {
				case 0://全单打折 无限次
					couponService.checkCoupon(couponDomain.getCode());
					System.out.println(0);
					break;
				case 1://全单满减 无限次
					couponService.checkCoupon(couponDomain.getCode());
					System.out.println(1);
					break;
				case 2://抵扣券 1次
					couponService.checkCoupon(couponDomain.getCode());
					couponDomain.setIsValid(0);
					couponDomain.setLeftTimes(couponDomain.getLeftTimes()-1);
					System.out.println(2);
					break;
				case 3://折扣券 1次
					couponService.checkCoupon(couponDomain.getCode());
					couponDomain.setIsValid(0);
					couponDomain.setLeftTimes(couponDomain.getLeftTimes()-1);
					System.out.println(3);
					break;
			}
			couponService.update(couponDomain);
		}
		//商品库存减少
		updateSkuStock(orderDomain);
		super.update(orderDomain);
	}
}
