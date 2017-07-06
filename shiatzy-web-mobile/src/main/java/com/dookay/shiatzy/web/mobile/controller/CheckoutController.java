package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.adapter.sendmsg.sendmail.SimpleAliDMSendMail;
import com.dookay.coral.common.enums.ValidEnum;
import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.utils.BeanValidators;
import com.dookay.coral.common.utils.RandomUtils;
import com.dookay.coral.common.web.BaseController;
import com.dookay.coral.common.web.CookieUtil;
import com.dookay.coral.common.web.HttpContext;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.common.web.validate.FieldMatch;
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
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.domain.GoodsItemDomain;
import com.dookay.coral.shop.goods.domain.PrototypeSpecificationOptionDomain;
import com.dookay.coral.shop.goods.domain.SkuDomain;
import com.dookay.coral.shop.goods.query.PrototypeSpecificationOptionQuery;
import com.dookay.coral.shop.goods.query.SkuQuery;
import com.dookay.coral.shop.goods.service.IGoodsItemService;
import com.dookay.coral.shop.goods.service.IGoodsService;
import com.dookay.coral.shop.goods.service.IPrototypeSpecificationOptionService;
import com.dookay.coral.shop.goods.service.ISkuService;
import com.dookay.coral.shop.message.enums.MessageTypeEnum;
import com.dookay.coral.shop.message.service.IEmailService;
import com.dookay.coral.shop.message.service.ISmsService;
import com.dookay.coral.shop.order.domain.OrderDomain;
import com.dookay.coral.shop.order.domain.OrderItemDomain;
import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;
import com.dookay.coral.shop.order.enums.OrderStatusEnum;
import com.dookay.coral.shop.order.enums.ShippingMethodEnum;
import com.dookay.coral.shop.order.enums.ShoppingCartTypeEnum;
import com.dookay.coral.shop.order.service.IOrderItemService;
import com.dookay.coral.shop.order.service.IOrderService;
import com.dookay.coral.shop.order.service.IShoppingCartService;
import com.dookay.coral.shop.promotion.domain.CouponDomain;
import com.dookay.coral.shop.promotion.service.ICouponService;
import com.dookay.coral.shop.shipping.domain.ShippingCountryDomain;
import com.dookay.coral.shop.shipping.query.ShippingCountryQuery;
import com.dookay.coral.shop.shipping.service.IShippingCountryService;
import com.dookay.coral.shop.store.domain.StoreCityDomain;
import com.dookay.coral.shop.store.domain.StoreCountryDomain;
import com.dookay.coral.shop.store.domain.StoreDomain;
import com.dookay.coral.shop.store.query.StoreCityQuery;
import com.dookay.coral.shop.store.query.StoreCountryQuery;
import com.dookay.coral.shop.store.query.StoreQuery;
import com.dookay.coral.shop.store.service.IStoreCityService;
import com.dookay.coral.shop.store.service.IStoreCountryService;
import com.dookay.coral.shop.store.service.IStoreService;
import com.dookay.coral.shop.temp.domain.TempMemberDomain;
import com.dookay.coral.shop.temp.domain.TempStockDomain;
import com.dookay.coral.shop.temp.query.TempMemberQuery;
import com.dookay.coral.shop.temp.query.TempStockQuery;
import com.dookay.coral.shop.temp.service.ITempMemberService;
import com.dookay.coral.shop.temp.service.ITempStockService;
import com.dookay.shiatzy.web.mobile.model.AddressModel;
import com.dookay.shiatzy.web.mobile.util.FreemarkerUtil;
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
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/2
 */
@RequestMapping("checkout/")
@Controller
public class CheckoutController  extends BaseController{

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IShoppingCartService shoppingCartService;

    @Autowired
    private ICustomerAddressService customerAddressService;

    @Autowired
    private ICouponService couponService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderItemService orderItemService;

    @Autowired
    private ISkuService skuService;

    @Autowired
    private IStoreService storeService;

    @Autowired
    private IStoreCountryService storeCountryService;
    @Autowired
    private IStoreCityService storeCityService;
    @Autowired
    private IShippingCountryService shippingCountryService;

    @Autowired
    private IGoodsItemService goodsItemService;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IPrototypeSpecificationOptionService prototypeSpecificationOptionService;
    @Autowired
    private ISmsService smsService;
    @Autowired
    private ITempStockService tempStockService;
    @Autowired
    private IEmailService emailService;
    @Autowired
    private IMessageTemplateService messageTemplateService;
    @Autowired
    private SimpleAliDMSendMail simpleAliDMSendMail;
    @Autowired
    private ITempMemberService tempMemberService;


    private static String CART_LIST = "cartList";
    private static String ORDER = "order";
    private final static String SHIPPING_COUNTRY_ID="shippingCountryId";
    /**
     * 从购物车初始化订单
     * @return
     */
    @RequestMapping(value = "initOrder",method = RequestMethod.GET)
    public String initOrder(){
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        //创建订单对象
        OrderDomain order = new OrderDomain();
        String countryId = CookieUtil.getCookieValueByKey(request,"shippingCountry");
        ShippingCountryDomain shippingCountryDomain = null;
        String currentCode = "CNY";
        Double fee = 0D;
        if(StringUtils.isNotBlank(countryId)){
            shippingCountryDomain = shippingCountryService.get(Long.parseLong(countryId));
            Double rate = shippingCountryDomain.getRate()!=null?shippingCountryDomain.getRate():1D;
            //根据国家选择结算币种
            int currentCodeType = shippingCountryDomain.getRateType();
            if(currentCodeType==1){
                currentCode = "USD";
            }else if(currentCodeType==2){
                currentCode = "EUR";
            }
            fee = shippingCountryDomain.getShippingCost()/rate;
        }else {
            countryId = "1";
        }
        order.setShipFee(new BigDecimal(fee).setScale(0,BigDecimal.ROUND_HALF_DOWN).doubleValue());
        //根据国家获取值
        order.setShippingCountryId(Long.parseLong(countryId));
        order.setCurrentCode(currentCode);
       /* order.setOrderNo(RandomUtils.buildNo());
        order.setCustomerId(customerDomain.getId());
        order.setStatus(OrderStatusEnum.UNPAID.getValue());
        List<OrderItemDomain> orderItemDomainList = new ArrayList<>();
        for (ShoppingCartItemDomain shoppingCartItemDomain :cartList){
            OrderItemDomain orderItemDomain = new OrderItemDomain();
            orderItemDomain.setGoodsCode(shoppingCartItemDomain.getGoodsCode());
            orderItemDomain.setGoodsName(shoppingCartItemDomain.getGoodsName());
            orderItemDomain.setGoodsPrice(shoppingCartItemDomain.getGoodsPrice());
            orderItemDomain.setNum(shoppingCartItemDomain.getNum());
            orderItemDomain.setSkuId(shoppingCartItemDomain.getSkuId());
            orderItemDomain.setSkuSpecifications(shoppingCartItemDomain.getSkuSpecifications());
            orderItemDomainList.add(orderItemDomain);
        }
        order.setOrderItemDomainList(orderItemDomainList);*/

        //保存订单对象到session

        session.setAttribute(ORDER,order);
        //跳转到结算页面
        return "redirect:orderInfo";
    }


    @RequestMapping(value = "getStockBySizeAndColor",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getStockBySizeAndColor(String goodsNo,Long colorId,Long sizeId){
        return successResult("查询成功",tempStockService.getStockBySizeAndColor(goodsNo,colorId,sizeId));
    }


    /**
     * 结算页面
     * @return
     */
    @RequestMapping(value = "orderInfo",method = RequestMethod.GET)
    public ModelAndView orderInfo(){
        UserContext userContext = UserContext.current();

        Long accountId = userContext.getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        //获取订单session
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        OrderDomain orderDomain = (OrderDomain)session.getAttribute(ORDER);
        if(orderDomain==null){
            return new ModelAndView("redirect:/home/index");
        }
        //购物车
        List<ShoppingCartItemDomain> cartList = shoppingCartService.listShoppingCartItemByCustomerId(customerDomain.getId(), ShoppingCartTypeEnum.SHOPPING_CART.getValue());
        shoppingCartService.withGoodsItem(cartList);
        shoppingCartService.withSizeDomain(cartList);
        if(cartList==null || cartList.size()==0){
            return new ModelAndView("redirect:/home/index");
        }

        int count = 0;

        for(ShoppingCartItemDomain line :cartList){
            //准备商品数据
            GoodsItemDomain goodsItemDomain =  goodsItemService.get(line.getItemId());
            Long goodsId = goodsItemDomain.getGoodsId();
            goodsItemService.withColor(goodsItemDomain);
            GoodsDomain goodsDomain = goodsService.get(goodsId);//得到商品
            goodsService.withGoodsItemList(goodsDomain);
            //尺寸
            List<Long> sizeIds =JsonUtils.toLongArray(goodsDomain.getSizeIds());
            PrototypeSpecificationOptionQuery prototypeSpecificationOptionQuery = new PrototypeSpecificationOptionQuery();
            prototypeSpecificationOptionQuery.setIds(sizeIds);
            List<PrototypeSpecificationOptionDomain> sizeList = prototypeSpecificationOptionService.getList(prototypeSpecificationOptionQuery);
            System.out.println("sizeIds:"+sizeIds);
            for(Long id:sizeIds){
                goodsService.withGoodsItemListAndQuantity(goodsDomain,id);
            }
            line.setSizeDomins(sizeList);
            line.setGoodsDomain(goodsDomain);
            Double rate = shippingCountryService.get(orderDomain.getShippingCountryId()).getRate();
            line.setGoodsPrice(new BigDecimal(line.getGoodsPrice()/rate).setScale(0,BigDecimal.ROUND_HALF_DOWN).doubleValue());
            Double disPrice = line.getGoodsDisPrice();
            if(disPrice!=null){
                line.setGoodsDisPrice(new BigDecimal(disPrice/rate).setScale(0,BigDecimal.ROUND_HALF_DOWN).doubleValue());
            }
            System.out.println("goodsDomain:"+goodsDomain+"\n sizeList:"+sizeList);
        }
        //商品金额
        calcOrderTotal(orderDomain, cartList);
        ModelAndView mv=  new ModelAndView("checkout/orderInfo");
        //mv.addObject(CART_LIST,cartList);
        //mv.addObject(ORDER,orderDomain);
        session.setAttribute(CART_LIST,cartList);
        session.setAttribute(ORDER,orderDomain);
        return mv;
    }

    @RequestMapping(value = "updateGoodsInCheck",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateGoodsInCheck(Long goodsItemId,Long sizeId,Long cartId,Integer num){
        System.out.println("oldCartGood:"+cartId);
        if(shoppingCartService.get(cartId)!=null){
            shoppingCartService.removeFromCart(cartId);
        }else{
            return errorResult("参数出错");
        }
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
         /*获取SKU*/
        Long itemId = goodsItemId;
        System.out.println("sizeId:"+sizeId+"\nitemId:"+itemId);

        SkuQuery skuQuery = new SkuQuery();
        skuQuery.setItemId(itemId);
        skuQuery.setIsValid(ValidEnum.YES.getValue());
        System.out.println("skuQuery"+JsonUtils.toJSONString(skuQuery));
        List<SkuDomain> skuDomainList = skuService.getList(skuQuery);
        System.out.println("skuDomainList"+JsonUtils.toJSONString(skuDomainList));
        SkuDomain skuDomain =  skuDomainList.stream().filter(x-> JSONObject.fromObject(x.getSpecifications()).getLong("size")==sizeId).findFirst().orElse(null);
        if(skuDomain == null)
        {
            throw new ServiceException("参数错误");
        }
        skuDomain.setItemId(itemId);
        shoppingCartService.addToCart(customerDomain, skuDomain,ShoppingCartTypeEnum.SHOPPING_CART.getValue(),num);
        return successResult("修改成功");
    }


    @RequestMapping(value = "confirm",method = RequestMethod.GET)
    public ModelAndView confirm(String page){
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        //获取订单session
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        OrderDomain orderDomain = (OrderDomain)session.getAttribute(ORDER);
        if(orderDomain==null){
            return new ModelAndView("redirect:/home/index");
        }

        //处理未支付返回的情形
        if(Boolean.TRUE==orderDomain.getSubmitted()){
            return new ModelAndView("redirect:/payment/payfailed?orderId="+orderDomain.getOrderNo());
        }
        //商品金额

        //购物车
        List<ShoppingCartItemDomain> cartList = (List<ShoppingCartItemDomain>)session.getAttribute(CART_LIST);
        calcOrderTotal(orderDomain, cartList);
        if(cartList==null || cartList.size()==0){
            return new ModelAndView("redirect:/home/index");
        }
        ModelAndView mv=  new ModelAndView("checkout/confirm");
        //mv.addObject(CART_LIST,cartList);
        //mv.addObject(ORDER,orderDomain);
        orderDomain.setSubmitted(false);//未提交
        session.setAttribute(CART_LIST,cartList);
        session.setAttribute(ORDER,orderDomain);
        session.setAttribute("referrerPage",page);
        return mv;
    }

    /**
     * 提交订单
     * @return
     */
    @RequestMapping(value = "submitOrder", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult submitOrder() throws MessagingException {
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        //从session中获取订单对象,对象至少包含商品列表、优惠券
        HttpServletRequest request = HttpContext.current().getRequest();
        String payWay = request.getParameter("payWay");
        Boolean isCOD = StringUtils.isNotBlank(payWay)&&"COD".equals(payWay)?true:false;
        HttpSession session = request.getSession();
        OrderDomain order = (OrderDomain)session.getAttribute(ORDER);
        if(order== null){
            return errorResult("订单已经失效");
        }

        //购物车
        List<ShoppingCartItemDomain> cartList = (List<ShoppingCartItemDomain>)session.getAttribute(CART_LIST);
        if(cartList== null || cartList.size() ==0){
            return errorResult("订单已经失效");
        }
        shoppingCartService.withGoodsItem(cartList);
        //持久化订单，验证优惠券码是否可用，商品库存是否足够
        Long couponId  = order.getCouponId();
        if(couponId!=null ){
            CouponDomain couponDomain = couponService.get(couponId);
            couponService.checkCoupon(couponDomain.getCode());
        }
        if(isCOD){
            order.setPaymentMethod(4);
            //优惠券减少
            if(couponId!=null ){
                orderService.subCouponNum(order);
            }
            //库存减少
            orderService.updateSkuStock(order);
        }
        List<Long> itemIds = new ArrayList<Long>();
        List<OrderItemDomain> orderItemDomainList = new ArrayList<OrderItemDomain>();
        //创建订单
        //商品金额
        calcOrderTotal(order, cartList);
        order.setOrderNo(RandomUtils.buildNo());
        order.setCustomerId(customerDomain.getId());
        order.setStatus(OrderStatusEnum.UNPAID.getValue());
        order.setOrderTime(new Date());
        CustomerAddressDomain customerAddressDomain = customerAddressService.get(order.getShipAddressId());
        if(order.getPaymentMethod()==3&&order.getShippingMethod()==1){//ipaylinks 必须要邮编
            order.setShipPostalCode(customerAddressDomain.getPostalCode());//邮编
        }else{
            order.setShipPostalCode("100000");//默认邮编
        }
        orderService.create(order);
        //创建明细
        for(int j = 0;j<cartList.size();j++){
            ShoppingCartItemDomain items = cartList.get(j);
            OrderItemDomain orderItemDomain = new OrderItemDomain();
            orderItemDomain.setOrderId(order.getId());
            SkuDomain skuDomain = skuService.get(items.getSkuId());
            if (skuDomain.getQuantity()<=0){
                itemIds.add(skuDomain.getGoodsId());
                continue;
            }
            orderItemDomain.setSkuId(items.getSkuId());
            orderItemDomain.setNum(items.getNum());
            orderItemDomain.setItemId(items.getItemId());
            String en_US = (String)session.getAttribute("language");
            orderItemDomain.setGoodsName("en_US".equals(en_US)?items.getGoodsEnName():items.getGoodsName());
            orderItemDomain.setGoodsCode(items.getGoodsCode());
            orderItemDomain.setGoodsPrice(items.getGoodsPrice());
            orderItemDomain.setGoodsDisPrice(items.getGoodsDisPrice());
            orderItemDomain.setSkuSpecifications(items.getSkuSpecifications());
            orderItemDomain.setSizeDomain(prototypeSpecificationOptionService.get(JSONObject.fromObject(items.getSkuSpecifications()).getLong("size")));
            orderItemDomain.setStatus(0);
            orderItemDomain.setReturnNum(0);
            System.out.println("order:"+ JsonUtils.toJSONString(order));
            orderItemService.create(orderItemDomain);
            orderItemDomainList.add(orderItemDomain);
        }

        order.setSubmitted(true);
        //清除session
        session.setAttribute(ORDER,order);
        session.setAttribute(CART_LIST,null);
        //清除购物车
        for(int i=0 ;i<cartList.size();i++){
            shoppingCartService.removeFromCart(cartList.get(i).getId());
        }


        /*//发送短信通知
        smsService.sendToSms(order.getShipPhone(), MessageTypeEnum.CREATE_ORDER.getValue());
        //发送邮件通知
        MessageTemplateQuery query = new MessageTemplateQuery();
        query.setType(1);
        query.setCode(MessageTypeEnum.CREATE_ORDER.getValue());
        query.setIsValid(1);
        MessageTemplateDomain messageTemplate = messageTemplateService.getFirst(query);
        //emailService.sendSingleEmail(customerDomain.getEmail(),messageTemplate.getTitle(),messageTemplate.getContent());
        //生成模版
        Map<String,Object> freeMap = new HashMap<>();
        freeMap.put("picUrl",FreemarkerUtil.getLogoUrl("static/images/logoSC.png"));
        freeMap.put("title",messageTemplate.getTitle());
        freeMap.put("name",customerDomain.getEmail());
        freeMap.put("status",OrderStatusEnum.UNPAID.getValue());
        freeMap.put("content",messageTemplate.getContent());
        freeMap.put("date",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(order.getOrderTime()));
        orderService.withGoodItme(orderItemDomainList);
        freeMap.put("order",order);
        freeMap.put("orderItem",orderItemDomainList);
        String html = FreemarkerUtil.printString("orderPaid.ftl",freeMap);

        HashMap<String,String> emailMap = new HashMap<>();
        emailMap.put(simpleAliDMSendMail.SEND_EMAIL,simpleAliDMSendMail.SEND_EMAIL_SINGEL);
        emailMap.put(simpleAliDMSendMail.RECEIVE_EMAIL,customerDomain.getEmail());
        emailMap.put(simpleAliDMSendMail.TITLE,messageTemplate.getTitle());
        emailMap.put(simpleAliDMSendMail.CONTENT,html);
        simpleAliDMSendMail.sendEmail(emailMap);*/

        Long orderId = order.getId();
        if(itemIds!=null && itemIds.size()>0){
            return successResult("商品库存不足",itemIds);
        }
        return successResult("操作成功",order);
    }

    private void calcOrderTotal(OrderDomain orderDomain, List<ShoppingCartItemDomain> cartList) {
        Double rate = shippingCountryService.get(orderDomain.getShippingCountryId()).getRate();
        Double memDis = 1D;
        Double memberDiscount = 0D;
        AccountDomain accountDomain = UserContext.current().getAccountDomain();
        if(accountDomain!=null){
            CustomerDomain customerDomain = customerService.getAccount(accountDomain.getId());
            if(customerDomain!=null&&customerDomain.getIsArtClubMember()!=null&&customerDomain.getIsArtClubMember()==1){
                TempMemberQuery query = new TempMemberQuery();
                query.setMobile(customerDomain.getValidMobile());
                List<String> cardType = new ArrayList<>();
                cardType.add("CN-A");
                cardType.add("CN-B");
                cardType.add("CN-C");
                cardType.add("CN-D");
                query.setCardType(cardType);
                TempMemberDomain tempMemberDomain = tempMemberService.getFirst(query);
                memDis =tempMemberDomain!=null?tempMemberDomain.getDiscount():1D;
            }
        }
        //商品金额
        Double goodsTotal = 0D;
        for (ShoppingCartItemDomain shoppingCartItemDomain :cartList){
            Double disPrice = shoppingCartItemDomain.getGoodsDisPrice();
            Double price = disPrice==null?shoppingCartItemDomain.getGoodsPrice():disPrice;
            goodsTotal  = goodsTotal+  price* shoppingCartItemDomain.getNum();
            memberDiscount = goodsTotal*(1-memDis)/rate;
        }
        orderDomain.setMemberDiscount(memberDiscount);
        orderDomain.setGoodsTotal(goodsTotal);
        Double couponDiscount = orderDomain.getCouponDiscount()==null?0D:orderDomain.getCouponDiscount();
        //memberDiscount = orderDomain.getMemberDiscount()==null?0D:orderDomain.getMemberDiscount();
        Double shipFee = orderDomain.getShipFee()==null?0D:orderDomain.getShipFee();
        System.out.println("shipFee:"+shipFee+" couponDiscount:"+couponDiscount+" memberDiscount:"+memberDiscount+" goodsTotal:"+goodsTotal);
        Double orderTotal = goodsTotal + shipFee -couponDiscount - memberDiscount;
        BigDecimal bd = new BigDecimal(orderTotal);
        orderDomain.setOrderTotal(bd.setScale(0,BigDecimal.ROUND_HALF_DOWN).doubleValue());
    }

    /**
     * 配送地址列表页面
     * @return
     */
    @RequestMapping(value = "listShipAddress",method = RequestMethod.GET)
    public  ModelAndView listShipAddress(String way){
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        CustomerAddressQuery query = new CustomerAddressQuery();
        query.setCustomerId(customerDomain.getId());
        List addressList = customerAddressService.getList(query);
        ModelAndView mv = new ModelAndView("checkout/listShipAddress");
        mv.addObject("addressList",addressList);
        mv.addObject("way",way);
        return mv;
    }

    @RequestMapping(value = "createShipAddress",method = RequestMethod.GET)
    public  ModelAndView createShipAddress(){
        ModelAndView mv = new ModelAndView("checkout/createShipAddress");
        //查询出配送国家
        ShippingCountryQuery query = new ShippingCountryQuery();
        query.setDesc(false);
        query.setOrderBy("rank");
        List<ShippingCountryDomain> shippingCountryDomainList = shippingCountryService.getList(query);
        mv.addObject("countryList",shippingCountryDomainList);
        return mv;
    }

    @RequestMapping(value = "createShipAddress", method = RequestMethod.POST)
    @ResponseBody
    public  JsonResult createShipAddress(@ModelAttribute CustomerAddressDomain addressModel){
        System.out.print("addressModel:"+JsonUtils.toJSONString(addressModel));
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        customerAddressService.createByCustomer(customerDomain,addressModel);
        return successResult("操作成功");
    }

    @RequestMapping(value = "updateShipAddress",method = RequestMethod.GET)
    public  ModelAndView updateShipAddress(Long addressId){
        CustomerAddressDomain customerAddressDomain = customerAddressService.get(addressId);
        ModelAndView mv = new ModelAndView("checkout/updateShipAddress");
        //查询出配送国家
        ShippingCountryQuery query = new ShippingCountryQuery();
        query.setDesc(false);
        query.setOrderBy("rank");
        List<ShippingCountryDomain> shippingCountryDomainList = shippingCountryService.getList(query);
        mv.addObject("countryList",shippingCountryDomainList);
        mv.addObject("address",customerAddressDomain);
        return mv;
    }


    @RequestMapping(value = "updateShipAddress",method = RequestMethod.POST)
    @ResponseBody
    public  JsonResult updateShipAddress(@ModelAttribute CustomerAddressDomain addressModel){
        customerAddressService.update(addressModel);
        return successResult("修改成功");
    }

    @RequestMapping(value = "removeAddress",method = RequestMethod.POST)
    @ResponseBody
    public  JsonResult removeAddress(Long addressId){
        customerAddressService.delete(addressId);
        return successResult("删除成功");
    }


    @RequestMapping(value = "checkAddress",method = RequestMethod.POST)
    @ResponseBody
    public  JsonResult checkAddress(Long addressId){
        CustomerAddressDomain customerAddressDomain = customerAddressService.get(addressId);
        if(customerAddressDomain.getCountryId()==null || customerAddressDomain.getCity()==null || customerAddressDomain.getPostalCode()==null
                || customerAddressDomain.getProvince()==null
                ){
            return errorResult("地址信息不全");
        }
        return successResult("地址可用");
    }

    /**
     * 设置收货地址
     * @return
     */
    @RequestMapping(value = "setAddress", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult setShipAddress(Long addressId){
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();

        CustomerAddressDomain customerAddressDomain =  customerAddressService.get(addressId);
        OrderDomain orderDomain = (OrderDomain)session.getAttribute(ORDER);
        orderDomain.setCustomerAddressDomain(customerAddressDomain);
        orderDomain.setStoreDomain(null);
        if(orderDomain == null) {
            return errorResult("页面失效");
        }
        orderDomain.setShipPhone(customerAddressDomain.getPhone());
        orderDomain.setShipFirstName(customerAddressDomain.getFirstName());
        orderDomain.setShipLastName(customerAddressDomain.getLastName());
        orderDomain.setShipTitle(customerAddressDomain.getTitle());
        orderDomain.setShipCity(customerAddressDomain.getCity());
        orderDomain.setShippingCountryId(customerAddressDomain.getCountryId());
        orderDomain.setShipCountry(customerAddressDomain.getCountryId()+"");
        orderDomain.setShipProvince(customerAddressDomain.getProvince());
        orderDomain.setShipAddress(customerAddressDomain.getAddress());
        orderDomain.setShipMemo(customerAddressDomain.getMemo());
        orderDomain.setShipAddressId(customerAddressDomain.getId());
        orderDomain.setShippingMethod(ShippingMethodEnum.EXPRESS.getValue());
        session.setAttribute(ORDER,orderDomain);
        return successResult("操作成功");
    }



    /**
     * 初始化自提门店
     * @return
     */
    @RequestMapping(value = "listStore",method = RequestMethod.GET)
    public  ModelAndView listStore(){
        List<StoreDomain> storeDomainList = storeService.getList(new StoreQuery());
        ModelAndView modelAndView= new ModelAndView("checkout/listStore");
        List<StoreCountryDomain> storeCountryList  = storeCountryService.getList(new StoreCountryQuery());
        modelAndView.addObject("storeDomainList",storeDomainList);
        modelAndView.addObject("storeCountryList",storeCountryList);
        return modelAndView;
    }

    @RequestMapping(value = "initCity" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult initCity(String countryId){
        StoreCityQuery query = new StoreCityQuery();
        query.setCountryId(countryId);
        List<StoreCityDomain>  storeCityList = storeCityService.getList(query);
        return successResult("初始化城市", JsonUtils.toJSONString(storeCityList));
    }

    @RequestMapping(value = "initStore" ,method = RequestMethod.POST)
    @ResponseBody
    public JsonResult initStore(String countryId,String cityId){
        StoreQuery query = new StoreQuery();
        query.setCityId(cityId);
        query.setCountryId(countryId);
        List<StoreDomain> storeList = storeService.getList(query);
        return successResult("初始化门店",JsonUtils.toJSONString(storeList));
    }


    /**
     * 设置自提门店
     * @return
     */
    @RequestMapping(value = "setStore", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult setStore(Long storeId){
        if(storeId==null){
            return errorResult("参数为空");
        }
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        OrderDomain order = (OrderDomain)session.getAttribute(ORDER);
        if(order==null){
            return errorResult("订单失效");
        }
        order.setShippingMethod(ShippingMethodEnum.STORE.getValue());
        order.setStoreId(storeId);
        order.setStoreDomain(storeService.get(storeId));
        order.setCustomerAddressDomain(null);
        session.setAttribute(ORDER,order);
        return successResult("操作成功");
    }

    /**
     * 设置支付方式
     * @return
     */
    @RequestMapping(value = "setPaymentMethod", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult setPaymentMethod(Integer paymentId){
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        OrderDomain order = (OrderDomain)session.getAttribute(ORDER);
        if(order==null){
            return errorResult("订单已过期");
        }
        order.setPaymentMethod(paymentId);
        session.setAttribute(ORDER,order);
        return successResult("操作成功");
    }

    /**
     * 设置配送方式
     * @return
     */
    @RequestMapping(value = "setShippingMethod", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult setShippingMethod(Integer shippingMethodId){
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        OrderDomain order = (OrderDomain)session.getAttribute(ORDER);
        order.setShippingMethod(shippingMethodId);
        session.setAttribute(ORDER,order);
        return successResult("操作成功");
    }

    @RequestMapping(value = "isNeedBill", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult isNeedBill(Integer isNeed,String info){
        if(isNeed==null){
            return errorResult("参数为空");
        }
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        OrderDomain order = (OrderDomain)session.getAttribute(ORDER);
        if(order==null){
            return errorResult("订单已过期");
        }
        order.setBillRequired(isNeed);
        order.setBillTitle(info);
        session.setAttribute(ORDER,order);
        return successResult("操作成功");
    }

    /**
     * 使用优惠码
     * @param couponCode
     * @return
     */
    @RequestMapping(value = "useCoupon", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult useCoupon(String couponCode){
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        OrderDomain order = (OrderDomain)session.getAttribute(ORDER);
        CouponDomain couponDomain = couponService.checkCoupon(couponCode);
        Double rate  = shippingCountryService.get(order.getShippingCountryId()).getRate();
        Double trueDiscountPrice = 0D;
        if(couponDomain!=null){
            order.setCouponId(couponDomain.getId());
            Double orderTotal = order.getOrderTotal();
            switch (couponDomain.getRuleType()) {
                case 0://全单打折 无限次
                    trueDiscountPrice = orderTotal*(1-couponDomain.getDiscount());
                    System.out.println(0);
                    break;
                case 1://全单满减 无限次
                    if(couponDomain.getSatisfyTop()/rate>=orderTotal){
                        trueDiscountPrice = couponDomain.getDiscountPrice()/rate;
                    }else{
                        return errorResult("优惠条件不符");
                    }
                    System.out.println(1);
                    break;
                case 2://抵扣券 1次
                    trueDiscountPrice = couponDomain.getDiscountPrice()/rate;
                    System.out.println(2);
                    break;
                case 3://折扣券 1次
                    trueDiscountPrice = orderTotal*(1-couponDomain.getDiscount());
                    System.out.println(3);
                    break;
            }
            System.out.println("优惠价："+trueDiscountPrice);
            trueDiscountPrice = new BigDecimal(trueDiscountPrice).setScale(0,BigDecimal.ROUND_HALF_DOWN).doubleValue();
            order.setCouponDiscount(trueDiscountPrice);
            session.setAttribute(ORDER,order);
        }
        return successResult("操作成功",trueDiscountPrice);
    }

    /**
    * 取消使用优惠券
    *
     * */
    @RequestMapping(value = "cancelUseCoupon", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult cancelUseCoupon(){
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        OrderDomain order = (OrderDomain)session.getAttribute(ORDER);
        order.setCouponId(null);
        order.setCouponDiscount(0D);
        session.setAttribute(ORDER,order);
        return successResult("操作成功");
    }

    @RequestMapping(value = "deleteGoods", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult deleteGoods(Long orderItemId){
        if(orderItemId==null){
            return errorResult("参数错误");
        }
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        List<ShoppingCartItemDomain> cartList = (List<ShoppingCartItemDomain>)session.getAttribute(CART_LIST);
        for( int i =0;cartList!=null&&cartList.size()>0&&i<cartList.size();i++){
            if(cartList.get(i).getId()==orderItemId){
                shoppingCartService.delete(orderItemId);
                cartList.remove(i);
                break;
            }
        }
        session.setAttribute(CART_LIST,cartList);
        return successResult("操作成功");
    }

    /**
     * 订单完成
     * @param orderNo
     * @return
     */
    public ModelAndView completed(String orderNo){
        ModelAndView mv  = new ModelAndView();
        return mv;
    }

    public BigDecimal calculateSub(Double price,Integer num){
        BigDecimal sub = BigDecimal.ZERO;
        sub = new BigDecimal(num).multiply(new BigDecimal(price));
        return sub;
    }
}
