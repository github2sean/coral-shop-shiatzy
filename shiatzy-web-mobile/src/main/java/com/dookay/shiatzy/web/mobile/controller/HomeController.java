package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.web.HttpContext;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.host.user.context.UserContext;
import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.host.user.service.IAccountService;
import com.dookay.coral.shop.content.domain.ContentCategoryDomain;
import com.dookay.coral.shop.content.query.ContentCategoryQuery;
import com.dookay.coral.shop.content.service.IContentCategoryService;
import com.dookay.coral.shop.customer.domain.CustomerAddressDomain;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.service.ICustomerAddressService;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.goods.domain.GoodsCategoryDomain;
import com.dookay.coral.shop.goods.query.GoodsCategoryQuery;
import com.dookay.coral.shop.goods.service.IGoodsCategoryService;
import com.dookay.coral.shop.promotion.domain.CouponDomain;
import com.dookay.coral.shop.promotion.query.CouponQuery;
import com.dookay.coral.shop.promotion.service.ICouponService;
import com.dookay.coral.shop.shipping.domain.ShippingCountryDomain;
import com.dookay.coral.shop.shipping.query.ShippingCountryQuery;
import com.dookay.coral.shop.shipping.service.IShippingCountryService;
import com.dookay.shiatzy.web.mobile.base.MobileBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @Autowired
    private IShippingCountryService shippingCountryService;
    @Autowired
    private ICustomerAddressService customerAddressService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IContentCategoryService contentCategoryService;
    public final static String SHIPPING_COUNTRY_ID="shippingCountryId";
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index(){

        ModelAndView mv = new ModelAndView("home/index");
        CouponQuery query = new CouponQuery();
        query.setIndexShow(1);
        CouponDomain couponDomain = couponService.getFirst(query);
        ContentCategoryQuery querys=new ContentCategoryQuery();
        querys.setLevel(1);
        List<ContentCategoryDomain> domainList=contentCategoryService.getList(querys);
        mv.addObject("coupon",couponDomain);
        mv.addObject("domainList",domainList);
        return mv;
    }


    @RequestMapping(value = "chooseShippingCountry", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult chooseShippingCountry(Long shippingCountryId){
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(SHIPPING_COUNTRY_ID,shippingCountryId);
        return successResult("选择成功");
    }

    @RequestMapping(value = "listShippingCountry", method = RequestMethod.GET)
    public ModelAndView listShippingCountry(){
        ModelAndView mv = new ModelAndView("home/listShippingCountry");
        //查询出配送国家
        ShippingCountryQuery query = new ShippingCountryQuery();
        query.setDesc(false);
        query.setOrderBy("rank");
        List<ShippingCountryDomain> shippingCountryDomainList = shippingCountryService.getList(query);
        mv.addObject("countryList",shippingCountryDomainList);
        return mv;
    }

    @RequestMapping(value = "selectLanguage", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult selectLanguage(String nowLanguage){
        //判断当前语言环境
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        if("zh_CN".equals(nowLanguage)){
            session.setAttribute("language",nowLanguage);
        }else if("en_US".equals(nowLanguage)){
            session.setAttribute("language",nowLanguage);
        }else{
            return errorResult("参数有错");
        }
        return successResult("选择成功");
    }

}
