package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.common.web.BaseController;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.shop.goods.service.IGoodsService;
import com.dookay.shiatzy.web.mobile.form.AddShoppingCartForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by admin on 2017/4/27.
 */

@Controller

@RequestMapping("boutique/")
public class BoutiqueController extends BaseController{

    private IGoodsService goodsService;

    @RequestMapping(value = "add" ,method = RequestMethod.POST)
    public JsonResult add(@ModelAttribute AddShoppingCartForm addShoppingCartForm){

        int type = addShoppingCartForm.getType();
        if(type==1){

        }else{

        }




        return  successResult("添加成功",1);
    }

    @RequestMapping(value = "list" ,method = RequestMethod.GET)
    public ModelAndView list(){




        ModelAndView mv = new ModelAndView("boutique/list");
        return mv;
    }



}
