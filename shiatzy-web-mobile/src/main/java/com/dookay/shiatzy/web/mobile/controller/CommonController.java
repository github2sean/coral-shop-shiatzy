package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.common.web.JsonResult;
import com.dookay.shiatzy.web.mobile.base.MobileBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/6/14
 */
@Controller
@RequestMapping("common/")
public class CommonController  extends MobileBaseController {

    @RequestMapping(value = "subscribe", method = RequestMethod.GET)
    public JsonResult subscribe(String email){
        return successResult("订阅成功");
    }
}
