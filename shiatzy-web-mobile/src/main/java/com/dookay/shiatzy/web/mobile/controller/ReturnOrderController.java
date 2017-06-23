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
import com.dookay.coral.shop.goods.query.GoodsQuery;
import com.dookay.coral.shop.goods.service.IGoodsService;
import com.dookay.coral.shop.goods.service.IPrototypeSpecificationOptionService;
import com.dookay.coral.shop.message.enums.MessageTypeEnum;
import com.dookay.coral.shop.message.service.ISmsService;
import com.dookay.coral.shop.order.domain.*;
import com.dookay.coral.shop.order.enums.ShoppingCartTypeEnum;
import com.dookay.coral.shop.order.query.OrderItemQuery;
import com.dookay.coral.shop.order.query.ReturnRequestItemQuery;
import com.dookay.coral.shop.order.service.*;
import com.dookay.coral.shop.store.domain.StoreCountryDomain;
import com.dookay.coral.shop.store.domain.StoreDomain;
import com.dookay.coral.shop.store.query.StoreCountryQuery;
import com.dookay.coral.shop.store.query.StoreQuery;
import com.dookay.coral.shop.store.service.IStoreCountryService;
import com.dookay.coral.shop.store.service.IStoreService;
import com.dookay.shiatzy.web.mobile.form.ReturnInfoForm;
import com.dookay.shiatzy.web.mobile.model.ChooseGoodsModel;
import com.dookay.shiatzy.web.mobile.model.ReturnReasonModel;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.NamedBean;
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
    @Autowired
    private IStoreService storeService;
    @Autowired
    private IStoreCountryService storeCountryService;
    @Autowired
    private IPrototypeSpecificationOptionService prototypeSpecificationOptionService;
    @Autowired
    private ISmsService smsService;
    @Autowired
    private IGoodsService goodsService;

    private static String CART_LIST = "cartList";
    private static String RETURN_ORDER = "return_order";
    private static String ORDER = "order";
    private static String BACK_WAY = "backWay";
    /**
     * 初始化退货页面
     * @return
     */

    @RequestMapping(value = "details" ,method = RequestMethod.GET)
    public ModelAndView details(Long orderId){
        ReturnRequestDomain returnRequestDomain = returnRequestService.get(orderId);
        ReturnRequestItemQuery query = new ReturnRequestItemQuery();
        query.setReturnRequestId(returnRequestDomain.getId());
        List<ReturnRequestItemDomain> returnOrderItemList  = returnRequestItemService.getList(query);
        Double preBackMoney = 0D;
        OrderDomain orderDomain = orderService.getOrder(returnRequestDomain.getOrderNo());
        Double fee = orderDomain.getShipFee();
        Double dis = orderDomain.getCouponDiscount();
        dis = dis==null?0D:dis;
        for(ReturnRequestItemDomain line:returnOrderItemList){
            preBackMoney += line.getGoodsPrice()*line.getNum();
            line.setSizeDomain(prototypeSpecificationOptionService.get(Long.parseLong(""+ JSONObject.fromObject(line.getSkuSpecifications()).get("size"))));
        }
        preBackMoney = preBackMoney-dis;
        returnRequestItemService.withGoodsItem(returnOrderItemList);
        ModelAndView mv = new ModelAndView("user/returnOrder/details");
        mv.addObject("returnRequestDomain",returnRequestDomain);
        mv.addObject("returnOrderItemList",returnOrderItemList);
        mv.addObject("preBackMoney",preBackMoney);
        mv.addObject("fee",fee);
        mv.addObject("dis",dis);
        return mv;
    }



    @RequestMapping(value = "initReturnOrder",method = RequestMethod.GET)
    public String initReturnOrder(Long orderId){

        //从购物获取商品列表
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);

        OrderItemQuery query = new OrderItemQuery();
        query.setOrderId(orderId);
        List<OrderItemDomain> orderItemList  = orderItemService.getList(query);
        List<OrderItemDomain> cartList = new ArrayList<OrderItemDomain>();
        for(OrderItemDomain line:orderItemList){
            if(!(line.getStatus()==1 && line.getReturnNum()>=line.getNum())){
                cartList.add(line);
            }
            GoodsQuery goodsQuery = new GoodsQuery();
            goodsQuery.setCode(line.getGoodsCode());
            line.setGoodsDomain(goodsService.getFirst(goodsQuery));
            line.setSizeDomain(prototypeSpecificationOptionService.get(JSONObject.fromObject(line.getSkuSpecifications()).getLong("size")));
        }
        if(cartList.size()==0){
            return "redirect:order/list";
        }
        orderService.withGoodItme(cartList);
        OrderDomain orderDomain = orderService.get(orderId);
        orderDomain.setOrderItemDomainList(cartList);
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
        /*mv.addObject(CART_LIST,cartList);
        mv.addObject(ORDER,orderDomain);*/
        session.setAttribute(CART_LIST,cartList);
        session.setAttribute(ORDER,orderDomain);
        return mv;
    }

    @RequestMapping(value = "returnOrderConsigneeInfo" ,method = RequestMethod.GET)
    public ModelAndView returnOrderConsigneeInfo(String page){
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        //判断是否选择有退货商品
        List<OrderItemDomain> list = (List<OrderItemDomain>) session.getAttribute(CART_LIST);
        if(!(list!=null && list.size()>0)){
            return new ModelAndView("redirect:returnOrderInfo");
        }
        Double preBackMoney = 0D;
        for(OrderItemDomain line:list){
            preBackMoney += line.getGoodsPrice()*line.getNum();
        }
        ModelAndView mv = new ModelAndView("user/returnOrder/returnOrderConsigneeInfo");
        mv.addObject("preBackMoney",preBackMoney);
        session.setAttribute("referrerPage",page);
        return mv;
    }


    /**
     * 查询退货地址
     *
     * @return
     */
    @RequestMapping(value = "chooseReturnWay" ,method = RequestMethod.GET)
    public ModelAndView chooseReturnWay(@ModelAttribute ReturnInfoForm returnInfoForm){
        ModelAndView mv = new ModelAndView("user/returnOrder/chooseReturnWay");
        return mv;
    }
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
     * 初始化自提门店
     * @return
     */
    @RequestMapping(value = "listStoreCountry",method = RequestMethod.GET)
    public  ModelAndView listStoreCountry(){
        ModelAndView modelAndView= new ModelAndView("user/returnOrder/listStore");
        List<StoreCountryDomain> storeCountryList  = storeCountryService.getList(new StoreCountryQuery());
        modelAndView.addObject("storeCountryList",storeCountryList);
        return modelAndView;
    }

    /**
     * 选择退货商品和退货理由
     *
     * @return
     */

    @RequestMapping(value = "chooseGoodsAndReason" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult chooseGoods(@ModelAttribute ReturnInfoForm returnInfoForm){
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        HashMap jsonMap = new HashMap();
        List<ReturnReasonModel> reasonModels = returnInfoForm.getReturnList();
        if(reasonModels==null||(reasonModels!=null&&reasonModels.size()<1)){
            return errorResult("没有选择退货商品");
        }
        List<OrderItemDomain> list = (List<OrderItemDomain>)session.getAttribute(CART_LIST);
        List<OrderItemDomain> newList = new ArrayList<OrderItemDomain>();

             for(int i=0;i<list.size();i++){
                 for(ReturnReasonModel line:reasonModels){
                     if(!line.isChooseReason()){
                         return errorResult("退货理由必选");
                     }
                     System.out.println("line:"+JsonUtils.toJSONString(line));
                     if(list.get(i).getId().equals(line.getOrderItemId())){
                         newList.add(list.get(i));
                         jsonMap.put(line.getOrderItemId()+"",line.allReason());
                     }
                 }
             }
        if(!(newList!=null && newList.size()>0)){
            return errorResult("没有选择退货商品");
        }
        session.setAttribute(CART_LIST,newList);
        session.setAttribute("returnJsonReason",jsonMap);
        return successResult("选择成功");
    }

    @RequestMapping(value = "fillReturnAddress" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult fillReturnAddress(String address,String name){
        if(StringUtils.isBlank(address)){
            return errorResult("地址必填");
        }
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        ReturnRequestDomain returnRequest = (ReturnRequestDomain)session.getAttribute(RETURN_ORDER);
        session.setAttribute("returnAddress",address);
        session.setAttribute("shipName",name);
        session.setAttribute(BACK_WAY,1);
        returnRequest.setShipAddress(address);
        returnRequest.setShipName(name);
        returnRequest.setReturnShippingMethod(1);
        return successResult("操作成功");
    }

    @RequestMapping(value = "sureReturnWay" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult sureReturnWay(Integer backWay,Long addressId){
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        ReturnRequestDomain returnRequest = (ReturnRequestDomain)session.getAttribute(RETURN_ORDER);
        CustomerAddressDomain customerAddress = customerAddressService.get(addressId);
        returnRequest.setReturnShippingMethod(backWay);
        /*if(backWay==1){
            if(customerAddress==null){
                return errorResult("选择失败，无此地址");
            }
            returnRequest.setShipAddress(customerAddress.getAddress());
            returnRequest.setReturnShopId(null);
            returnRequest.setCustomerAddressDomain(customerAddress);
            returnRequest.setStoreDomain(null);
        }else*/ if(backWay==2){
            StoreDomain storeDomain = storeService.get(addressId);
            if(storeDomain==null){
                return errorResult("选择失败，无此门店");
            }
            session.setAttribute("returnAddress",null);
            returnRequest.setStoreDomain(storeDomain);
            returnRequest.setReturnShopId(addressId);
            returnRequest.setShipName(null);
            returnRequest.setShipAddress(null);
            returnRequest.setCustomerAddressDomain(null);
        }
        session.setAttribute(BACK_WAY,backWay);
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
        //查询订单状态是否付款是否取消
        Integer status =  orderDomain.getStatus();
        if(status==1||status==-1){
            return errorResult("该订单不能退货");
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
            returnRequestItemDomain.setGoodsName(line.getGoodsName());
            returnRequestItemDomain.setGoodsCode(line.getGoodsCode());
            returnRequestItemDomain.setGoodsPrice(line.getGoodsPrice());
            returnRequestItemDomain.setSkuSpecifications(line.getSkuSpecifications());
            returnRequestItemDomain.setSkuId(line.getSkuId());
            returnRequestItemDomain.setItemId(line.getItemId());
            returnRequestItemService.create(returnRequestItemDomain);
            //修改订单商品状态和退货数量
            OrderItemDomain orderItemDomain = orderItemService.get(Long.parseLong(returnRequestItemDomain.getOrderItemId()));
            orderItemDomain.setStatus(1);
            orderItemDomain.setReturnNum(returnRequestItemDomain.getNum());
            orderItemService.update(orderItemDomain);
        }

        //清空session
        session.setAttribute(ORDER,null);
        session.setAttribute(RETURN_ORDER,null);
        session.setAttribute(CART_LIST,null);
        session.setAttribute(BACK_WAY, null);
        session.setAttribute("returnJsonReason", null);
        session.setAttribute("shipName",null);
        session.setAttribute("returnAddress",null);

        //发送短信
        smsService.sendToSms(customerDomain.getPhone(), MessageTypeEnum.RETURN_REQUEST.getValue());
        //发送邮件  TODO: 2017/6/15

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




    @RequestMapping(value = "returnReasonIntroduce" ,method = RequestMethod.GET)
    public ModelAndView returnReasonIntroduce(){
        ModelAndView mv = new ModelAndView("user/returnOrder/returnReasonIntroduce");
        return mv;
    }

    @RequestMapping(value = "toCreateAddress" ,method = RequestMethod.GET)
    public ModelAndView toCreateAddress(){
        ModelAndView mv = new ModelAndView("user/returnOrder/createAddress");
        return mv;
    }


}
