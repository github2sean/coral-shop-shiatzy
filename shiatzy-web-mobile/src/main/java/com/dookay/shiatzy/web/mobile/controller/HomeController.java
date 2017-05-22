package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.web.HttpContext;
import com.dookay.coral.shop.goods.domain.GoodsCategoryDomain;
import com.dookay.coral.shop.goods.query.GoodsCategoryQuery;
import com.dookay.coral.shop.goods.service.IGoodsCategoryService;
import com.dookay.coral.shop.promotion.domain.CouponDomain;
import com.dookay.coral.shop.promotion.query.CouponQuery;
import com.dookay.coral.shop.promotion.service.ICouponService;
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
    @Autowired
    private ICouponService couponService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("home/index");
        CouponQuery query = new CouponQuery();
        query.setIndexShow(1);
        CouponDomain couponDomain = couponService.getFirst(query);
        if(couponService.checkCoupon(couponDomain.getCode())!=null){
            mv.addObject("coupon",couponDomain);
        }
        return mv;
    }


}
