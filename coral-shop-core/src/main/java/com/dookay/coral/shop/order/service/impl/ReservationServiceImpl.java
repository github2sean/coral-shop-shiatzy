package com.dookay.coral.shop.order.service.impl;

import com.dookay.coral.common.utils.RandomUtils;
import com.dookay.coral.common.web.CookieUtil;
import com.dookay.coral.common.web.HttpContext;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.order.domain.ReservationItemDomain;
import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;
import com.dookay.coral.shop.order.service.IReservationItemService;
import com.dookay.coral.shop.order.service.IShoppingCartService;
import com.dookay.coral.shop.shipping.domain.ShippingCountryDomain;
import com.dookay.coral.shop.shipping.service.IShippingCountryService;
import com.dookay.coral.shop.store.domain.StoreDomain;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.order.mapper.ReservationMapper;
import com.dookay.coral.shop.order.domain.ReservationDomain;
import com.dookay.coral.shop.order.service.IReservationService;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 预约单的业务实现类
 * @author : luxor
 * @since : 2017年05月03日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("reservationService")
public class ReservationServiceImpl extends BaseServiceImpl<ReservationDomain> implements IReservationService {
	
	@Autowired
	private ReservationMapper reservationMapper;
	@Autowired
	private IShippingCountryService shippingCountryService;
	@Autowired
	private IReservationItemService reservationItemService;
	@Autowired
	private IShoppingCartService shoppingCartService;




	@Override
	@Transactional("transactionManager")
	public Long submit(List<ShoppingCartItemDomain> cartList, CustomerDomain customerDomain,StoreDomain storeDomain) {

		HttpServletRequest request = HttpContext.current().getRequest();
		String en_US = CookieUtil.getCookieValueByKey(request,"Language");
		ReservationDomain reservationDomain = new ReservationDomain();
		reservationDomain.setCreateTime(new Date());
		reservationDomain.setRank(1);
		reservationDomain.setIsVisible(0);
		reservationDomain.setStatus(0);
		reservationDomain.setCustomerId(customerDomain.getId());
		reservationDomain.setReservationNo(RandomUtils.buildNo());
		reservationDomain.setStoreTitle(storeDomain.getId()+"");
		reservationDomain.setTel(storeDomain.getTel());
		reservationDomain.setAddress("en_US".equals(en_US)?storeDomain.getEnAddress():storeDomain.getAddress());
		reservationDomain.setTime(storeDomain.getTime());
		reservationDomain.setNote("");
		reservationDomain.setUpdateTime(new Date());

		String countryId = CookieUtil.getCookieValueByKey(request,"shippingCountry");
		ShippingCountryDomain shippingCountryDomain = null;
		String currentCode = "CNY";
		Double rate = 1D;
		if(StringUtils.isNotBlank(countryId)){
			shippingCountryDomain = shippingCountryService.get(Long.parseLong(countryId));
			rate = shippingCountryDomain.getRate();
			//根据国家选择结算币种
			int currentCodeType = shippingCountryDomain.getRateType();
			if(currentCodeType==1){
				currentCode = "USD";
			}else if(currentCodeType==2){
				currentCode = "EUR";
			}
		}
		reservationDomain.setRate(rate);
		reservationDomain.setCurrentCode(currentCode);
		create(reservationDomain);

		for (ShoppingCartItemDomain line:cartList){
			ReservationItemDomain reservationItemDomain = new ReservationItemDomain();
			reservationItemDomain.setRank(1);
			reservationItemDomain.setIsVisible(1);
			reservationItemDomain.setReservationId(reservationDomain.getId());
			reservationItemDomain.setGoodsName("en_US".equals(en_US)?line.getGoodsEnName():line.getGoodsName());
			reservationItemDomain.setGoodsPrice(line.getGoodsPrice()/rate);
			reservationItemDomain.setGoodsDisPrice(line.getGoodsDisPrice()/rate);
			reservationItemDomain.setSkuCode(line.getSkuId()+"");
			reservationItemDomain.setNum(line.getNum());
			reservationItemDomain.setItemId(line.getItemId());
			reservationItemDomain.setSpecifications(line.getSkuSpecifications());
			reservationItemDomain.setCreateTime(new Date());
			reservationItemDomain.setUpdateTime(new Date());
			reservationItemDomain.setStatus(0);
			reservationItemService.create(reservationItemDomain);
			//从精品店中移除
			shoppingCartService.removeFromCart(line.getId());
		}
		return reservationDomain.getId();
	}
}
