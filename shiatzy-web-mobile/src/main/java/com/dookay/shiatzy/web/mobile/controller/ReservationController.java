package com.dookay.shiatzy.web.mobile.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.utils.RandomUtils;
import com.dookay.coral.common.web.BaseController;
import com.dookay.coral.common.web.HttpContext;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.host.user.context.UserContext;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.order.domain.ReservationDomain;
import com.dookay.coral.shop.order.domain.ReservationItemDomain;
import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;
import com.dookay.coral.shop.order.enums.ShoppingCartTypeEnum;
import com.dookay.coral.shop.order.query.ReservationItemQuery;
import com.dookay.coral.shop.order.query.ReservationQuery;
import com.dookay.coral.shop.order.query.ShoppingCartItemQuery;
import com.dookay.coral.shop.order.service.IReservationItemService;
import com.dookay.coral.shop.order.service.IReservationService;
import com.dookay.coral.shop.order.service.IReturnRequestItemService;
import com.dookay.coral.shop.order.service.IShoppingCartService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public static int RESERVATION_TYPE=3;

/**
 * 预约单列表
 */

    @RequestMapping(value = "list",method = RequestMethod.GET)
    public ModelAndView list(){
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
    public ModelAndView details(Long reservationId){
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        //预约单查询
        ReservationDomain reservationDomain = reservationService.get(reservationId);
        Long storeId = Long.parseLong(reservationDomain.getStoreTitle());
        reservationDomain.setStoreDomain(storeService.get(storeId));
        ReservationItemQuery query = new ReservationItemQuery();
        query.setReservationId(reservationId);
        List<ReservationItemDomain> reservationList  = reservationItemService.getList(query);
        shoppingCartService.withReservationItem(reservationList);
        reservationDomain.setReservationItemDomainList(reservationList);
        ModelAndView mv  =  new ModelAndView("user/reservation/details");
        mv.addObject("reservationDomain",reservationDomain);
        return mv;
    }



    @RequestMapping(value = "initChoose" ,method = RequestMethod.GET)
    public ModelAndView initChoose(){
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
        List<PreOderItem> preOderItemList = new ArrayList<PreOderItem>();
        for (int i=0;cartList!=null&&cartList.size()>0 && i<cartList.size();i++){
            if(i!=0&&i%2!=0){
                continue;
            }
            PreOderItem preOderItem = i+1<cartList.size()?new PreOderItem(cartList.get(i),cartList.get(i+1)):new PreOderItem(cartList.get(i),null);
            preOderItemList.add(preOderItem);
        }
        mv.addObject("storeCountryList",storeCountryList);
        mv.addObject("preOderItemList",preOderItemList);
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
    public  JsonResult submitPreOrder(Long storeId){
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        if(storeId==null){
            return errorResult("请选择门店");
        }
        StoreDomain storeDomain = storeService.get(storeId);
        if(storeDomain==null){
            return errorResult("无此门店");
        }
        ReservationDomain reservationDomain = new ReservationDomain();
        reservationDomain.setCreateTime(new Date());
        reservationDomain.setRank(1);
        reservationDomain.setIsVisible(0);
        reservationDomain.setStatus(0);
        reservationDomain.setCustomerId(customerDomain.getId());
        reservationDomain.setReservationNo(RandomUtils.buildNo());
        reservationDomain.setStoreTitle(storeDomain.getId()+"");
        reservationDomain.setTel(storeDomain.getTel());
        reservationDomain.setAddress(storeDomain.getAddress());
        reservationDomain.setTime(storeDomain.getTime());
        reservationDomain.setNote("");
        reservationDomain.setUpdateTime(new Date());
        reservationService.create(reservationDomain);

        //判断传入商品ID是否在精品店中
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        List<ShoppingCartItemDomain> cartList = (List<ShoppingCartItemDomain>)session.getAttribute("submitCartList");
        for (ShoppingCartItemDomain line:cartList){
            ReservationItemDomain reservationItemDomain = new ReservationItemDomain();
            reservationItemDomain.setRank(1);
            reservationItemDomain.setIsVisible(1);
            reservationItemDomain.setReservationId(reservationDomain.getId());
            reservationItemDomain.setGoodsName(line.getGoodsName());
            reservationItemDomain.setSkuCode(line.getSkuId()+"");
            reservationItemDomain.setNum(line.getNum());
            reservationItemDomain.setItemId(line.getItemId());
            reservationItemDomain.setSpecifications(line.getSkuSpecifications());
            reservationItemDomain.setCreateTime(new Date());
            reservationItemDomain.setUpdateTime(new Date());
            reservationItemDomain.setStatus(0);
            reservationItemService.create(reservationItemDomain);
            //从精品店中移除
            shoppingCartService.removeFromCart(line.getId());
        }
        //清空session
        session.setAttribute("submitCartList",null);
        return successResult("提交成功",reservationDomain.getId());
    }



}
