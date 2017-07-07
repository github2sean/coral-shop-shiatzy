package com.dookay.coral.shop.order.service.impl;

import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.common.web.HttpContext;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.domain.GoodsItemDomain;
import com.dookay.coral.shop.goods.domain.SkuDomain;
import com.dookay.coral.shop.goods.query.GoodsItemQuery;
import com.dookay.coral.shop.goods.query.GoodsQuery;
import com.dookay.coral.shop.goods.service.IGoodsItemService;
import com.dookay.coral.shop.goods.service.IGoodsService;
import com.dookay.coral.shop.goods.service.IPrototypeSpecificationOptionService;
import com.dookay.coral.shop.goods.service.ISkuService;
import com.dookay.coral.shop.order.domain.OrderItemDomain;
import com.dookay.coral.shop.order.domain.ReturnRequestItemDomain;
import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;
import com.dookay.coral.shop.order.enums.OrderStatusEnum;
import com.dookay.coral.shop.order.query.OrderItemQuery;
import com.dookay.coral.shop.order.query.OrderQuery;
import com.dookay.coral.shop.order.service.IOrderItemService;
import com.dookay.coral.shop.promotion.domain.CouponDomain;
import com.dookay.coral.shop.promotion.service.ICouponService;
import com.dookay.coral.shop.temp.domain.TempStockDomain;
import com.dookay.coral.shop.temp.query.TempStockQuery;
import com.dookay.coral.shop.temp.service.ITempStockService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.order.mapper.OrderMapper;
import com.dookay.coral.shop.order.domain.OrderDomain;
import com.dookay.coral.shop.order.service.IOrderService;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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
	@Autowired
	private ITempStockService tempStockService;
	@Autowired
	private IPrototypeSpecificationOptionService prototypeSpecificationOptionService;

	@Override
	public void withGoodItme(List<OrderItemDomain> cartList) {
		List<Long> ids = cartList.stream().map(OrderItemDomain::getItemId).collect(Collectors.toList());
		GoodsItemQuery query = new GoodsItemQuery();
		query.setIds(ids);
		List<GoodsItemDomain> goodsItemDomainList = goodsItemService.getList(query);
		HttpServletRequest request =  HttpContext.current().getRequest();
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
		for(GoodsItemDomain itemDomain:goodsItemDomainList){
			itemDomain.setGoods(goodsService.get(itemDomain.getGoodsId()));
			itemDomain.setPicUrl(basePath+ JSONObject.fromObject(JSONArray.fromObject(itemDomain.getThumb()).get(0)).getString("file"));
		}
		for (OrderItemDomain orderItemDomain:cartList){
			GoodsItemDomain goodsItemDomain = goodsItemDomainList.stream()
					.filter(x-> Objects.equals(x.getId(), orderItemDomain.getItemId())).findFirst().orElse(new GoodsItemDomain());
			orderItemDomain.setGoodsItemDomain(goodsItemDomain);
			orderItemDomain.setSizeDomain(prototypeSpecificationOptionService.get(JSONObject.fromObject(orderItemDomain.getSkuSpecifications()).getLong("size")));
		}
	}

	@Override
	public void returnWithGoodItem(List<ReturnRequestItemDomain> requestItemDomainList) {

		List<Long> ids = requestItemDomainList.stream().map(ReturnRequestItemDomain::getItemId).collect(Collectors.toList());
		GoodsItemQuery query = new GoodsItemQuery();
		query.setIds(ids);
		List<GoodsItemDomain> goodsItemDomainList = goodsItemService.getList(query);
		HttpServletRequest request =  HttpContext.current().getRequest();
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
		for(GoodsItemDomain itemDomain:goodsItemDomainList){
			itemDomain.setGoods(goodsService.get(itemDomain.getGoodsId()));
			itemDomain.setPicUrl(basePath+ JSONObject.fromObject(JSONArray.fromObject(itemDomain.getThumb()).get(0)).getString("file"));
		}
		for (ReturnRequestItemDomain returnRequestItemDomain:requestItemDomainList){
			GoodsItemDomain goodsItemDomain = goodsItemDomainList.stream()
					.filter(x-> Objects.equals(x.getId(), returnRequestItemDomain.getItemId())).findFirst().orElse(new GoodsItemDomain());
			returnRequestItemDomain.setGoodsItemDomain(goodsItemDomain);
		}
	}

	@Override
	public OrderDomain getOrder(String orderNo) {
		OrderQuery orderQuery = new OrderQuery();
		orderQuery.setOrderNo(orderNo);
		return super.getOne(orderQuery);
	}

	@Override
	@Transactional("transactionManager")
	public void updateSkuStock(OrderDomain orderDomain) {
		if(orderDomain==null){
			throw new ServiceException("订单为空");
		}
		OrderItemQuery query = new OrderItemQuery();
		query.setOrderId(orderDomain.getId());
		List<OrderItemDomain> orderItemDomainList = orderItemService.getList(query);
		for (OrderItemDomain orderItem :orderItemDomainList){
			//sku中库存减少
			SkuDomain skuDomain = skuService.get(orderItem.getSkuId());
			skuDomain.setQuantity(skuDomain.getQuantity()-orderItem.getNum());
			skuService.update(skuDomain);
			//temp_stock中库存减少
			TempStockQuery tempStockQuery = new TempStockQuery();
			tempStockQuery.setProductNo(orderItem.getGoodsCode());


			tempStockQuery.setSize(prototypeSpecificationOptionService.get(JSONObject.fromObject(orderItem.getSkuSpecifications()).getLong("size")).getName());
			tempStockQuery.setColor(goodsItemService.get(orderItem.getItemId()).getName());
			TempStockDomain tempStockDomain = tempStockService.getFirst(tempStockQuery);
			if(tempStockDomain!=null){
				Integer stock = tempStockDomain.getNum();
				if(stock>0){
					tempStockDomain.setNum(stock-1);
					tempStockService.update(tempStockDomain);
				}
			}
		}
	}

	@Override
	@Transactional("transactionManager")
	public void updateOrderStatus(OrderDomain orderDomain){
		orderDomain.setPaidTime(new Date());
		orderDomain.setStatus(OrderStatusEnum.PAID.getValue());

		//优惠券数量减少
		subCouponNum(orderDomain);
		//商品库存减少
		updateSkuStock(orderDomain);

		super.update(orderDomain);
	}

	@Override
	public void subCouponNum(OrderDomain orderDomain){
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
	}

	@Override
	public List<OrderDomain> getUnpaidOrder() {
		OrderQuery query = new OrderQuery();
		query.setStatus(OrderStatusEnum.UNPAID.getValue());
		query.setCod(4);
		return getList(query);
	}
}
