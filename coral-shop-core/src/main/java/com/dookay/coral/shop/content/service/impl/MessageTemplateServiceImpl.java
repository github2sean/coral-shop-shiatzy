package com.dookay.coral.shop.content.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.content.mapper.MessageTemplateMapper;
import com.dookay.coral.shop.content.domain.MessageTemplateDomain;
import com.dookay.coral.shop.content.service.IMessageTemplateService;

/**
 * 消息模板的业务实现类
 * @author : luxor
 * @since : 2017年06月13日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("messageTemplateService")
public class MessageTemplateServiceImpl extends BaseServiceImpl<MessageTemplateDomain> implements IMessageTemplateService {
	
	@Autowired
	private MessageTemplateMapper messageTemplateMapper;
	  
}
