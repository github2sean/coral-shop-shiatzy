package com.dookay.coral.shop.order.service.impl;

import com.dookay.coral.adapter.sendmsg.sendmail.SimpleAliDMSendMail;
import com.dookay.coral.common.utils.RandomUtils;
import com.dookay.coral.common.web.CookieUtil;
import com.dookay.coral.common.web.HttpContext;
import com.dookay.coral.shop.content.domain.MessageTemplateDomain;
import com.dookay.coral.shop.content.query.MessageTemplateQuery;
import com.dookay.coral.shop.content.service.IMessageTemplateService;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.goods.domain.GoodsItemDomain;
import com.dookay.coral.shop.goods.query.GoodsItemQuery;
import com.dookay.coral.shop.goods.service.IGoodsItemService;
import com.dookay.coral.shop.goods.service.IGoodsService;
import com.dookay.coral.shop.goods.service.IPrototypeSpecificationOptionService;
import com.dookay.coral.shop.message.enums.MessageTypeEnum;
import com.dookay.coral.shop.message.service.ISmsService;
import com.dookay.coral.shop.order.domain.ReservationItemDomain;
import com.dookay.coral.shop.order.domain.ReturnRequestItemDomain;
import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;
import com.dookay.coral.shop.order.service.IReservationItemService;
import com.dookay.coral.shop.order.service.IShoppingCartService;
import com.dookay.coral.shop.order.utils.FreemarkerUtil;
import com.dookay.coral.shop.shipping.domain.ShippingCountryDomain;
import com.dookay.coral.shop.shipping.service.IShippingCountryService;
import com.dookay.coral.shop.store.domain.StoreDomain;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.order.mapper.ReservationMapper;
import com.dookay.coral.shop.order.domain.ReservationDomain;
import com.dookay.coral.shop.order.service.IReservationService;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
	@Autowired
	private SimpleAliDMSendMail simpleAliDMSendMail;
	@Autowired
	private IMessageTemplateService messageTemplateService;
	@Autowired
	private ISmsService smsService;
	@Autowired
	private IGoodsItemService goodsItemService;
	@Autowired
	private IGoodsService goodsService;
	@Autowired
	private IPrototypeSpecificationOptionService prototypeSpecificationOptionService;

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
		reservationDomain.setStoreDomain(storeDomain);
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
		Double totalAmt = 0D;
		List<ReservationItemDomain> requestList = new ArrayList<>();
		for (ShoppingCartItemDomain line:cartList){
			ReservationItemDomain reservationItemDomain = new ReservationItemDomain();
			reservationItemDomain.setRank(1);
			reservationItemDomain.setIsVisible(1);
			reservationItemDomain.setReservationId(reservationDomain.getId());
			reservationItemDomain.setGoodsName("en_US".equals(en_US)?line.getGoodsEnName():line.getGoodsName());
			reservationItemDomain.setGoodsPrice(formatDouble(line.getGoodsPrice()/rate));
			reservationItemDomain.setGoodsDisPrice(formatDouble(line.getGoodsDisPrice()/rate));
			reservationItemDomain.setSkuCode(line.getSkuId()+"");
			reservationItemDomain.setNum(line.getNum());
			reservationItemDomain.setItemId(line.getItemId());
			reservationItemDomain.setSpecifications(line.getSkuSpecifications());
			reservationItemDomain.setCreateTime(new Date());
			reservationItemDomain.setUpdateTime(new Date());
			reservationItemDomain.setStatus(0);
			reservationItemDomain.setSizeDomain(prototypeSpecificationOptionService.get(JSONObject.fromObject(line.getSkuSpecifications()).getLong("size")));
			reservationItemDomain.setGoodsItemDomain(goodsItemService.get(line.getItemId()));
			reservationItemService.create(reservationItemDomain);
			requestList.add(reservationItemDomain);
			Double price = reservationItemDomain.getGoodsDisPrice()!=null?reservationItemDomain.getGoodsDisPrice():reservationItemDomain.getGoodsPrice();
			totalAmt = price * reservationItemDomain.getNum();
			//从精品店中移除
			shoppingCartService.removeFromCart(line.getId());
		}

		//发送短信
		String phone = customerDomain.getPhone();
		if(StringUtils.isNotBlank(phone)){
			smsService.sendToSms(customerDomain.getPhone(), MessageTypeEnum.STORE_RESERVATION.getValue());
		}
		//发送邮件
		//1.查询发送内容
		MessageTemplateQuery query = new MessageTemplateQuery();
		query.setType(1);
		query.setCode(MessageTypeEnum.STORE_RESERVATION.getValue());
		query.setIsValid(1);
		MessageTemplateDomain messageTemplate = messageTemplateService.getFirst(query);
		//2.生成模版
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Map<String,Object> freeMap = new HashMap<>();
		freeMap.put("picUrl", FreemarkerUtil.getLogoUrl("static/images/logoSC.png"));
		freeMap.put("title",messageTemplate.getTitle());
		freeMap.put("name",customerDomain.getEmail());
		freeMap.put("status", "处理中");
		freeMap.put("content",messageTemplate.getContent());
		freeMap.put("date",simpleDateFormat.format(reservationDomain.getCreateTime()));
		freeMap.put("order",reservationDomain);
		freeMap.put("orderItem",requestList);
		reservationWithGoodItem(requestList);
		freeMap.put("totalFee",totalAmt);
		freeMap.put("openDate",reservationDomain.getStoreDomain().getTime());
		String html = FreemarkerUtil.printString("reservationOrder.ftl",freeMap);
		//3.设置发送邮件参数
		HashMap<String,String> emailMap = new HashMap<>();
		emailMap.put(simpleAliDMSendMail.SEND_EMAIL,simpleAliDMSendMail.SEND_EMAIL_SINGEL);
		emailMap.put(simpleAliDMSendMail.RECEIVE_EMAIL,customerDomain.getEmail());
		emailMap.put(simpleAliDMSendMail.TITLE,messageTemplate.getTitle());
		emailMap.put(simpleAliDMSendMail.CONTENT,html);
		try {
			simpleAliDMSendMail.sendEmail(emailMap);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return reservationDomain.getId();
	}


	@Override
	public void reservationWithGoodItem(List<ReservationItemDomain> reservationItemDomainList) {

		List<Long> ids = reservationItemDomainList.stream().map(ReservationItemDomain::getItemId).collect(Collectors.toList());
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
		for (ReservationItemDomain reservationItemDomain:reservationItemDomainList){
			GoodsItemDomain goodsItemDomain = goodsItemDomainList.stream()
					.filter(x-> Objects.equals(x.getId(), reservationItemDomain.getItemId())).findFirst().orElse(new GoodsItemDomain());
			reservationItemDomain.setGoodsItemDomain(goodsItemDomain);
			reservationItemDomain.setSizeDomain(prototypeSpecificationOptionService.get(JSONObject.fromObject(reservationItemDomain.getSpecifications()).getLong("size")));
		}
	}

	public Double formatDouble(Double doubleNum){
		doubleNum = new BigDecimal(doubleNum).setScale(0,BigDecimal.ROUND_HALF_DOWN).doubleValue();
		return doubleNum;
	}

}
