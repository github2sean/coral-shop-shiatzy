package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.common.web.BaseController;
import com.dookay.shiatzy.web.mobile.form.QueryGoodsForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by admin on 2017/4/25.
 */
@Controller
@RequestMapping("/content/")
public class ContentController extends BaseController {

    @RequestMapping(value = "faq", method = RequestMethod.GET)
    public ModelAndView faq(){
        ModelAndView modelAndView = new ModelAndView("/content/faq");
        return modelAndView;
    }
}
