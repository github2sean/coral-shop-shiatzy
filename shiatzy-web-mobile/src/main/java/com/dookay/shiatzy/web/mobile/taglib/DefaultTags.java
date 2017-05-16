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

import java.util.List;

/**
 * 系统默认标签库
 * @author : luxor
 * @since : 2016年11月25日
 * @version : v0.0.1
 */
public class DefaultTags {
	
	/**
	 * 获取用户信息
	 * @return
	 * @author : kezhan	
	 * @since : 2016年12月11日
	 */
	public static List<GoodsCategoryDomain> getGoodsCategoryList() {
		IGoodsCategoryService goodsCategoryService = SpringContextHolder.getBean("goodsCategoryService");
		GoodsCategoryQuery query = new GoodsCategoryQuery();
		query.setOrderBy("displayOrder");
		query.setDesc(false);
		query.setLevel(1);
		return   goodsCategoryService.listCategory(query);
	}

}
