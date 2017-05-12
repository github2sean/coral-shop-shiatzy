package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.common.web.BaseController;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.host.user.context.UserContext;
import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.domain.GoodsItemDomain;
import com.dookay.coral.shop.goods.query.GoodsItemQuery;
import com.dookay.coral.shop.goods.query.GoodsQuery;
import com.dookay.coral.shop.goods.service.IGoodsItemService;
import com.dookay.coral.shop.order.domain.OrderDomain;
import com.dookay.coral.shop.order.domain.OrderItemDomain;
import com.dookay.coral.shop.order.domain.OrderLogDomain;
import com.dookay.coral.shop.order.domain.ReservationDomain;
import com.dookay.coral.shop.order.query.*;
import com.dookay.coral.shop.order.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by admin on 2017/4/25.
 */

@Controller
@RequestMapping("order/")
public class OrderController extends BaseController {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IOrderItemService orderItemService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IOrderLogService orderLogService;
    @Autowired
    private IReturnRequestService returnRequestService;
    @Autowired
    private IReservationService reservationService;
    @Autowired
    private IGoodsItemService goodsItemService;

    @RequestMapping(value = "list" ,method = RequestMethod.GET)
    public ModelAndView list(){
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        OrderQuery query = new OrderQuery();
        query.setCustomerId(customerDomain.getId());
        List orderList = orderService.getList(query);

        //退货单
        ReturnRequestQuery query2 = new ReturnRequestQuery();
        query2.setCustomerId(customerDomain.getId());
        List returnList= returnRequestService.getList(query2);
        //预约单查询
        ReservationQuery query3 = new ReservationQuery();
        query3.setCustomerId(customerDomain.getId());
        List reservationList  = reservationService.getList(query3);

        ModelAndView mv = new ModelAndView("user/order/list");
        mv.addObject("orderList",orderList);
        mv.addObject("returnList",returnList);
        mv.addObject("reservationList",reservationList);
        return mv;
    }

    @RequestMapping(value = "details" ,method = RequestMethod.GET)
    public ModelAndView details(Long orderId){
        OrderDomain orderDomain = orderService.get(orderId);
        OrderItemQuery query = new OrderItemQuery();
        query.setOrderId(orderDomain.getId());
        List<OrderItemDomain> orderItemList  = orderItemService.getList(query);

        List<Long> ids = orderItemList.stream().map(OrderItemDomain::getItemId).collect(Collectors.toList());
        GoodsItemQuery goodsItemQuery = new GoodsItemQuery();
        goodsItemQuery.setIds(ids);
        List<GoodsItemDomain> goodsItemDomainList = goodsItemService.getList(goodsItemQuery);
        //退货数量等于订单的数量不可在退货
        Integer orderNum = 0;
        Integer returnNum = 0;
        for (OrderItemDomain orderItemDomain:orderItemList){
            GoodsItemDomain goodsItemDomain = goodsItemDomainList.stream()
                    .filter(x-> Objects.equals(x.getId(), orderItemDomain.getItemId())).findFirst().orElse(null);
            orderItemDomain.setGoodsItemDomain(goodsItemDomain);
            orderNum += orderItemDomain.getNum();
            returnNum += orderItemDomain.getReturnNum();
        }
        orderDomain.setCanReturnNum(orderNum-returnNum);
        ModelAndView mv = new ModelAndView("user/order/details");
        mv.addObject("orderDomain",orderDomain);
        mv.addObject("orderItemList",orderItemList);
        return mv;
    }


    @RequestMapping(value = "receipt" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult receipt(Long orderId){
        OrderDomain orderDomain = orderService.get(orderId);
        //检查订单与用户是否匹配
        AccountDomain accountDomain = UserContext.current().getAccountDomain();
        CustomerDomain customerDomain = customerService.getAccount(accountDomain.getId());
        Long customerId = orderDomain.getCustomerId();
        if(customerDomain.getId()!=customerId){
            return errorResult("无权操作此订单");
        }
        //查询订单状态是否是已发货
        Integer status =  orderDomain.getStatus();
        if(status!=3){
            return errorResult("不能跨步操作订单");
        }
        //修改状态
        orderDomain.setStatus(4);
        orderService.update(orderDomain);
        //生成操作订单日志
        OrderLogDomain orderLogDomain = new OrderLogDomain();
        orderLogDomain.setOrderId(orderId);
        orderLogDomain.setCreateTime(new Date());
        orderLogDomain.setAdminId(customerId);
        orderLogDomain.setMessage("买家确认收货");
        orderLogDomain.setIsSuccessed(1);
        orderLogService.create(orderLogDomain);
        return successResult("已收货");
    }

    @RequestMapping(value = "cancel" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult cancel(Long orderId){
        OrderDomain orderDomain = orderService.get(orderId);
        //检查订单与用户是否匹配
        AccountDomain accountDomain = UserContext.current().getAccountDomain();
        CustomerDomain customerDomain = customerService.getAccount(accountDomain.getId());
        Long customerId = orderDomain.getCustomerId();
        if(customerDomain.getId()!=customerId){
            return errorResult("无权操作此订单");
        }
        //查询订单状态是否是待支付
        Integer status =  orderDomain.getStatus();
        if(status!=1){
            return errorResult("不能跨步操作订单");
        }
        //修改状态
        orderDomain.setStatus(-1);
        orderService.update(orderDomain);
        //生成操作订单日志
        OrderLogDomain orderLogDomain = new OrderLogDomain();
        orderLogDomain.setOrderId(orderId);
        orderLogDomain.setCreateTime(new Date());
        orderLogDomain.setAdminId(customerId);
        orderLogDomain.setMessage("买家取消订单");
        orderLogDomain.setIsSuccessed(1);
        orderLogService.create(orderLogDomain);
        return successResult("已取消");
    }

}
