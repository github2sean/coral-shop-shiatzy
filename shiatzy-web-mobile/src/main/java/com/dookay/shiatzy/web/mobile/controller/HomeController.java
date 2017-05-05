package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.web.HttpContext;
import com.dookay.coral.shop.goods.domain.GoodsCategoryDomain;
import com.dookay.coral.shop.goods.query.GoodsCategoryQuery;
import com.dookay.coral.shop.goods.service.IGoodsCategoryService;
import com.dookay.shiatzy.web.mobile.base.MobileBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/4/25
 */
@Controller
@RequestMapping("home/")
public class HomeController extends MobileBaseController {

    @Autowired
    private IGoodsCategoryService goodsCategoryService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("home/index");
        //获取session
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        GoodsCategoryQuery query = new GoodsCategoryQuery();
        query.setLevel(1);
        List<GoodsCategoryDomain> categoryLevle1List =  goodsCategoryService.getList(query);
        query.setLevel(2);
        List<GoodsCategoryDomain> categoryLevle2List =  goodsCategoryService.getList(query);
        query.setLevel(3);
        List<GoodsCategoryDomain> categoryLevle3List =  goodsCategoryService.getList(query);
        session.setAttribute("categoryLevle1List",categoryLevle1List);
        session.setAttribute("categoryLevle2List",categoryLevle2List);
        session.setAttribute("categoryLevle3List",categoryLevle3List);
        System.out.println("categoryLevle1List:"+ JsonUtils.toJSONString(categoryLevle1List));
        return mv;
    }


}
