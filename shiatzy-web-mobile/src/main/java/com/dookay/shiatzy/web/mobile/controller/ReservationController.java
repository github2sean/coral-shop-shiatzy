package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.web.BaseController;
import com.dookay.coral.common.web.HttpContext;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.host.user.context.UserContext;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.goods.service.IPrototypeSpecificationOptionService;
import com.dookay.coral.shop.order.domain.ReservationDomain;
import com.dookay.coral.shop.order.domain.ReservationItemDomain;
import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;
import com.dookay.coral.shop.order.query.ReservationItemQuery;
import com.dookay.coral.shop.order.query.ReservationQuery;
import com.dookay.coral.shop.order.query.ShoppingCartItemQuery;
import com.dookay.coral.shop.order.service.IReservationItemService;
import com.dookay.coral.shop.order.service.IReservationService;
import com.dookay.coral.shop.order.service.IReturnRequestItemService;
import com.dookay.coral.shop.order.service.IShoppingCartService;
import com.dookay.coral.shop.shipping.service.IShippingCountryService;
import com.dookay.coral.shop.store.domain.StoreCityDomain;
import com.dookay.coral.shop.store.domain.StoreCountryDomain;
import com.dookay.coral.shop.store.domain.StoreDomain;
import com.dookay.coral.shop.store.query.StoreCityQuery;
import com.dookay.coral.shop.store.query.StoreCountryQuery;
import com.dookay.coral.shop.store.query.StoreQuery;
import com.dookay.coral.shop.store.service.IStoreCityService;
import com.dookay.coral.shop.store.service.IStoreCountryService;
import com.dookay.coral.shop.store.service.IStoreService;
import com.dookay.shiatzy.web.mobile.model.PreOderItem;
import com.dookay.shiatzy.web.mobile.taglib.DefaultTags;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by admin on 2017/5/4.
 */

@Controller
@RequestMapping("reservation/")
public class ReservationController extends BaseController{

    @Autowired
    private IReservationService reservationService;
    @Autowired
    private IReservationItemService reservationItemService;

    @Autowired
    private IReturnRequestItemService returnRequestItemService;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IStoreService storeService;
    @Autowired
    private IStoreCityService storeCityService;
    @Autowired
    private IStoreCountryService storeCountryService;
    @Autowired
    private IShoppingCartService shoppingCartService;
    @Autowired
    private IPrototypeSpecificationOptionService prototypeSpecificationOptionService;
    @Autowired
    private IShippingCountryService shippingCountryService;


    public static int RESERVATION_TYPE=3;


    /**
     * 预约单列表
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public ModelAndView list() throws UnsupportedEncodingException {
        ModelAndView loginRef = getModelAndView();
        if (loginRef != null) return loginRef;
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        //预约单查询
        ReservationQuery query3 = new ReservationQuery();
        query3.setCustomerId(customerDomain.getId());
        List reservationList  = reservationService.getList(query3);

        ModelAndView mv  =  new ModelAndView("user/reservation/list");
        mv.addObject("reservationList",reservationList);
        return mv;
    }


    @RequestMapping(value = "details",method = RequestMethod.GET)
    public ModelAndView details(Long reservationId) throws UnsupportedEncodingException {
        ModelAndView loginRef = getModelAndView();
        if (loginRef != null) return loginRef;
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        //预约单查询
        ReservationDomain reservationDomain = reservationService.get(reservationId);
        Long storeId = Long.parseLong(reservationDomain.getStoreTitle());
        reservationDomain.setStoreDomain(storeService.get(storeId));
        ReservationItemQuery query = new ReservationItemQuery();
        query.setReservationId(reservationId);
        List<ReservationItemDomain> reservationList  = reservationItemService.getList(query);
        Long sizeId = null;
        for (ReservationItemDomain returnItem:reservationList){
            JSONObject jsonObject = JSONObject.fromObject(returnItem.getSpecifications());
            sizeId = Long.parseLong(""+jsonObject.get("size"));
            returnItem.setSizeDomain(prototypeSpecificationOptionService.get(sizeId));
        }
        shoppingCartService.withReservationItem(reservationList);
        reservationDomain.setReservationItemDomainList(reservationList);
        ModelAndView mv  =  new ModelAndView("user/reservation/details");
        mv.addObject("reservationDomain",reservationDomain);
        return mv;
    }

    private ModelAndView getModelAndView() throws UnsupportedEncodingException {
        if(UserContext.current() == null){
            String loginRef = "/passport/toLogin?ref=" + URLEncoder.encode(HttpContext.current().getRequest().getServletPath(), "UTF-8");
            return new ModelAndView("redirect:"+loginRef);
        }
        return null;
    }


    @RequestMapping(value = "initChoose" ,method = RequestMethod.GET)
    public ModelAndView initChoose() throws UnsupportedEncodingException {
        ModelAndView loginRef = getModelAndView();
        if (loginRef != null) return loginRef;
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        List<StoreCountryDomain> storeCountryList  = storeCountryService.getList(new StoreCountryQuery());
        ModelAndView mv = new ModelAndView("user/reservation/initChoose");
        ShoppingCartItemQuery query = new ShoppingCartItemQuery();
        query.setCustomerId(customerDomain.getId());
        query.setShoppingCartType(RESERVATION_TYPE);
        List<ShoppingCartItemDomain> cartList = shoppingCartService.getList(query);
        shoppingCartService.withGoodsItem(cartList);
        for (ShoppingCartItemDomain line : cartList) {
            line.setSizeDomain(prototypeSpecificationOptionService.get(JSONObject.fromObject(line.getSkuSpecifications()).getLong("size")));
        }
        mv.addObject("storeCountryList",storeCountryList);
        session.setAttribute("submitCartList",cartList);
        return mv;
    }

    @RequestMapping(value = "chooseCity" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult chooseCity(String countryId){
        StoreCityQuery query = new StoreCityQuery();
        query.setCountryId(countryId);
        List<StoreCityDomain>  storeCityList = storeCityService.getList(query);
        return successResult("初始化城市", JsonUtils.toJSONString(storeCityList));
    }

    @RequestMapping(value = "chooseStore" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult chooseStore(String countryId,String cityId){
        StoreQuery query = new StoreQuery();
        query.setCountryId(countryId);
        query.setCityId(cityId);
        List<StoreDomain> storeList = storeService.getList(query);
        return successResult("初始化门店",JsonUtils.toJSONString(storeList));
    }

    @RequestMapping(value = "submitPreOrder" ,method = RequestMethod.POST)
    @ResponseBody
    public  JsonResult submitPreOrder(Long storeId)  {
        if(UserContext.current() == null)
            return errorResult(DefaultTags.translate("预约单过期","Session expired"));

        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        HttpSession session = HttpContext.current().getRequest().getSession();

        if(storeId==null){
            return errorResult(DefaultTags.translate("请选择门店","Please choose store"));
        }
        StoreDomain storeDomain = storeService.get(storeId);
        if(storeDomain==null){
            return errorResult(DefaultTags.translate("无此门店","Error store"));
        }
        //判断传入商品ID是否在精品店中
        List<ShoppingCartItemDomain> cartList = (List<ShoppingCartItemDomain>)session.getAttribute("submitCartList");
        if(!(cartList!=null&&cartList.size()>0)){
            return errorResult(DefaultTags.translate("预约单过期","Session expired"));
        }
        Long reservationDomainId =  reservationService.submit(cartList,customerDomain,storeDomain);
        //清空session
        session.setAttribute("submitCartList",null);
        return successResult(DefaultTags.translate("提交成功","Send successfully"),reservationDomainId);
    }



}
