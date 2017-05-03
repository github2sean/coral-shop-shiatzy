package com.dookay.shiatzy.web.mobile.controller;

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
import com.dookay.coral.shop.goods.query.SkuQuery;
import com.dookay.coral.shop.goods.service.ISkuService;
import com.dookay.coral.shop.order.domain.OrderDomain;
import com.dookay.coral.shop.order.domain.OrderItemDomain;
import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;
import com.dookay.coral.shop.order.enums.ShoppingCartTypeEnum;
import com.dookay.coral.shop.order.service.IOrderItemService;
import com.dookay.coral.shop.order.service.IOrderService;
import com.dookay.coral.shop.order.service.IShoppingCartService;
import com.dookay.coral.shop.promotion.domain.CouponDomain;
import com.dookay.coral.shop.promotion.query.CouponQuery;
import com.dookay.coral.shop.promotion.service.ICouponService;
import com.dookay.shiatzy.web.mobile.model.AddressModel;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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

    private static String CART_LIST = "cartList";
    private static String ODER = "order";
    /**
     * 从购物车初始化订单
     * @return
     */
    @RequestMapping(value = "initOrder",method = RequestMethod.GET)
    public String initOrder(){
        //从购物获取商品列表
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        List<ShoppingCartItemDomain> cartList = shoppingCartService.listShoppingCartItemByCustomerId(customerDomain.getId(), ShoppingCartTypeEnum.SHOPPING_CART.getValue());
        //创建订单对象
        OrderDomain order = new OrderDomain();
        order.setCustomerId(customerDomain.getId());
        //保存订单对象到session
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(CART_LIST,cartList);
        session.setAttribute(ODER,order);
        //跳转到结算页面
        return "redirect:orderInfo";
    }

    /**
     * 结算页面
     * @return
     */
    @RequestMapping(value = "orderInfo",method = RequestMethod.GET)
    public ModelAndView orderInfo(){
        //获取订单session
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        //如果session为空，跳转到商品列表页面
        List cartList = (List) session.getAttribute(CART_LIST);
        if(cartList==null){
            return new ModelAndView("redirect:home/index");
        }
        ModelAndView mv=  new ModelAndView("checkout/orderInfo");
        mv.addObject(CART_LIST,cartList);
        return mv;
    }

    /**
     * 配送地址列表页面
     * @return
     */
    @RequestMapping(value = "listAddress",method = RequestMethod.GET)
    public  ModelAndView listAddress(){
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        CustomerAddressQuery query = new CustomerAddressQuery();
        query.setCustomerId(customerDomain.getId());
        List addressList = customerAddressService.getList(query);
        ModelAndView mv = new ModelAndView();
        mv.addObject("addressList",addressList);
        return mv;
    }

    @RequestMapping(value = "addAddress",method = RequestMethod.GET)
    public  ModelAndView addAddress(){
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    @RequestMapping(value = "addAddress", method = RequestMethod.POST)
    public  JsonResult addAddress(@ModelAttribute AddressModel addressModel){
        Long accountId = UserContext.current().getAccountDomain().getId();
        CustomerDomain customerDomain = customerService.getAccount(accountId);
        CustomerAddressDomain customerAddressDomain = new CustomerAddressDomain();
        customerAddressDomain.setCustomerId(customerDomain.getId());
        customerAddressDomain.setPhone(customerDomain.getPhone());
        customerAddressDomain.setLastName(customerDomain.getLastName());
        customerAddressDomain.setFirstName(customerDomain.getFirstName());

        customerAddressDomain.setMemo(addressModel.getMemo());
        customerAddressDomain.setTitle(addressModel.getTitle());
        customerAddressDomain.setCountryId(addressModel.getCountryId());
        customerAddressDomain.setCityId(addressModel.getCityId());
        customerAddressDomain.setProvinceId(addressModel.getProvinceId());
        customerAddressDomain.setAdress(addressModel.getAdress());

        customerAddressService.create(customerAddressDomain);
        return successResult("操作成功");
    }

    /**
     * 设置收货地址
     * @return
     */
    @RequestMapping(value = "setAddress", method = RequestMethod.POST)
    public JsonResult setAddress(Long addressId){
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();

        CustomerAddressDomain customerAddressDomain =  customerAddressService.get(addressId);
        OrderDomain orderDomain = (OrderDomain)session.getAttribute(ODER);

        orderDomain.setShipPhone(customerAddressDomain.getPhone());
        orderDomain.setShipName(customerAddressDomain.getFirstName()+customerAddressDomain.getLastName());
        orderDomain.setShipTitle(customerAddressDomain.getTitle());
        orderDomain.setShipCity(customerAddressDomain.getCityId()+"");
        orderDomain.setShipCountry(customerAddressDomain.getCountryId()+"");
        orderDomain.setShipProvince(customerAddressDomain.getProvinceId()+"");
        orderDomain.setShipAddress(customerAddressDomain.getAdress());
        orderDomain.setShipMemo(customerAddressDomain.getMemo());

        session.setAttribute(ODER,orderDomain);
        return successResult("操作成功");
    }

    /**
     * 设置自提门店
     * @return
     */
    @RequestMapping(value = "setStore", method = RequestMethod.POST)
    public JsonResult setStore(){

        //数据库暂无门店

        return successResult("操作成功");
    }

    /**
     * 设置支付方式
     * @return
     */
    @RequestMapping(value = "setPaymentMethod", method = RequestMethod.POST)
    public JsonResult setPaymentMethod(Integer paymentId){
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        OrderDomain order = (OrderDomain)session.getAttribute(ODER);
        order.setPaymentMethod(paymentId);
        session.setAttribute(ODER,order);
        return successResult("操作成功");
    }

    /**
     * 设置配送方式
     * @return
     */
    @RequestMapping(value = "setShippingMethod", method = RequestMethod.POST)
    public JsonResult setShippingMethod(Integer shippingMethodId){
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        OrderDomain order = (OrderDomain)session.getAttribute(ODER);
        order.setShippingMethod(shippingMethodId);
        session.setAttribute(ODER,order);
        return successResult("操作成功");
    }

    /**
     * 使用优惠码
     * @param couponCode
     * @return
     */
    @RequestMapping(value = "useCoupon", method = RequestMethod.POST)
    public JsonResult useCoupon(String couponCode){
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        OrderDomain order = (OrderDomain)session.getAttribute(ODER);
        CouponDomain couponDomain = couponService.checkCoupon(couponCode);
        if(couponDomain!=null){
            order.setCouponId(couponDomain.getId());
            session.setAttribute(ODER,order);
        }
        return successResult("操作成功");
    }

    /**
     * 提交订单
     * @return
     */
    @RequestMapping(value = "submitOrder", method = RequestMethod.POST)
    public JsonResult submitOrder(){
        //从session中获取订单对象,对象至少包含商品列表、优惠券
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        OrderDomain order = (OrderDomain)session.getAttribute(ODER);
        List<ShoppingCartItemDomain> cartList = (List<ShoppingCartItemDomain>)session.getAttribute(CART_LIST);
        //持久化订单，验证优惠券码是否可用，商品库存是否足够
        Long couponId  = order.getCouponId();
        CouponDomain couponDomain = couponService.get(couponId);
        List<Long> itemIds = new ArrayList<Long>();
        if(couponService.checkCoupon(couponDomain.getCode())!=null){
            //创建明细
            for(int j = 0;cartList!=null&&cartList.size()>0&&j<cartList.size();j++){
                ShoppingCartItemDomain items = cartList.get(j);
                OrderItemDomain orderItemDomain = new OrderItemDomain();
                orderItemDomain.setOrderId(order.getId());
                SkuDomain skuDomain = skuService.get(items.getSkuId());
                if (skuDomain.getQuantity()<=0){
                    itemIds.add(skuDomain.getGoodsId());
                    continue;
                }
                orderItemDomain.setSkuId(items.getSkuId());
                orderItemDomain.setNum((long)items.getNum());
                orderItemDomain.setGoodsName(items.getGoodsName());
                orderItemDomain.setGoodsCode(items.getGoodsCode());
                orderItemDomain.setGoodsPrice(items.getGoodsPrice());
                orderItemDomain.setSkuSpecifications(items.getSkuSpecifications());
                orderService.create(order);
                orderItemService.create(orderItemDomain);
            }
        }

        //清除session
        session.setAttribute(ODER,null);
        session.setAttribute(CART_LIST,null);
        //清除购物车

        for(int i=0 ;cartList!=null && cartList.size()>0 && i<cartList.size();i++){
            shoppingCartService.removeFromCart(cartList.get(i).getId());
        }

        String orderNo = order.getOrderNo();
        if(itemIds!=null && itemIds.size()>0){
            return successResult("商品库存不足",itemIds);
        }
        return successResult("操作成功",orderNo);
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
}
