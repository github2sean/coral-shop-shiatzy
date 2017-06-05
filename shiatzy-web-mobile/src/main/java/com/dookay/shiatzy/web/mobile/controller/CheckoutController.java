package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.utils.BeanValidators;
import com.dookay.coral.common.utils.RandomUtils;
import com.dookay.coral.common.web.BaseController;
import com.dookay.coral.common.web.HttpContext;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.host.user.context.UserContext;
import com.dookay.coral.shop.customer.domain.CustomerAddressDomain;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.query.CustomerAddressQuery;
import com.dookay.coral.shop.customer.service.ICustomerAddressService;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.goods.domain.SkuDomain;
import com.dookay.coral.shop.goods.service.ISkuService;
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
import com.dookay.shiatzy.web.mobile.model.AddressModel;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


    private static String CART_LIST = "cartList";
    private static String ORDER = "order";

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
        String countryId = session.getAttribute(HomeController.SHIPPING_COUNTRY_ID)+"";
        ShippingCountryDomain shippingCountryDomain = null;
        if(StringUtils.isNotBlank(countryId)){
            shippingCountryDomain = shippingCountryService.get(Long.parseLong(countryId));
        }
        //根据国家获取值
        order.setShipFee(shippingCountryDomain==null?0D:shippingCountryDomain.getShippingCost());
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

    /**
     * 结算页面
     * @return
     */
    @RequestMapping(value = "orderInfo",method = RequestMethod.GET)
    public ModelAndView orderInfo(){
        UserContext userContext = UserContext.current();

        Long accountId = UserContext.current().getAccountDomain().getId();
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
        if(cartList==null || cartList.size()==0){
            return new ModelAndView("redirect:/home/index");
        }
        //商品金额
        calcOrderTotal(orderDomain, cartList);
        ModelAndView mv=  new ModelAndView("checkout/orderInfo");
        mv.addObject(CART_LIST,cartList);
        mv.addObject(ORDER,orderDomain);
        return mv;
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
        //购物车
        List<ShoppingCartItemDomain> cartList = shoppingCartService.listShoppingCartItemByCustomerId(customerDomain.getId(), ShoppingCartTypeEnum.SHOPPING_CART.getValue());
        shoppingCartService.withGoodsItem(cartList);
        if(cartList==null || cartList.size()==0){
            return new ModelAndView("redirect:/home/index");
        }
        calcOrderTotal(orderDomain, cartList);
        //配送方式
        CustomerAddressQuery customerAddressQuery = new CustomerAddressQuery();
        customerAddressQuery.setCustomerId(customerDomain.getId());
        CustomerAddressDomain customerAddressDomain = customerAddressService.getFirst(customerAddressQuery);
        if(customerAddressDomain !=null){
            orderDomain.setShippingMethod(ShippingMethodEnum.EXPRESS.getValue());
            orderDomain.setShipAddressId(customerAddressDomain.getId());
            orderDomain.setShippingCountryId(customerAddressDomain.getCountryId());
            orderDomain.setShipProvince(customerAddressDomain.getProvince());
            orderDomain.setShipCity(customerAddressDomain.getCity());
            orderDomain.setShipFirstName(customerAddressDomain.getFirstName());
            orderDomain.setShipLastName(customerAddressDomain.getLastName());
            orderDomain.setShipTitle(customerAddressDomain.getTitle());
            orderDomain.setShipPhone(customerAddressDomain.getPhone());
            orderDomain.setShipAddress(customerAddressDomain.getAddress());
            orderDomain.setShipMemo(customerAddressDomain.getMemo());
            orderDomain.setOrderTime(new Date());
        }

        ModelAndView mv=  new ModelAndView("checkout/confirm");
        mv.addObject(CART_LIST,cartList);
        mv.addObject(ORDER,orderDomain);
        session.setAttribute("referrerPage",page);
        return mv;
    }

    /**
     * 提交订单
     * @return
     */
    @RequestMapping(value = "submitOrder", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult submitOrder(){
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        //从session中获取订单对象,对象至少包含商品列表、优惠券
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        OrderDomain order = (OrderDomain)session.getAttribute(ORDER);
        if(order== null){
            return errorResult("订单已经失效");
        }
        //购物车
        List<ShoppingCartItemDomain> cartList = shoppingCartService.listShoppingCartItemByCustomerId(customerDomain.getId(), ShoppingCartTypeEnum.SHOPPING_CART.getValue());
        shoppingCartService.withGoodsItem(cartList);
        if(cartList== null || cartList.size() ==0){
            return errorResult("订单已经失效");
        }
        //持久化订单，验证优惠券码是否可用，商品库存是否足够
        Long couponId  = order.getCouponId();
        if(couponId!=null ){
            CouponDomain couponDomain = couponService.get(couponId);
            couponService.checkCoupon(couponDomain.getCode());
        }
        List<Long> itemIds = new ArrayList<Long>();
        //创建订单
        order.setOrderNo(RandomUtils.buildNo());
        order.setCustomerId(customerDomain.getId());
        order.setStatus(OrderStatusEnum.UNPAID.getValue());
        CustomerAddressDomain customerAddressDomain = customerAddressService.get(order.getShipAddressId());
        order.setShipPostalCode(customerAddressDomain.getPostalCode());//邮编
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
            orderItemDomain.setGoodsName(items.getGoodsName());
            orderItemDomain.setGoodsCode(items.getGoodsCode());
            orderItemDomain.setGoodsPrice(items.getGoodsPrice());
            orderItemDomain.setSkuSpecifications(items.getSkuSpecifications());
            orderItemDomain.setStatus(0);
            orderItemDomain.setReturnNum(0);
            System.out.println("order:"+ JsonUtils.toJSONString(order));
            orderItemService.create(orderItemDomain);
        }

        //清除session
        session.setAttribute(ORDER,null);
        //清除购物车
        for(int i=0 ;i<cartList.size();i++){
            shoppingCartService.removeFromCart(cartList.get(i).getId());
        }

        Long orderId = order.getId();
        if(itemIds!=null && itemIds.size()>0){
            return successResult("商品库存不足",itemIds);
        }
        return successResult("操作成功",order);
    }

    private void calcOrderTotal(OrderDomain orderDomain, List<ShoppingCartItemDomain> cartList) {
        //商品金额
        Double goodsTotal = 0D;
        for (ShoppingCartItemDomain shoppingCartItemDomain :cartList){
            goodsTotal  = goodsTotal+ shoppingCartItemDomain.getGoodsPrice()* shoppingCartItemDomain.getNum();
        }
        orderDomain.setGoodsTotal(goodsTotal);
        Double couponDiscount = orderDomain.getCouponDiscount()==null?0D:orderDomain.getCouponDiscount();
        Double memberDiscount = orderDomain.getMemberDiscount()==null?0D:orderDomain.getMemberDiscount();
        Double shipFee = orderDomain.getShipFee()==null?0D:orderDomain.getShipFee();
        Double orderTotal = goodsTotal + shipFee -couponDiscount - memberDiscount;
        orderDomain.setOrderTotal(orderTotal);
    }

    /**
     * 配送地址列表页面
     * @return
     */
    @RequestMapping(value = "listShipAddress",method = RequestMethod.GET)
    public  ModelAndView listShipAddress(){
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        CustomerAddressQuery query = new CustomerAddressQuery();
        query.setCustomerId(customerDomain.getId());
        List addressList = customerAddressService.getList(query);
        ModelAndView mv = new ModelAndView("checkout/listShipAddress");
        mv.addObject("addressList",addressList);
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
        if(orderDomain == null)
        {
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
        if(couponDomain!=null){
            order.setCouponId(couponDomain.getId());
            order.setCouponDiscount(couponDomain.getDiscountPrice());
            session.setAttribute(ORDER,order);
        }
        return successResult("操作成功",couponDomain.getDiscountPrice());
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
