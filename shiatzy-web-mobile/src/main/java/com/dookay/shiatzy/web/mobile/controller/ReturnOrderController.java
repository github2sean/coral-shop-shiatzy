package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.common.web.BaseController;
import com.dookay.coral.common.web.HttpContext;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.host.user.context.UserContext;
import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.order.domain.OrderDomain;
import com.dookay.coral.shop.order.domain.OrderLogDomain;
import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;
import com.dookay.coral.shop.order.enums.ShoppingCartTypeEnum;
import com.dookay.coral.shop.order.query.OrderItemQuery;
import com.dookay.coral.shop.order.query.OrderQuery;
import com.dookay.coral.shop.order.service.IOrderItemService;
import com.dookay.coral.shop.order.service.IOrderLogService;
import com.dookay.coral.shop.order.service.IOrderService;
import com.dookay.coral.shop.order.service.IShoppingCartService;
import com.dookay.shiatzy.web.mobile.form.ReturnInfoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2017/4/25.
 */

@Controller
@RequestMapping("returnOrder/")
public class ReturnOrderController extends BaseController {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IOrderItemService orderItemService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IOrderLogService orderLogService;
    @Autowired
    private IShoppingCartService shoppingCartService;

    public static String CART_LIST = "cartList";
    public static String RETURN_ORDER = "return_order";


    @RequestMapping(value = "initReturnOrder",method = RequestMethod.GET)
    public String initReturnOrder(){


        return "redirect:orderInfo";
    }


    @RequestMapping(value = "fillReturnAddress" ,method = RequestMethod.GET)
    public ModelAndView fillReturnAddress(@ModelAttribute ReturnInfoForm returnInfoForm){


        ModelAndView mv = new ModelAndView("");
        return mv;
    }

    @RequestMapping(value = "chooseGoods" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult chooseGoods(){


        return successResult("操作成功");
    }

    @RequestMapping(value = "chooseReturnWay" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult chooseReturnWay(){


        return successResult("操作成功");
    }




    @RequestMapping(value = "applyReturn" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult applyReturn(Long orderId){
        OrderDomain orderDomain = orderService.get(orderId);
        //检查订单与用户是否匹配
        AccountDomain accountDomain = UserContext.current().getAccountDomain();
        CustomerDomain customerDomain = customerService.getAccount(accountDomain.getId());
        Long customerId = orderDomain.getCustomerId();
        if(customerDomain.getId()!=customerId){
            return errorResult("无权操作此订单");
        }
        //查询订单状态是否是已收货
        Integer status =  orderDomain.getStatus();
        if(status!=1){
            return errorResult("不能跨步操作订单");
        }
        //生成



        //生成操作订单日志
        OrderLogDomain orderLogDomain = new OrderLogDomain();
        orderLogDomain.setOrderId(orderId);
        orderLogDomain.setCreateTime(new Date());
        orderLogDomain.setAdminId(customerId);
        orderLogDomain.setMessage("买家申请退货");
        orderLogDomain.setIsSuccessed(1);
        orderLogService.create(orderLogDomain);

        return successResult("已发送申请");
    }





}
