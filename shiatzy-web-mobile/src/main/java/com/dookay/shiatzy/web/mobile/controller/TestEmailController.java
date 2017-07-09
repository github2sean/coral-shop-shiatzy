package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.common.utils.RandomUtils;
import com.dookay.coral.shop.goods.service.IPrototypeSpecificationOptionService;
import com.dookay.coral.shop.order.domain.OrderDomain;
import com.dookay.coral.shop.order.domain.OrderItemDomain;
import com.dookay.coral.shop.order.domain.ReturnRequestDomain;
import com.dookay.coral.shop.order.domain.ReturnRequestItemDomain;
import com.dookay.coral.shop.order.query.OrderItemQuery;
import com.dookay.coral.shop.order.query.ReturnRequestItemQuery;
import com.dookay.coral.shop.order.service.IOrderItemService;
import com.dookay.coral.shop.order.service.IOrderService;
import com.dookay.coral.shop.order.service.IReturnRequestItemService;
import com.dookay.coral.shop.order.service.IReturnRequestService;
import com.dookay.shiatzy.web.mobile.base.MobileBaseController;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.dookay.coral.shop.message.util.EmailUtil;

import java.sql.Timestamp;
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
//            item.setSizeDomain(prototypeSpecificationOptionService.get(JSONObject.fromObject(line.getSkuSpecifications()).getLong("size")));
        }
        EmailUtil.applyReturn(isEn,userName,returnRequestDomain,requstList,10.0);
    }
}
