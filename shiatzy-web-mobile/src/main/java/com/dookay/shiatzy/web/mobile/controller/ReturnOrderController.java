package com.dookay.shiatzy.web.mobile.controller;

import com.alibaba.fastjson.JSON;
import com.dookay.coral.adapter.sendmsg.sendmail.SimpleAliDMSendMail;
import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.web.BaseController;
import com.dookay.coral.common.web.HttpContext;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.host.user.context.UserContext;
import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.shop.content.domain.MessageTemplateDomain;
import com.dookay.coral.shop.content.query.MessageTemplateQuery;
import com.dookay.coral.shop.content.service.IMessageTemplateService;
import com.dookay.coral.shop.customer.domain.CustomerAddressDomain;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.query.CustomerAddressQuery;
import com.dookay.coral.shop.customer.service.ICustomerAddressService;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.goods.query.GoodsQuery;
import com.dookay.coral.shop.goods.service.IGoodsItemService;
import com.dookay.coral.shop.goods.service.IGoodsService;
import com.dookay.coral.shop.goods.service.IPrototypeSpecificationOptionService;
import com.dookay.coral.shop.message.enums.MessageTypeEnum;
import com.dookay.coral.shop.message.service.ISmsService;
import com.dookay.coral.shop.message.util.EmailUtil;
import com.dookay.coral.shop.message.util.FreemarkerUtil;
import com.dookay.coral.shop.order.domain.*;
import com.dookay.coral.shop.order.query.OrderItemQuery;
import com.dookay.coral.shop.order.query.ReturnRequestItemQuery;
import com.dookay.coral.shop.order.service.*;
import com.dookay.coral.shop.shipping.domain.ShippingCountryDomain;
import com.dookay.coral.shop.shipping.service.IShippingCountryService;
import com.dookay.coral.shop.store.domain.StoreCountryDomain;
import com.dookay.coral.shop.store.domain.StoreDomain;
import com.dookay.coral.shop.store.query.StoreCountryQuery;
import com.dookay.coral.shop.store.service.IStoreCountryService;
import com.dookay.coral.shop.store.service.IStoreService;
import com.dookay.shiatzy.web.mobile.form.ReturnInfoForm;
import com.dookay.shiatzy.web.mobile.model.ReturnReasonModel;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by admin on 2017/4/25.
 */

@Controller
@RequestMapping("u/returnOrder/")
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
    @Autowired
    private IShippingCountryService shippingCountryService;

    @Autowired
    private SimpleAliDMSendMail simpleAliDMSendMail;
    @Autowired
    private IMessageTemplateService messageTemplateService;
    @Autowired
    private IGoodsItemService goodsItemService;

    private static String CART_LIST = "cartList";
    private static String RETURN_ORDER = "return_order";
    private static String ORDER = "order";
    private static String BACK_WAY = "backWay";
    private static Double BACK_FEE = 0D;

    /**
     * 退货详情页
     * @param orderId
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
        returnRequestDomain.setOrderDomain(orderDomain);
        Double fee = orderDomain.getShipFee();
        Double dis = orderDomain.getCouponDiscount();
        Double memDis = orderDomain.getMemberDiscount();
        dis = dis==null?0D:dis;
        dis = memDis==null?dis:dis+memDis;

        HashMap<String, String> newReturnReasonMap =  new HashMap<>();

        for(ReturnRequestItemDomain line:returnOrderItemList){

            Double goodsPrice = line.getGoodsDisPrice()!=null?line.getGoodsDisPrice():line.getGoodsPrice();
            preBackMoney += goodsPrice*line.getNum();
            GoodsQuery goodsQuery = new GoodsQuery();
            goodsQuery.setCode(line.getGoodsCode());
            line.setGoodsDomain(goodsService.getFirst(goodsQuery));
            line.setSizeDomain(prototypeSpecificationOptionService.get(Long.parseLong(""+ JSONObject.fromObject(line.getSkuSpecifications()).get("size"))));
            newReturnReasonMap.put(line.getId().toString(),getReasonList(line.getReturnReason()));
        }
        preBackMoney = preBackMoney-dis;
        returnRequestItemService.withGoodsItem(returnOrderItemList);


        ModelAndView mv = new ModelAndView("user/returnOrder/details");
        mv.addObject("returnRequestDomain",returnRequestDomain);
        mv.addObject("returnOrderItemList",returnOrderItemList);
        mv.addObject("preBackMoney",preBackMoney);
        mv.addObject("returnReasonMap",newReturnReasonMap);
        mv.addObject("fee",fee);
        mv.addObject("dis",dis);
        return mv;
    }

    /**
     * 申请退货
     * @param orderId
     * @return
     */
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
            return "redirect:u/order/list";
        }
        orderService.withGoodItme(cartList);
        OrderDomain orderDomain = orderService.get(orderId);
        orderDomain.setOrderItemDomainList(cartList);

        HttpServletRequest request = HttpContext.current().getRequest();
        Long countryId = orderDomain.getShippingCountryId();
        ShippingCountryDomain shippingCountryDomain = null;
        String currentCode = "CNY";
        Double fee = 0D;
        if(countryId!=null){
            shippingCountryDomain = shippingCountryService.get(countryId);
            Double rate = shippingCountryDomain.getRate();
            //根据国家选择结算币种
            int currentCodeType = shippingCountryDomain.getRateType();
            if(currentCodeType==1){
                currentCode = "USD";
                fee = 350D/rate;
            }else if(currentCodeType==2){
                currentCode = "EUR";
                fee = 350D/rate;
            }else {
                fee = 25D/rate;
            }
        }
        //创建订单对象
        ReturnRequestDomain returnRequestDomain = new ReturnRequestDomain();
        returnRequestDomain.setCustomerId(customerDomain.getId());
        returnRequestDomain.setCreateTime(new Date());
        returnRequestDomain.setOrderId(orderDomain.getId());
        returnRequestDomain.setOrderNo(orderDomain.getOrderNo());
        returnRequestDomain.setShipName(customerDomain.getFirstName()+customerDomain.getLastName());
        returnRequestDomain.setOrderTime(orderDomain.getOrderTime());
        returnRequestDomain.setShipFee(new BigDecimal(fee).setScale(0,BigDecimal.ROUND_HALF_DOWN).doubleValue());
        BACK_FEE = returnRequestDomain.getShipFee();
        returnRequestDomain.setCurrentCode(currentCode);
        returnRequestDomain.setStatus(1);
        //保存订单对象到session

        HttpSession session = request.getSession();
        session.setAttribute(CART_LIST,cartList);
        session.setAttribute(ORDER,orderDomain);
        session.setAttribute(RETURN_ORDER,returnRequestDomain);
        //跳转到填写信息页面
        return "redirect:returnOrderInfo";
    }

    /**
     * 选择退货商品和理由页面
     * @return
     */
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
        session.setAttribute(CART_LIST,cartList);
        session.setAttribute(ORDER,orderDomain);
        return mv;
    }

    /**
     * 选择退货商品和退货理由
     * @return
     */
    @RequestMapping(value = "chooseGoodsAndReason" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult chooseGoods(@ModelAttribute ReturnInfoForm returnInfoForm){
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        HashMap jsonMap = new HashMap();
        List<ReturnReasonModel> reasonModels = returnInfoForm.getReturnList();
        if(reasonModels == null || reasonModels.stream().filter(ReturnReasonModel::itemSelected).count()==0){
            return errorResult("没有选择退货商品");
        }
        List<OrderItemDomain> list = (List<OrderItemDomain>)session.getAttribute(CART_LIST);
        List<OrderItemDomain> newList = new ArrayList<OrderItemDomain>();

        for(int i=0;i<list.size();i++){
            for(ReturnReasonModel line:reasonModels){
                if(line.itemSelected() && !line.isChooseReason()){
                    return errorResult("退货理由必选");
                }
                System.out.println("line:"+JsonUtils.toJSONString(line));
                if(list.get(i).getId().equals(line.getOrderItemId())){
                    newList.add(list.get(i));
                    jsonMap.put(line.getOrderItemId()+"",line.allReason());
                }
            }
        }

        session.setAttribute(CART_LIST,newList);
        session.setAttribute("returnJsonReason",jsonMap);
        return successResult("选择成功");
    }

    /**
     * 确认退货页面
     * @param page
     * @return
     */
    @RequestMapping(value = "returnOrderConsigneeInfo" ,method = RequestMethod.GET)
    public ModelAndView returnOrderConsigneeInfo(String page){
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        ReturnRequestDomain returnRequestDomain = (ReturnRequestDomain)session.getAttribute(RETURN_ORDER);
        if(returnRequestDomain==null){
            throw new ServiceException("退货单过期");
        }
        //判断是否选择有退货商品
        List<OrderItemDomain> list = (List<OrderItemDomain>) session.getAttribute(CART_LIST);
        if(!(list!=null && list.size()>0)){
            return new ModelAndView("redirect:returnOrderInfo");
        }
        HashMap<String, String> returnReasonMap =  (HashMap) session.getAttribute("returnJsonReason");
        HashMap<String, String> newReturnReasonMap =  new HashMap<>();
        for (String key : returnReasonMap.keySet()) {
            String  value = returnReasonMap.get(key);
            newReturnReasonMap.put(key,getReasonList(value));
        }

        //创建退货项目列表

        Double preBackMoney = 0D;
        for(OrderItemDomain line:list){
            Double goodsPrice = line.getGoodsDisPrice()!=null?line.getGoodsDisPrice():line.getGoodsPrice();
            preBackMoney += goodsPrice*line.getNum();
        }
        ModelAndView mv = new ModelAndView("user/returnOrder/returnOrderConsigneeInfo");
        OrderDomain orderDomain = orderService.get(returnRequestDomain.getOrderId());
        Double dis = orderDomain.getCouponDiscount();
        Double memDis = orderDomain.getMemberDiscount();
        dis = dis==null?0D:dis;
        dis = memDis==null?dis:dis+memDis;

        mv.addObject("preBackMoney",preBackMoney-returnRequestDomain.getShipFee()-dis);
        mv.addObject("returnReasonMap",newReturnReasonMap);
        session.setAttribute("referrerPage",page);
        return mv;
    }

    private String getReasonList(String reasonJson){
        System.out.print(reasonJson);
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(reasonJson);
        List<String> reasonList = new ArrayList<>();
        if(jsonObject.containsKey("service")){
            com.alibaba.fastjson.JSONObject jsonObject1 = jsonObject.getJSONObject("service");
            if(jsonObject1.containsKey("reason1")){
                reasonList.add(jsonObject1.getString("reason1"));
            }
            if(jsonObject1.containsKey("reason2")){
                reasonList.add(jsonObject1.getString("reason2"));
            }
            if(jsonObject1.containsKey("reason3")){
                reasonList.add(jsonObject1.getString("reason3"));
            }
            if(jsonObject1.containsKey("reason4")){
                reasonList.add(jsonObject1.getString("reason4"));
            }
        }
        if(jsonObject.containsKey("quality")){
            com.alibaba.fastjson.JSONObject jsonObject1 = jsonObject.getJSONObject("quality");
            if(jsonObject1.containsKey("reason1")){
                reasonList.add(jsonObject1.getString("reason1"));
            }
            if(jsonObject1.containsKey("reason2")){
                reasonList.add(jsonObject1.getString("reason2"));
            }
            if(jsonObject1.containsKey("reason3")){
                reasonList.add(jsonObject1.getString("reason3"));
            }
            if(jsonObject1.containsKey("reason4")){
                reasonList.add(jsonObject1.getString("reason4"));
            }
        }
        if(jsonObject.containsKey("size")){
            com.alibaba.fastjson.JSONObject jsonObject1 = jsonObject.getJSONObject("size");
            if(jsonObject1.containsKey("reason1")){
                reasonList.add(jsonObject1.getString("reason1"));
            }
            if(jsonObject1.containsKey("reason2")){
                reasonList.add(jsonObject1.getString("reason2"));
            }
            if(jsonObject1.containsKey("reason3")){
                reasonList.add(jsonObject1.getString("reason3"));
            }
            if(jsonObject1.containsKey("reason4")){
                reasonList.add(jsonObject1.getString("reason4"));
            }
        }
        if(jsonObject.containsKey("other")){
            com.alibaba.fastjson.JSONObject jsonObject1 = jsonObject.getJSONObject("other");
            if(jsonObject1.containsKey("reason1")){
                reasonList.add(jsonObject1.getString("reason1"));
            }
            if(jsonObject1.containsKey("reason2")){
                reasonList.add(jsonObject1.getString("reason2"));
            }
            if(jsonObject1.containsKey("reason3")){
                reasonList.add(jsonObject1.getString("reason3"));
            }
            if(jsonObject1.containsKey("reason4")){
                reasonList.add(jsonObject1.getString("reason4"));
            }
        }

        return StringUtils.join(reasonList,",");
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
        returnRequest.setShipFee(BACK_FEE);
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
            returnRequest.setShipFee(0D);
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
        Double dis = orderDomain.getCouponDiscount();
        Double memDis = orderDomain.getMemberDiscount();
        dis = dis==null?0D:dis;
        dis = memDis==null?dis:dis+memDis;
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
        List<ReturnRequestItemDomain> requestList = new ArrayList<>();
        Double totalAmt = 0D;
        for(OrderItemDomain line:orderItemDomains){
            Double price = line.getGoodsDisPrice()!=null?line.getGoodsDisPrice():line.getGoodsPrice();
            totalAmt = price*line.getNum();
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
            returnRequestItemDomain.setGoodsDisPrice(line.getGoodsDisPrice());
            returnRequestItemDomain.setSkuSpecifications(line.getSkuSpecifications());
            returnRequestItemDomain.setSkuId(line.getSkuId());
            returnRequestItemDomain.setItemId(line.getItemId());
            returnRequestItemDomain.setSizeDomain(prototypeSpecificationOptionService.get(JSONObject.fromObject(line.getSkuSpecifications()).getLong("size")));
            returnRequestItemService.create(returnRequestItemDomain);
            requestList.add(returnRequestItemDomain);
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

        Boolean isEN = orderDomain.getShippingCountryId()!=1?true:false;
        Double totalFee=totalAmt-returnRequestDomain.getShipFee()-dis;
        //发送短信
        if(StringUtils.isNotBlank(customerDomain.getPhone())){
        smsService.sendToSms(isEN,customerDomain.getPhone(), MessageTypeEnum.RETURN_REQUEST.getValue());
        }
        //发送邮件
        //1.查询发送内容
        EmailUtil.applyReturn(isEN,customerDomain.getEmail(),returnRequestDomain,requestList,totalFee);

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
