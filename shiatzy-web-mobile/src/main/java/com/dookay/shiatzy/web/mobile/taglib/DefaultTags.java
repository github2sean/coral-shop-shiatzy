package com.dookay.shiatzy.web.mobile.taglib;

import com.dookay.coral.common.enums.ValidEnum;
import com.dookay.coral.common.web.CookieUtil;
import com.dookay.coral.common.web.HttpContext;
import com.dookay.coral.common.web.utils.SpringContextHolder;
import com.dookay.coral.host.user.context.UserContext;
import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.goods.domain.GoodsCategoryDomain;
import com.dookay.coral.shop.goods.query.GoodsCategoryQuery;
import com.dookay.coral.shop.goods.service.IGoodsCategoryService;
import com.dookay.coral.shop.order.domain.OrderDomain;
import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;
import com.dookay.coral.shop.order.enums.ShoppingCartTypeEnum;
import com.dookay.coral.shop.order.query.ShoppingCartItemQuery;
import com.dookay.coral.shop.order.service.IShoppingCartService;
import com.dookay.coral.shop.shipping.domain.ShippingCountryDomain;
import com.dookay.coral.shop.shipping.query.ShippingCountryQuery;
import com.dookay.coral.shop.shipping.service.IShippingCountryService;
import com.dookay.shiatzy.web.mobile.form.AddShoppingCartForm;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
		query.setOrderBy("rank");
		query.setDesc(false);
		query.setLevel(1);
		query.setIsValid(ValidEnum.YES.getValue());
		return   goodsCategoryService.listCategory(query);
	}

	public static List<ShippingCountryDomain> getShippingCountryList() {
		IShippingCountryService shippingCountryService = SpringContextHolder.getBean("shippingCountryService");
		ShippingCountryQuery query = new ShippingCountryQuery();
		query.setDesc(false);
		query.setOrderBy("rank");
		return  shippingCountryService.getList(query);
	}

	public static String getSelectCountry() {
		HttpServletRequest request = HttpContext.current().getRequest();
		String selectCountry = CookieUtil.getCookieValueByKey(request,"shippingCountry");
		return  selectCountry;
	}

	public static List<ShippingCountryDomain> getSelectCountryPhoneList() {
		IShippingCountryService shippingCountryService = SpringContextHolder.getBean("shippingCountryService");
		ShippingCountryQuery query = new ShippingCountryQuery();
		query.setDesc(false);
		query.setOrderBy("rank");
		return  shippingCountryService.getList(query);
	}

	public static String getSelectLanguage() {
		HttpServletRequest request = HttpContext.current().getRequest();
		String selectLanguage = CookieUtil.getCookieValueByKey(request,"Language");
		return  selectLanguage;
	}

	/**
	 * 选择地区是否中国
	 * @return
	 */
	public static Boolean isChina() {
		HttpServletRequest request = HttpContext.current().getRequest();
		String selectCountry = CookieUtil.getCookieValueByKey(request,"shippingCountry");
		return "1".equals(selectCountry);
	}

	public static Boolean isEn() {
		HttpServletRequest request = HttpContext.current().getRequest();
		String selectLanguage = CookieUtil.getCookieValueByKey(request,"Language");
		return "en_US".equals(selectLanguage);
	}

	public static String translate(String cnString,String enString) {
		HttpServletRequest request = HttpContext.current().getRequest();
		String selectLanguage = CookieUtil.getCookieValueByKey(request,"Language");
		return "en_US".equals(selectLanguage)?enString:cnString;
	}

	public static Integer getCartNum(Integer type) {
		IShoppingCartService shoppingCartService = SpringContextHolder.getBean("shoppingCartService");
		ICustomerService customerService = SpringContextHolder.getBean("customerService");
		UserContext userContext = UserContext.current();
		if(userContext==null){
			HttpSession session = HttpContext.current().getSession();
			List<AddShoppingCartForm> listCart = (List<AddShoppingCartForm>)session.getAttribute("session_cart");
			Integer retNum = 0;
			if(listCart!=null&&listCart.size()>0){
				for(AddShoppingCartForm form :listCart){
					if(form.getType()==type){
						retNum +=1;
					}
				}
			}
			return retNum;
		}
		AccountDomain accountDomain = userContext.getAccountDomain();
		ShoppingCartItemQuery query = new ShoppingCartItemQuery();
		query.setShoppingCartType(type);
		query.setCustomerId(customerService.getAccount(accountDomain.getId()).getId());
		List<ShoppingCartItemDomain> cartList = shoppingCartService.getList(query);
		return  cartList!=null&&cartList.size()>0?cartList.size():0;
	}

	public static Boolean isSubmited() {
		HttpServletRequest request = HttpContext.current().getRequest();
		OrderDomain order = (OrderDomain)request.getSession().getAttribute("order");
		System.out.println("order:"+order==null?true:false);
		return order==null?true:false;
	}

}
