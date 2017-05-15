package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.adapter.payment.alipay.config.AlipayConfig;
import com.dookay.coral.adapter.payment.alipay.util.AlipayNotify;
import com.dookay.coral.adapter.payment.alipay.util.AlipaySubmit;
import com.dookay.coral.shop.order.domain.OrderDomain;
import com.dookay.coral.shop.order.enums.OrderStatusEnum;
import com.dookay.coral.shop.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/2
 */
public class PaymentContoller {

    @Autowired
    private AlipayConfig alipayConfig;

    @Autowired
    private IOrderService orderService;

    /**
     * 第三方支付提交页面
     * @param paymentMethod
     * @param orderNo
     * @return
     */
    public ModelAndView buildPayment(Integer paymentMethod,String orderNo){
        ModelAndView mv = new ModelAndView();
        OrderDomain orderDomain = orderService.getOrder(orderNo);
        //获取第三方支付配置
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", alipayConfig.getService());
        sParaTemp.put("partner", alipayConfig.getPartner());
        sParaTemp.put("seller_id", alipayConfig.getSeller_id());
        sParaTemp.put("_input_charset", alipayConfig.getInput_charset());
        sParaTemp.put("payment_type", alipayConfig.getPayment_type());
        sParaTemp.put("notify_url", alipayConfig.getNotify_url());
        sParaTemp.put("return_url", alipayConfig.getReturn_url());
        sParaTemp.put("out_trade_no", orderNo);//商户订单号，商户网站订单系统中唯一订单号，必填
        sParaTemp.put("subject", orderDomain.getOrderNo());//订单名称，必填
        sParaTemp.put("total_fee", String.format("%.2f", orderDomain.getOrderTotal())); //付款金额，必填
        sParaTemp.put("show_url", "");//收银台页面上，商品展示的超链接，必填
        //sParaTemp.put("app_pay","Y");//启用此参数可唤起钱包APP支付。
        sParaTemp.put("body", ""); //商品描述，可空
        //其他业务参数根据在线开发文档，添加参数.文档地址:https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.2Z6TSk&treeId=60&articleId=103693&docType=1

        //建立请求
        String form = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
        mv.addObject("form",form);
        return mv;
    }

    /**
     * 第三方支付异步回调
     * @return
     */
    @ResponseBody
    public String asynReturnUrl(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
        //交易金额
        String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"),"UTF-8");
        //商户
        String seller_id = new String(request.getParameter("seller_id").getBytes("ISO-8859-1"),"UTF-8");
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

        if(AlipayNotify.verify(params)){//验证成功
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码

            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
            if(trade_status.equals("TRADE_FINISHED")){

                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            } else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序
                OrderDomain orderDomain = orderService.getOrder(out_trade_no);
                if(orderDomain.getStatus() != OrderStatusEnum.UNPAID.getValue() &&
                        total_fee.equals(String.format("%.2f",orderDomain.getOrderTotal()))&&
                        seller_id.equals(alipayConfig.getSeller_id())){
                    orderDomain.setPaidTime(new Date());
                    orderDomain.setStatus(OrderStatusEnum.PAID.getValue());
                    orderService.update(orderDomain);
                }
                //注意：
                //付款完成后，支付宝系统发送该交易状态通知
            }

            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

            return "success";	//请不要修改或删除

            //////////////////////////////////////////////////////////////////////////////////////////
        }else{//验证失败
            return "fail";
        }
        //根据订单号获取订单
        //判断订单是否已经完成支付
        //如果订单状态不是已支付，调用订单支付服务
    }

    /**
     * 第三方支付同步回调页面
     * @return
     */
    public ModelAndView returnUrl(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号

        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //支付宝交易号

        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
        //交易金额
        String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"),"UTF-8");
        //商户
        String seller_id = new String(request.getParameter("seller_id").getBytes("ISO-8859-1"),"UTF-8");
        //计算得出通知验证结果
        boolean verify_result = AlipayNotify.verify(params);

        String message="";
        if(verify_result){//验证成功
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码
            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
            if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序
                OrderDomain orderDomain = orderService.getOrder(out_trade_no);
                if(orderDomain.getStatus() != OrderStatusEnum.UNPAID.getValue() &&
                        total_fee.equals(String.format("%.2f",orderDomain.getOrderTotal()))&&
                        seller_id.equals(alipayConfig.getSeller_id())){
                    orderDomain.setPaidTime(new Date());
                    orderDomain.setStatus(OrderStatusEnum.PAID.getValue());
                    orderService.update(orderDomain);
                }
            }
            //该页面可做页面美工编辑
            message = "验证成功";
        }else{
            //该页面可做页面美工编辑
            message = "验证失败";
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("message",message);
        return mv;
    }
}
