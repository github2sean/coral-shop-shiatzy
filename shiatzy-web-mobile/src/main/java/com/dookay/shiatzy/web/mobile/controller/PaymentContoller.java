package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.common.web.JsonResult;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/2
 */
public class PaymentContoller {

    /**
     * 第三方支付提交页面
     * @param paymentMethod
     * @param orderNo
     * @return
     */
    public ModelAndView buildPayment(Integer paymentMethod,String orderNo){
        ModelAndView mv = new ModelAndView();
        //获取第三方支付配置
        return mv;
    }

    /**
     * 第三方支付异步回调
     * @return
     */
    @ResponseBody
    public String asynReturnUrl(){
        //根据订单号获取订单
        //判断订单是否已经完成支付
        //如果订单状态不是已支付，调用订单支付服务
        return "ok";
    }

    /**
     * 第三方支付同步回调页面
     * @return
     */
    public ModelAndView returnUrl(){
        ModelAndView mv = new ModelAndView();

        return mv;
    }
}
