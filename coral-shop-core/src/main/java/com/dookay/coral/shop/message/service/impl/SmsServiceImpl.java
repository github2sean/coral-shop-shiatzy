package com.dookay.coral.shop.message.service.impl;

import com.dookay.coral.adapter.sendmsg.config.SendToPhoneConfig;
import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.common.utils.HttpClientUtil;
import com.dookay.coral.shop.content.domain.MessageTemplateDomain;
import com.dookay.coral.shop.content.query.MessageTemplateQuery;
import com.dookay.coral.shop.content.service.IMessageTemplateService;
import com.dookay.coral.shop.message.domain.SmsHistoryDomain;
import com.dookay.coral.shop.message.service.ISmsHistoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.message.mapper.SmsMapper;
import com.dookay.coral.shop.message.domain.SmsDomain;
import com.dookay.coral.shop.message.service.ISmsService;

import java.util.Date;
import java.util.HashMap;

/**
 * 短信的业务实现类
 * @author : luxor
 * @since : 2017年06月13日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("smsService")
public class SmsServiceImpl extends BaseServiceImpl<SmsDomain> implements ISmsService {
	
	@Autowired
	private SmsMapper smsMapper;
	@Autowired
	private SendToPhoneConfig sendToPhoneConfig;

	@Autowired
	private ISmsHistoryService smsHistoryService;

	@Autowired
	private IMessageTemplateService messageTemplateService;


	@Override
	public void sendToSms(Boolean isEN,String phone, Integer code) {

		if(StringUtils.isBlank(phone)||code==null){
			throw  new ServiceException("参数不能为空");
		}
		MessageTemplateQuery query = new MessageTemplateQuery();
		query.setIsValid(1);
		query.setCode(code);
		query.setType(2);
		MessageTemplateDomain messageTemplate = messageTemplateService.getFirst(query);

		HttpClientUtil httpClientUtil = new HttpClientUtil();
		String url = sendToPhoneConfig.getUrl();
		HashMap<String,Object> map = new HashMap();
		map.put("account",sendToPhoneConfig.account);
		map.put("pswd",sendToPhoneConfig.getPswd());
		map.put("mobile",phone);
		map.put("needstatus",sendToPhoneConfig.getNeedstatus());
		map.put("msg",isEN?messageTemplate.getEnContent():messageTemplate.getContent());
		map.put("product",sendToPhoneConfig.getProduct());
		Object obj = null;
		try {
			obj = httpClientUtil .sendPostRequest(url,map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("发送失败");
		}
		//加入发送历史
		SmsHistoryDomain smsDomain = new SmsHistoryDomain();
		smsDomain.setBody(isEN?messageTemplate.getEnContent():messageTemplate.getContent());
		smsDomain.setMobile(phone);
		smsDomain.setMobile(new Date().toString());
		smsHistoryService.create(smsDomain);

	}
}
