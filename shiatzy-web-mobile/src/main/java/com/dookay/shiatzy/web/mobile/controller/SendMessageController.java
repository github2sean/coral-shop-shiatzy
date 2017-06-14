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
    public JsonResult sendToSMS(String phone, String content) {
        HttpClientUtil httpClientUtil = new HttpClientUtil();
        String url = sendToPhoneConfig.getUrl();
        HashMap<String,Object> map = new HashMap();
        map.put("account",sendToPhoneConfig.account);
        map.put("pswd",sendToPhoneConfig.getPswd());
        map.put("mobile",phone);
        map.put("needstatus",sendToPhoneConfig.getNeedstatus());
        map.put("msg",content);
        map.put("product",sendToPhoneConfig.getProduct());
        Object obj = null;
        try {
            obj = httpClientUtil .sendPostRequest(url,map);
        } catch (Exception e) {
            e.printStackTrace();
            return errorResult("发送失败");
        }
        //加入发送历史
        SmsDomain smsDomain = new SmsDomain();
        smsDomain.setBody(content);
        smsDomain.setMobile(phone);
        smsDomain.setMobile(new Date().toString());
        smsService.create(smsDomain);

        return successResult("操作完成",obj);
    }

    @RequestMapping(value = "sendToEmail",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult sendToEmail() {


        System.out.println("sendEmail");
        return successResult("操作完成");
    }

}
