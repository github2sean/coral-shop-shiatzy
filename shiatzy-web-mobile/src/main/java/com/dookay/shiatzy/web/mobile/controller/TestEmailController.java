package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.common.utils.RandomUtils;
import com.dookay.coral.shop.goods.service.IGoodsItemService;
import com.dookay.coral.shop.goods.service.IPrototypeSpecificationOptionService;
import com.dookay.coral.shop.order.domain.*;
import com.dookay.coral.shop.order.query.OrderItemQuery;
import com.dookay.coral.shop.order.query.ReservationItemQuery;
import com.dookay.coral.shop.order.query.ReturnRequestItemQuery;
import com.dookay.coral.shop.order.service.*;
import com.dookay.coral.shop.store.service.IStoreService;
import com.dookay.shiatzy.web.mobile.base.MobileBaseController;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.dookay.coral.shop.message.util.EmailUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by 磊 on 2017/7/9.
 */
@Controller
@RequestMapping("test/email")
public class TestEmailController extends MobileBaseController {

    String userName = "425880812@qq.com";

    @Autowired
    private IOrderItemService orderItemService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IReturnRequestService returnRequestService;

    @Autowired
    private IReturnRequestItemService returnRequestItemService;

    @Autowired
    private IPrototypeSpecificationOptionService prototypeSpecificationOptionService;

    @Autowired
    private IReservationService reservationService;

    @Autowired
    private IReservationItemService reservationItemService;

    @Autowired
    private IGoodsItemService goodsItemService;

    @Autowired
    private IStoreService storeService;


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public void register(Boolean isEn) throws Exception {

        EmailUtil.Register(userName, isEn);
    }

    @RequestMapping(value = "/forgetPassword", method = RequestMethod.GET)
    public void forgetPassword(Boolean isEn) throws Exception {
        String secretKey = UUID.randomUUID().toString();//密钥
        Timestamp outDate = new Timestamp(System.currentTimeMillis() + 30 * 60 * 1000);//30分钟后过期
        long date = outDate.getTime() / 1000 * 1000;//忽略毫秒数
        String newPass = RandomUtils.randomNumbers(6);
        EmailUtil.forgetPwd(userName, date, secretKey, newPass, isEn);
    }

    @RequestMapping(value = "/sendInformation/{id}", method = RequestMethod.GET)
    public void sendInformation(@PathVariable Long id, Boolean isEn) throws Exception {
        OrderDomain order=orderService.get(id);
        OrderItemQuery orderItemquery = new OrderItemQuery();
        orderItemquery.setOrderId(order.getId());
        List<OrderItemDomain> orderItemDomainList = orderItemService.getList(orderItemquery);
        EmailUtil.sendInformation(isEn,userName,order,orderItemDomainList);
    }

    @RequestMapping(value = "/sendGoods/{id}", method = RequestMethod.GET)
    public void sendGoods(@PathVariable Long id, Boolean isEn) throws Exception{
        OrderDomain order=orderService.get(id);
        OrderItemQuery orderItemquery = new OrderItemQuery();
        orderItemquery.setOrderId(order.getId());
        List<OrderItemDomain> orderItemDomainList = orderItemService.getList(orderItemquery);
        EmailUtil.SendGoods(isEn,userName,order,orderItemDomainList);
    }

    @RequestMapping(value = "/applyReturn/{id}", method = RequestMethod.GET)
    public void applyReturn(@PathVariable Long id, Boolean isEn) throws Exception{
        ReturnRequestDomain returnRequestDomain = returnRequestService.get(id);
        ReturnRequestItemQuery query=new ReturnRequestItemQuery();
        query.setReturnRequestId(id);
        List<ReturnRequestItemDomain>requstList=returnRequestItemService.getList(query);
        for (ReturnRequestItemDomain item : requstList){
           item.setSizeDomain(prototypeSpecificationOptionService.get(JSONObject.fromObject(item.getSkuSpecifications()).getLong("size")));
        }
        EmailUtil.applyReturn(isEn,userName,returnRequestDomain,requstList,10.0);
    }

    @RequestMapping(value = "/submitOrder/{id}", method = RequestMethod.GET)
    public void submitOrder(@PathVariable Long id, Boolean isEn) throws Exception{
        ReservationDomain reservationDomain=reservationService.get(id);
        reservationDomain.setStoreDomain(storeService.get(Long.parseLong(reservationDomain.getStoreTitle())));
        ReservationItemQuery query=new ReservationItemQuery();
        query.setReservationId(id);
        List<ReservationItemDomain> requestList =reservationItemService.getList(query);
        for (ReservationItemDomain item:requestList){
            item.setSizeDomain(prototypeSpecificationOptionService.get(JSONObject.fromObject(item.getSpecifications()).getLong("size")));
            item.setGoodsItemDomain(goodsItemService.get(item.getItemId()));
        }
        EmailUtil.submitOrder(isEn,userName,reservationDomain,requestList,10.0);
    }
}
