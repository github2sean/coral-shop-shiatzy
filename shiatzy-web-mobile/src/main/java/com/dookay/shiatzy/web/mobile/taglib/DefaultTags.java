package com.dookay.shiatzy.web.mobile.taglib;

import com.dookay.coral.common.web.utils.SpringContextHolder;
import com.dookay.coral.host.user.context.UserContext;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.goods.domain.GoodsCategoryDomain;
import com.dookay.coral.shop.goods.query.GoodsCategoryQuery;
import com.dookay.coral.shop.goods.service.IGoodsCategoryService;
import com.dookay.coral.shop.order.enums.ShoppingCartTypeEnum;
import com.dookay.coral.shop.order.query.ShoppingCartItemQuery;
import com.dookay.coral.shop.order.service.IShoppingCartService;
import com.dookay.coral.shop.shipping.domain.ShippingCountryDomain;
import com.dookay.coral.shop.shipping.query.ShippingCountryQuery;
import com.dookay.coral.shop.shipping.service.IShippingCountryService;

import java.util.List;

/**
 * 系统默认标签库
 * @author : luxor
 * @since : 2016年11月25日
 * @version : v0.0.1
 */
public class DefaultTags {
	

	public static List<GoodsCategoryDomain> getGoodsCategoryList() {
		IGoodsCategoryService goodsCategoryService = SpringContextHolder.getBean("goodsCategoryService");
		GoodsCategoryQuery query = new GoodsCategoryQuery();
		query.setOrderBy("displayOrder");
		query.setDesc(false);
		query.setLevel(1);
		return   goodsCategoryService.listCategory(query);
	}

	public static List<ShippingCountryDomain> getShippingCountryList() {
		IShippingCountryService shippingCountryService = SpringContextHolder.getBean("shippingCountryService");
		ShippingCountryQuery query = new ShippingCountryQuery();
		query.setDesc(false);
		query.setOrderBy("rank");
		return  shippingCountryService.getList(query);
	}

}
