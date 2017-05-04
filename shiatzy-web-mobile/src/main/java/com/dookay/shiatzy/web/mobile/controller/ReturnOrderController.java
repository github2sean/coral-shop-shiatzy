package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.web.BaseController;
import com.dookay.coral.common.web.HttpContext;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.host.user.context.UserContext;
import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.shop.customer.domain.CustomerAddressDomain;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.query.CustomerAddressQuery;
import com.dookay.coral.shop.customer.service.ICustomerAddressService;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.order.domain.*;
import com.dookay.coral.shop.order.enums.ShoppingCartTypeEnum;
import com.dookay.coral.shop.order.query.OrderItemQuery;
import com.dookay.coral.shop.order.query.ReturnRequestItemQuery;
import com.dookay.coral.shop.order.service.*;
import com.dookay.shiatzy.web.mobile.form.ReturnInfoForm;
import com.dookay.shiatzy.web.mobile.model.ChooseGoodsModel;
import com.dookay.shiatzy.web.mobile.model.ReturnReasonModel;
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
import java.util.HashMap;
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
    @Autowired
    private ICustomerAddressService customerAddressService;
    @Autowired
    private IReturnRequestService returnRequestService;
    @Autowired
    private IReturnRequestItemService returnRequestItemService;

    public static String CART_LIST = "cartList";
    public static String RETURN_ORDER = "return_order";
    public static String ORDER = "order";
    /**
     * 初始化退货页面
     * @return
     */

    @RequestMapping(value = "details" ,method = RequestMethod.GET)
    public ModelAndView details(Long orderId){
        ReturnRequestDomain returnRequestDomain = returnRequestService.get(orderId);
        ReturnRequestItemQuery query = new ReturnRequestItemQuery();
        query.setReturnRequestId(returnRequestDomain.getId());
        List returnOrderItemList  = returnRequestItemService.getList(query);
        ModelAndView mv = new ModelAndView("user/returnOrder/details");
        mv.addObject("returnRequestDomain",returnRequestDomain);
        mv.addObject("returnOrderItemList",returnOrderItemList);
        return mv;
    }



    @RequestMapping(value = "initReturnOrder",method = RequestMethod.GET)
    public String initReturnOrder(Long orderId){

        //从购物获取商品列表
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);

        OrderItemQuery query = new OrderItemQuery();
        query.setOrderId(orderId);
        List<OrderItemDomain> cartList  = orderItemService.getList(query);
        OrderDomain orderDomain = orderService.get(orderId);
        //创建订单对象
        ReturnRequestDomain returnRequestDomain = new ReturnRequestDomain();
        returnRequestDomain.setCustomerId(customerDomain.getId());
        returnRequestDomain.setCreateTime(new Date());
        returnRequestDomain.setOrderId(orderDomain.getId());
        returnRequestDomain.setOrderNo(orderDomain.getOrderNo());
        returnRequestDomain.setShipName(customerDomain.getFirstName()+customerDomain.getLastName());
        returnRequestDomain.setOrderTime(orderDomain.getOrderTime());
        //保存订单对象到session
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(CART_LIST,cartList);
        session.setAttribute(ORDER,orderDomain);
        session.setAttribute(RETURN_ORDER,returnRequestDomain);
        //跳转到填写信息页面
        return "redirect:returnOrderInfo";
    }

    @RequestMapping(value = "returnOrderInfo" ,method = RequestMethod.GET)
    public ModelAndView returnOrderInfo(){
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        //如果session为空，跳转到商品列表页面
        List cartList = (List) session.getAttribute(CART_LIST);
        OrderDomain orderDomain = (OrderDomain)session.getAttribute(ORDER);
        if(cartList==null || orderDomain==null){
            return new ModelAndView("redirect:home/index");
        }
        ModelAndView mv = new ModelAndView("user/returnOrder/returnOrderInfo");
        mv.addObject(CART_LIST,cartList);
        mv.addObject(ORDER,orderDomain);
        return mv;
    }

    @RequestMapping(value = "returnOrderConsigneeInfo" ,method = RequestMethod.GET)
    public ModelAndView returnOrderConsigneeInfo(){
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        //判断是否选择有退货商品
        List<OrderItemDomain> list = (List<OrderItemDomain>) session.getAttribute(CART_LIST);
        if(!(list!=null && list.size()>0)){
            return new ModelAndView("redirect:returnOrderInfo");
        }
        ModelAndView mv = new ModelAndView("user/returnOrder/returnOrderConsigneeInfo");
        return mv;
    }


    /**
     * 查询退货地址
     *
     * @return
     */
    @RequestMapping(value = "listAddress" ,method = RequestMethod.GET)
    public ModelAndView listAddress(@ModelAttribute ReturnInfoForm returnInfoForm){
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        CustomerAddressQuery query = new CustomerAddressQuery();
        query.setCustomerId(customerDomain.getId());
        List addressList = customerAddressService.getList(query);
        ModelAndView mv = new ModelAndView("user/returnOrder/listAddress");
        mv.addObject("addressList",addressList);
        return mv;
    }

    /**
     * 选择退货商品和退货理由
     *
     * @return
     */

    @RequestMapping(value = "chooseGoodsAndReason" ,method = RequestMethod.POST)
    public String chooseGoods(@ModelAttribute ReturnInfoForm returnInfoForm){
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        HashMap jsonMap = new HashMap();
        List<ReturnReasonModel> reasonModels = returnInfoForm.getReturnList();
        List<OrderItemDomain> list = (List<OrderItemDomain>)session.getAttribute(CART_LIST);
        List<OrderItemDomain> newList = new ArrayList<OrderItemDomain>();
             for(int i=0;i<list.size();i++){
                 for(ReturnReasonModel line:reasonModels){
                     if(list.get(i).getId()==line.getOrderItemId()){
                         newList.add(list.get(i));
                         jsonMap.put(line.getOrderItemId()+"",JsonUtils.toJSONString(line));
                     }
                 }
             }
        session.setAttribute(CART_LIST,newList);
        session.setAttribute("returnJsonReason",jsonMap);
        return "redirect:returnOrderConsigneeInfo";
    }


    @RequestMapping(value = "sureReturnWay" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult sureReturnWay(Integer backWay,Long addressId){
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        ReturnRequestDomain returnRequest = (ReturnRequestDomain)session.getAttribute(RETURN_ORDER);
        CustomerAddressDomain customerAddress = customerAddressService.get(addressId);
        if(customerAddress==null){
            return errorResult("选择失败，无此地址");
        }
        returnRequest.setReturnShippingMethod(backWay);
        if(backWay==1){
            returnRequest.setShipAddress(customerAddress.getAddress());
        }else if(backWay==2){
            returnRequest.setReturnShopId(addressId);
        }
        session.setAttribute("customerAddress",customerAddress);
        session.setAttribute("backWay",backWay);
        session.setAttribute(RETURN_ORDER,returnRequest);
        return successResult("操作成功");
    }

    /**
     * 提交退货申请
     *
     * @return
     */


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
        if(status!=4){
            return errorResult("不能跨步操作订单");
        }
        //查寻session是为空
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();

        ReturnRequestDomain returnRequestDomain = (ReturnRequestDomain)session.getAttribute(RETURN_ORDER);
        List<OrderItemDomain> orderItemDomains = (List<OrderItemDomain>)session.getAttribute(CART_LIST);

        if(returnRequestDomain==null||orderItemDomains==null){
            return errorResult("操作失败");
        }

        //生成退货单和退货单商品
        returnRequestService.create(returnRequestDomain);
        HashMap map =  (HashMap) session.getAttribute("returnJsonReason");
        for(OrderItemDomain line:orderItemDomains){
            ReturnRequestItemDomain returnRequestItemDomain = new ReturnRequestItemDomain();
            returnRequestItemDomain.setReturnRequestId(returnRequestDomain.getId());
            returnRequestItemDomain.setOrderItemId(line.getId()+"");
            returnRequestItemDomain.setCustomerId(customerId);
            returnRequestItemDomain.setNum( Integer.parseInt(line.getNum()+""));
            returnRequestItemDomain.setCreateTime(new Date());
            returnRequestItemDomain.setStatus(1);
            returnRequestItemDomain.setReturnReason(map.get(line.getId()+"")+"");
            returnRequestItemService.create(returnRequestItemDomain);
        }

        //生成操作订单日志
        OrderLogDomain orderLogDomain = new OrderLogDomain();
        orderLogDomain.setOrderId(orderId);
        orderLogDomain.setCreateTime(new Date());
        orderLogDomain.setAdminId(customerId);
        orderLogDomain.setMessage("买家申请退货");
        orderLogDomain.setIsSuccessed(1);
        orderLogService.create(orderLogDomain);

        return successResult("已发送申请",returnRequestDomain.getId());
    }





}
