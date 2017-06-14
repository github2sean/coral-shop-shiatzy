package com.dookay.shiatzy.web.mobile.controller;


import com.dookay.coral.adapter.sendmsg.config.SendToPhoneConfig;
import com.dookay.coral.common.utils.HttpClientUtil;
import com.dookay.coral.common.web.BaseController;
import com.dookay.coral.common.web.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * Created by admin on 2017/6/13.
 */
@Controller
@RequestMapping("send/")
public class SendMsgController extends BaseController {



    @Autowired
    private SendToPhoneConfig sendToPhoneConfig;

    @RequestMapping(name = "sendSMS",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult sendSMS(String phone, String content) throws Exception {
        HttpClientUtil httpClientUtil = new HttpClientUtil();
        String url = sendToPhoneConfig.getUrl();
        HashMap<String,Object> map = new HashMap();
        map.put("account",sendToPhoneConfig.account);
        map.put("pswd",sendToPhoneConfig.getPswd());
        map.put("mobile",phone);
        map.put("needstatus",sendToPhoneConfig.getNeedstatus());
        map.put("msg",content);
        map.put("product",sendToPhoneConfig.getProduct());

        System.out.println("map:"+map);
        Object obj =   httpClientUtil .sendPostRequest(url,map);
        return successResult("操作完成",obj);
    }

    @RequestMapping(name = "sendEmail",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult sendEmail(String email, String content) {


        System.out.println("sendEmail");
        return successResult("操作完成");
    }

}
