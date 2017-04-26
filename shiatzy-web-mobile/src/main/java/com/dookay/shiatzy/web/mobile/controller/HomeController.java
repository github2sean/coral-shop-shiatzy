package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.shiatzy.web.mobile.base.MobileBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/4/25
 */
@Controller
@RequestMapping("home/")
public class HomeController extends MobileBaseController {

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("home/index");
        return mv;
    }
}
