package com.dookay.coral.shop.order.service.impl;

import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.domain.SkuDomain;
import com.dookay.coral.shop.goods.service.IGoodsService;
import com.dookay.coral.shop.goods.service.ISkuSpecificationValueService;
import com.dookay.coral.shop.order.enums.ShoppingCartTypeEnum;
import com.dookay.coral.shop.order.query.ShoppingCartItemQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.order.mapper.ShoppingCartItemMapper;
import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;
import com.dookay.coral.shop.order.service.IShoppingCartService;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

/**
 * 购物车的业务实现类
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("shoppingCartItemService")
public class ShoppingCartServiceImpl extends BaseServiceImpl<ShoppingCartItemDomain> implements IShoppingCartService {
	
	@Autowired
	private ShoppingCartItemMapper shoppingCartItemMapper;

	@Autowired
	private IGoodsService goodsService;

	@Override
	public void removeFromCart(Long id) {
		super.delete(id);
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

		GoodsDomain goodsDomain = goodsService.get(skuDomain.getGoodsId());

		ShoppingCartItemDomain shoppingCartItemDomain = new ShoppingCartItemDomain();
		shoppingCartItemDomain.setCustomerId(customerDomain.getId());
		shoppingCartItemDomain.setGoodsCode(goodsDomain.getCode());
		shoppingCartItemDomain.setGoodsName(goodsDomain.getName());
		shoppingCartItemDomain.setGoodsPrice(skuDomain.getPrice());
		shoppingCartItemDomain.setSkuId(skuDomain.getId());
		shoppingCartItemDomain.setShoppingCartType(shoppingCartType);
		shoppingCartItemDomain.setNum(num);
		shoppingCartItemDomain.setSkuSpecifications(skuDomain.getSpecifications());
		super.create(shoppingCartItemDomain);
	}

	@Override
	public void UpdateShoppingCartItem(CustomerDomain customerDomain, Long shoppingCartItemId, int num) {
		Long customerId = customerDomain.getId();
		ShoppingCartItemDomain shoppingCartItemDomain = super.get(shoppingCartItemId);
		if(Objects.equals(shoppingCartItemDomain.getCustomerId(), customerId)){
			if(num>0){
				shoppingCartItemDomain.setNum(num);
				super.update(shoppingCartItemDomain);
			}else{
				super.delete(shoppingCartItemId);
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
}
