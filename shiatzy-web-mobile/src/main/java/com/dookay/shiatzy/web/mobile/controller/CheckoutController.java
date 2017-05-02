package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.common.web.BaseController;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.shiatzy.web.mobile.model.AddressModel;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/2
 */
public class CheckoutController  extends BaseController{

    /**
     * 从购物车初始化订单
     * @return
     */
    public String initOrder(){
        //从购物获取商品列表
        //创建订单对象
        //保存订单对象到session
        //跳转到结算页面
        return "redirect:orderInfo";
    }

    /**
     * 结算页面
     * @return
     */
    public ModelAndView orderInfo(){
        ModelAndView mv = new ModelAndView();
        //获取订单session
        //如果session为空，跳转到商品列表页面

        return mv;
    }

    /**
     * 配送地址列表页面
     * @return
     */
    public  ModelAndView listAddress(){
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    public  ModelAndView addAddress(){
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    @RequestMapping(value = "/addAddress", method = RequestMethod.POST)
    public  JsonResult addAddress(@ModelAttribute AddressModel addressModel){
        return successResult("操作成功");
    }

    /**
     * 设置收货地址
     * @return
     */
    public JsonResult setAddress(){
        return successResult("操作成功");
    }

    /**
     * 设置自提门店
     * @return
     */
    public JsonResult setStore(){
        return successResult("操作成功");
    }

    /**
     * 设置支付方式
     * @return
     */
    public JsonResult setPaymentMethod(){
        return successResult("操作成功");
    }

    /**
     * 设置配送方式
     * @return
     */
    public JsonResult setShippingMethod(){
        return successResult("操作成功");
    }

    /**
     * 使用优惠码
     * @param couponCode
     * @return
     */
    public JsonResult useCoupon(String couponCode){
        return successResult("操作成功");
    }

    /**
     * 提交订单
     * @return
     */
    public JsonResult submitOrder(){
        //从session中获取订单对象,对象至少包含商品列表、优惠券
        //持久化订单，验证优惠券码是否可用，商品库存是否足够
        //清除session
        //清除购物车
        String orderNo = "";
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
