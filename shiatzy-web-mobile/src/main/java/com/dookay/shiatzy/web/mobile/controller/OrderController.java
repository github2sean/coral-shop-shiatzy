package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.adapter.express.KdniaoSubscribeAPI;
import com.dookay.coral.common.persistence.pager.PageList;
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
import com.dookay.coral.shop.goods.service.IGoodsService;
import com.dookay.coral.shop.goods.service.IPrototypeSpecificationOptionService;
import com.dookay.coral.shop.message.enums.MessageTypeEnum;
import com.dookay.coral.shop.message.service.ISmsService;
import com.dookay.coral.shop.order.domain.*;
import com.dookay.coral.shop.order.query.*;
import com.dookay.coral.shop.order.service.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
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
    private IReturnRequestItemService returnRequestItemService;
    @Autowired
    private IReservationService reservationService;
    @Autowired
    private IGoodsItemService goodsItemService;
    @Autowired
    private KdniaoSubscribeAPI kdniaoSubscribeAPI;
    @Autowired
    private IPrototypeSpecificationOptionService prototypeSpecificationOptionService;
    @Autowired
    private ISmsService smsService;
    @Autowired
    private IGoodsService goodsService;

    @RequestMapping(value = "list" ,method = RequestMethod.GET)
    public ModelAndView list(){
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        OrderQuery query = new OrderQuery();
        query.setCustomerId(customerDomain.getId());
        PageList<OrderDomain> orderList = orderService.getPageList(query);
        ModelAndView mv = new ModelAndView("user/order/list");
        mv.addObject("orderList",orderList);
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
        //退货数量等于订单的数量不可再退货
        Integer orderNum = 0;
        Integer returnNum = 0;
        GoodsQuery goodsQuery = new GoodsQuery();
        for (OrderItemDomain orderItemDomain:orderItemList){
            GoodsItemDomain goodsItemDomain = goodsItemDomainList.stream()
                    .filter(x-> Objects.equals(x.getId(), orderItemDomain.getItemId())).findFirst().orElse(null);
            orderItemDomain.setGoodsItemDomain(goodsItemDomain);
            orderNum += orderItemDomain.getNum();
            returnNum += orderItemDomain.getReturnNum();
            JSONObject jsonObject  = JSONObject.fromObject(orderItemDomain.getSkuSpecifications());
            orderItemDomain.setSizeDomain(prototypeSpecificationOptionService.get(Long.parseLong(""+jsonObject.get("size"))));
            goodsQuery.setCode(orderItemDomain.getGoodsCode());
            orderItemDomain.setGoodsDomain(goodsService.getFirst(goodsQuery));
        }
        //判断该订单是否有对应退货单
        ReturnRequestQuery backQuery = new ReturnRequestQuery();
        query.setOrderId(orderDomain.getId());
        ReturnRequestDomain returnRequestDomain = returnRequestService.getFirst(query);
        if(returnRequestDomain!=null){
            orderDomain.setReturnRequestDomain(returnRequestDomain);
            ReturnRequestItemQuery returnRequestItemQuery = new ReturnRequestItemQuery();
            returnRequestItemQuery.setReturnRequestId(returnRequestDomain.getId());
            orderDomain.setReturnRequestItemList(returnRequestItemService.getList(returnRequestItemQuery));
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
        //发送短信
        smsService.sendToSms(orderDomain.getShipPhone(), MessageTypeEnum.CANCEL_ORDER.getValue());
        //发送邮件  TODO: 2017/6/15

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


    /***
     * 查看快递状态
     */
    @RequestMapping(value = "queryExpress",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult queryExpress(Long orderId) throws Exception {

        OrderDomain orderDomain = orderService.get(orderId);
        OrderItemQuery query = new OrderItemQuery();
        query.setOrderId(orderDomain.getId());
        String requestData="{'ShipperCode':'"+orderDomain.getShipperCompany()+"'," +
                "'LogisticCode':'"+orderDomain.getTrackingNumber()+"'}";
        String returnStr =  kdniaoSubscribeAPI.orderTracesSubByJson(requestData);
        return successResult("查询成功",returnStr);
    }

}
