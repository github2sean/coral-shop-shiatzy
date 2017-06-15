package com.dookay.shiatzy.web.mobile.controller;


import com.dookay.coral.adapter.sendmsg.config.SendToPhoneConfig;
import com.dookay.coral.common.utils.HttpClientUtil;
import com.dookay.coral.common.web.BaseController;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.shop.content.service.IMessageTemplateService;
import com.dookay.coral.shop.message.domain.SmsDomain;
import com.dookay.coral.shop.message.service.IEmailHistoryService;
import com.dookay.coral.shop.message.service.IEmailService;
import com.dookay.coral.shop.message.service.ISmsHistoryService;
import com.dookay.coral.shop.message.service.ISmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by admin on 2017/6/13.
 */
@Controller
@RequestMapping("sendMessage/")
public class SendMessageController extends BaseController {



    @Autowired
    private SendToPhoneConfig sendToPhoneConfig;
    @Autowired
    private ISmsService smsService;
    @Autowired
    private ISmsHistoryService smsHistoryService;
    @Autowired
    private IEmailHistoryService emailHistoryService;
    @Autowired
    private IEmailService emailService;

    @RequestMapping(value = "sendToSMS",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult sendToSMS(String phone, Integer contentCode) {

        smsService.sendToSms(phone,contentCode);
        return successResult("操作完成");
    }

    @RequestMapping(value = "sendToEmail",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult sendToEmail() {


        System.out.println("sendEmail");
        return successResult("操作完成");
    }

}
