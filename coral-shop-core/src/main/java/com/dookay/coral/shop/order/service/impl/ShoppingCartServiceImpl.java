package com.dookay.coral.shop.order.service.impl;

import com.dookay.coral.common.enums.ValidEnum;
import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.utils.RandomUtils;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.domain.GoodsItemDomain;
import com.dookay.coral.shop.goods.domain.SkuDomain;
import com.dookay.coral.shop.goods.query.GoodsItemQuery;
import com.dookay.coral.shop.goods.query.SkuQuery;
import com.dookay.coral.shop.goods.service.IGoodsItemService;
import com.dookay.coral.shop.goods.service.IGoodsService;
import com.dookay.coral.shop.goods.service.IPrototypeSpecificationOptionService;
import com.dookay.coral.shop.goods.service.ISkuService;
import com.dookay.coral.shop.order.domain.OrderDomain;
import com.dookay.coral.shop.order.domain.OrderItemDomain;
import com.dookay.coral.shop.order.domain.ReservationItemDomain;
import com.dookay.coral.shop.order.enums.ShoppingCartTypeEnum;
import com.dookay.coral.shop.order.query.OrderQuery;
import com.dookay.coral.shop.order.query.ShoppingCartItemQuery;
import com.dookay.coral.shop.order.service.IOrderItemService;
import com.dookay.coral.shop.order.service.IOrderService;
import net.sf.json.JSONObject;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.order.mapper.ShoppingCartItemMapper;
import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;
import com.dookay.coral.shop.order.service.IShoppingCartService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 购物车的业务实现类
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("shoppingCartService")
public class ShoppingCartServiceImpl extends BaseServiceImpl<ShoppingCartItemDomain> implements IShoppingCartService {
	
	@Autowired
	private ShoppingCartItemMapper shoppingCartItemMapper;

	@Autowired
	private IGoodsService goodsService;

	@Autowired
	private IOrderService orderService;

	@Autowired
	private IOrderItemService orderItemService;
	@Autowired
	private IGoodsItemService goodsItemService;
	@Autowired
	private  IShoppingCartService shoppingCartService;
	@Autowired
	private IPrototypeSpecificationOptionService prototypeSpecificationOptionService;

	@Autowired
	private ISkuService skuService;
	@Override
	public void removeFromCart(Long id) {
		if(id!=null){
			super.delete(id);
		}else {
			throw new ServiceException("商品ID为空");
		}
	}

	@Override
	public void addToCart(CustomerDomain customerDomain, SkuDomain skuDomain, Integer shoppingCartType, int num) {
		Assert.notNull(customerDomain,"customerDomain");
		Assert.notNull(skuDomain,"skuDomain");

		Long customerId = customerDomain.getId();
		List<ShoppingCartItemDomain>  shoppingCartItemDomainList = this.listShoppingCartItemByCustomerId(customerId,shoppingCartType);
		if(shoppingCartItemDomainList.size()>=8){
			throw new ServiceException(ShoppingCartTypeEnum.valueOf(shoppingCartType).getDescription()+"商品数量不能超过8个");
		}

		ShoppingCartItemDomain existShoppingCartItem =
				shoppingCartItemDomainList.stream().filter(x-> Objects.equals(x.getSkuId(), skuDomain.getId())).findFirst().orElse(null);
		//如果购物车中已经存在，则更新购物车
		if(existShoppingCartItem != null){
			if(customerDomain!=null){
				this.updateShoppingCartItem(customerDomain,existShoppingCartItem.getItemId(),num);
			}
		}else{
			GoodsDomain goodsDomain = goodsService.get(skuDomain.getGoodsId());
			GoodsItemDomain goodsItemDomain = goodsItemService.get(skuDomain.getItemId());
			ShoppingCartItemDomain shoppingCartItemDomain = new ShoppingCartItemDomain();
			shoppingCartItemDomain.setCustomerId(customerDomain.getId());
			shoppingCartItemDomain.setGoodsCode(goodsItemDomain.getGoodsNo());
			shoppingCartItemDomain.setGoodsName(goodsDomain.getName());
			shoppingCartItemDomain.setGoodsEnName(goodsDomain.getEnName());
			shoppingCartItemDomain.setGoodsPrice(goodsItemDomain.getPrice());
			shoppingCartItemDomain.setGoodsDisPrice(goodsDomain.getDisPrice());
			shoppingCartItemDomain.setSkuId(skuDomain.getId());
			shoppingCartItemDomain.setItemId(skuDomain.getItemId());
			shoppingCartItemDomain.setShoppingCartType(shoppingCartType);
			shoppingCartItemDomain.setNum(num);
			shoppingCartItemDomain.setSkuSpecifications(skuDomain.getSpecifications());
			super.create(shoppingCartItemDomain);
		}
	}

	@Override
	public void updateShoppingCartItem(CustomerDomain customerDomain, Long shoppingCartItemId, int num) {
		Long customerId = customerDomain.getId();
		ShoppingCartItemDomain shoppingCartItemDomain = super.get(shoppingCartItemId);
		if(shoppingCartItemDomain!=null){
			if(Objects.equals(shoppingCartItemDomain.getCustomerId(), customerId)){
				if(num>0){
					shoppingCartItemDomain.setNum(num);
					super.update(shoppingCartItemDomain);
				}else{
					super.delete(shoppingCartItemId);
				}
			}
		}
	}

	@Override
	public List<ShoppingCartItemDomain> listShoppingCartItemByCustomerId(Long customerId, Integer shoppingCartType) {
		ShoppingCartItemQuery shoppingCartItemQuery = new ShoppingCartItemQuery();
		shoppingCartItemQuery.setCustomerId(customerId);
		shoppingCartItemQuery.setShoppingCartType(shoppingCartType);
		return super.getList(shoppingCartItemQuery);
	}

	@Override
	public Boolean removeFromWish(CustomerDomain customerDomain, SkuDomain skuDomain) {
		try {
			ShoppingCartItemDomain shoppingCart= isExistInWish(customerDomain,skuDomain);
			if(shoppingCart!=null){
				Long shopCartId = shoppingCart.getId();
				delete(shopCartId);
			}else{
				throw new ServiceException("心愿单中不存在此商品");
			}
		}catch (Exception e){
			return false;
		}
		return true;
	}

	@Override
	public ShoppingCartItemDomain isExistInWish(CustomerDomain customerDomain, SkuDomain skuDomain) {
		ShoppingCartItemQuery query = new ShoppingCartItemQuery();
		query.setCustomerId(customerDomain.getId());
		query.setSkuId(skuDomain.getId());
		query.setShoppingCartType(2);
		ShoppingCartItemDomain shoppingCart = getFirst(query);
		return shoppingCart;
	}

	@Override
	public ShoppingCartItemDomain isExistInCart(CustomerDomain customerDomain, SkuDomain skuDomain,Integer cartType) {
		ShoppingCartItemQuery query = new ShoppingCartItemQuery();
		query.setCustomerId(customerDomain.getId());
		query.setSkuId(skuDomain.getId());
		query.setShoppingCartType(cartType);
		ShoppingCartItemDomain shoppingCart = getFirst(query);
		return shoppingCart;
	}


	@Override
	@Transactional("transactionManager")
	public OrderDomain createOrder(CustomerDomain customerDomain, List<ShoppingCartItemDomain> cartList) {
		OrderDomain retOrderDomain = null;
		if(cartList!=null&&cartList.size()>0){
			BigDecimal amt = BigDecimal.ZERO;
			for (int i = 0;i<cartList.size();i++){
				ShoppingCartItemDomain line = cartList.get(i);
				BigDecimal pri = new BigDecimal(line.getGoodsPrice());
				BigDecimal num = new BigDecimal(line.getNum());
				amt = amt.add(pri.multiply(num));
			}
			OrderDomain orderDomain = new OrderDomain();
			Long customerId = customerDomain.getId();
			orderDomain.setCustomerId(customerId);

			String orderNo = "OR"+new Date().hashCode();//RandomUtils.generateNumUuid();报错
			orderDomain.setOrderNo(orderNo);
			orderDomain.setStatus(1);
			Date creatDate = new Date();
			//其他字段暂未填写

			orderDomain.setOrderTime(creatDate);
			//邮费暂未查询
			orderDomain.setOrderTotal(amt.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
			retOrderDomain = orderService.create(orderDomain);
			//创建明细
			for(int j = 0;j<cartList.size();j++){
				ShoppingCartItemDomain items = cartList.get(j);
				OrderItemDomain orderItemDomain = new OrderItemDomain();
				orderItemDomain.setOrderId(retOrderDomain.getId());
				orderItemDomain.setSkuId(items.getSkuId());
				orderItemDomain.setNum(items.getNum());
				orderItemDomain.setGoodsName(items.getGoodsName());
				orderItemDomain.setGoodsCode(items.getGoodsCode());
				orderItemDomain.setGoodsPrice(items.getGoodsPrice());
				orderItemDomain.setSkuSpecifications(items.getSkuSpecifications());
				orderItemService.create(orderItemDomain);
				//购物车中删除
				super.delete(items.getId());
			}
		}else {
			throw new ServiceException("购物车为空");
		}
		return retOrderDomain;
	}

	@Override
	@Transactional("transactionManager")
	public void wishToCart(CustomerDomain customerDomain, Long shoppingCartItemId) {
		Long customerId = customerDomain.getId();
		ShoppingCartItemDomain shoppingCartItemDomain = super.get(shoppingCartItemId);
		if(Objects.equals(shoppingCartItemDomain.getCustomerId(), customerId)){
			ShoppingCartItemQuery query = new ShoppingCartItemQuery();
			query.setCustomerId(customerId);
			query.setShoppingCartType(1);
			query.setGoodsName(shoppingCartItemDomain.getGoodsName());
			query.setSkuId(shoppingCartItemDomain.getSkuId());
			query.setGoodsPrice(shoppingCartItemDomain.getGoodsPrice());
			query.setGoodsCode(shoppingCartItemDomain.getGoodsCode());
			query.setSkuSpecifications(shoppingCartItemDomain.getSkuSpecifications());
			ShoppingCartItemDomain shoppingCartItemDomain2 = getOne(query);
			if(shoppingCartItemDomain2!=null){
				Integer num = shoppingCartItemDomain2.getNum();
				shoppingCartItemDomain2.setNum(num+1);
				update(shoppingCartItemDomain2);
				delete(shoppingCartItemId);
			}else{
				shoppingCartItemDomain.setShoppingCartType(1);
				update(shoppingCartItemDomain);
			}
		}else{
			throw new ServiceException("无法操作此商品");
		}
	}

	@Override
	public void wishToBoutique(CustomerDomain customerDomain, Long shoppingCartItemId) {
		ShoppingCartItemDomain shoppingCartItemDomain = get(shoppingCartItemId);
		shoppingCartItemDomain.setShoppingCartType(3);
		update(shoppingCartItemDomain);
	}


	@Override
	public void withGoodsItem(List<ShoppingCartItemDomain> shoppingCartItemDomainList) {
		List<Long> ids = shoppingCartItemDomainList.stream().map(ShoppingCartItemDomain::getItemId).collect(Collectors.toList());
		GoodsItemQuery query = new GoodsItemQuery();
		query.setIds(ids);
		List<GoodsItemDomain> goodsItemDomainList = goodsItemService.getList(query);
		for (ShoppingCartItemDomain shoppingCartItemDomain:shoppingCartItemDomainList){
			GoodsItemDomain goodsItemDomain = goodsItemDomainList.stream()
					.filter(x-> Objects.equals(x.getId(), shoppingCartItemDomain.getItemId())).findFirst().orElse(new GoodsItemDomain());
			shoppingCartItemDomain.setGoodsItemDomain(goodsItemDomain);
		}
	}

	@Override
	public void withReservationItem(List<ReservationItemDomain> reservationItemDomainList) {
		List<Long> ids = reservationItemDomainList.stream().map(ReservationItemDomain::getItemId).collect(Collectors.toList());
		GoodsItemQuery query = new GoodsItemQuery();
		query.setIds(ids);
		List<GoodsItemDomain> goodsItemDomainList = goodsItemService.getList(query);
		for (ReservationItemDomain reservationItemDomain:reservationItemDomainList){
			GoodsItemDomain goodsItemDomain = goodsItemDomainList.stream()
					.filter(x-> Objects.equals(x.getId(), reservationItemDomain.getItemId())).findFirst().orElse(new GoodsItemDomain());
			reservationItemDomain.setGoodsItemDomain(goodsItemDomain);
		}
	}

	@Override
	public SkuDomain getSkubySizeAndItem(Long itemId ,Long sizeId) {
		 /*获取SKU*/
		System.out.println("sizeId:"+sizeId+"\nitemId:"+itemId);
		SkuQuery skuQuery = new SkuQuery();
		skuQuery.setItemId(itemId);
		skuQuery.setIsValid(ValidEnum.YES.getValue());
		System.out.println("skuQuery"+ JsonUtils.toJSONString(skuQuery));
		List<SkuDomain> skuDomainList = skuService.getList(skuQuery);
		System.out.println("skuDomainList"+JsonUtils.toJSONString(skuDomainList));
		SkuDomain skuDomain =  skuDomainList.stream().filter(x-> JSONObject.fromObject(x.getSpecifications()).getLong("size")==sizeId).findFirst().orElse(null);
		return skuDomain;
	}

	@Override
	public void withSku(List<ShoppingCartItemDomain> shoppingCartItemDomainList) {
		for(ShoppingCartItemDomain line:shoppingCartItemDomainList){
			SkuDomain skuDomain = 	skuService.get(line.getSkuId());
			line.setQuantity(skuDomain.getQuantity());
			line.setSkuDomain(skuDomain);
		}
	}

	@Override
	public void withSizeDomain(List<ShoppingCartItemDomain> cartList) {
		Long sizeId = null;
		for(ShoppingCartItemDomain line:cartList){
			JSONObject jsonObject = JSONObject.fromObject(line.getSkuSpecifications());
			sizeId = Long.parseLong(""+jsonObject.get("size"));
			line.setSizeDomain(prototypeSpecificationOptionService.get(sizeId));
		}
	}


}
