package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.shop.content.service.ISubscribeService;
import com.dookay.shiatzy.web.mobile.base.MobileBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/6/14
 */
@Controller
@RequestMapping("common/")
public class CommonController  extends MobileBaseController {

    @Autowired
    private ISubscribeService subscribeService;

    @ResponseBody
    @RequestMapping(value = "subscribe", method = RequestMethod.POST)
    public JsonResult subscribe(String email){
        subscribeService.createSubscribe(email);
        return successResult("订阅成功");
    }
}
