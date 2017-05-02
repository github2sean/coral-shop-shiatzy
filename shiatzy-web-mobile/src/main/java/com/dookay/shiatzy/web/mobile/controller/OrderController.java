package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.host.user.context.UserContext;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.order.domain.OrderDomain;
import com.dookay.coral.shop.order.domain.OrderItemDomain;
import com.dookay.coral.shop.order.query.OrderItemQuery;
import com.dookay.coral.shop.order.query.OrderQuery;
import com.dookay.coral.shop.order.service.IOrderItemService;
import com.dookay.coral.shop.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by admin on 2017/4/25.
 */

@Controller
@RequestMapping("order/")
public class OrderController {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IOrderItemService orderItemService;
    @Autowired
    private ICustomerService customerService;



    @RequestMapping(value = "list" ,method = RequestMethod.GET)
    public ModelAndView list(){
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        OrderQuery query = new OrderQuery();
        query.setCustomerId(customerDomain.getId());
        List orderList = orderService.getList(query);
        ModelAndView mv = new ModelAndView("user/order/list");
        mv.addObject("orderList",orderList);
        return mv;
    }

    @RequestMapping(value = "details" ,method = RequestMethod.GET)
    public ModelAndView details(Long orderId){
        OrderDomain orderDomain = orderService.get(orderId);
        OrderItemQuery query = new OrderItemQuery();
        query.setOrderId(orderDomain.getId());
        List orderItemList  = orderItemService.getList(query);
        ModelAndView mv = new ModelAndView("user/order/details");
        mv.addObject("orderDomain",orderDomain);
        mv.addObject("orderItemList",orderItemList);
        return mv;
    }


}
