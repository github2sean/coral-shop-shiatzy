package com.dookay.shiatzy.web.mobile.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.dookay.coral.adapter.payment.alipay.config.AlipayConfig;
import com.dookay.coral.adapter.payment.alipay.util.AlipayNotify;
import com.dookay.coral.adapter.payment.alipay.util.AlipaySubmit;
import com.dookay.coral.adapter.payment.unionpay.acp.config.UnionConfig;
import com.dookay.coral.adapter.payment.unionpay.acp.demo.DemoBase;
import com.dookay.coral.adapter.payment.unionpay.acp.sdk.AcpService;
import com.dookay.coral.adapter.payment.unionpay.acp.sdk.LogUtil;
import com.dookay.coral.adapter.payment.unionpay.acp.sdk.SDKConfig;
import com.dookay.coral.adapter.payment.unionpay.acp.sdk.SDKConstants;
import com.dookay.coral.common.exception.BaseException;
import com.dookay.coral.common.web.BaseController;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.shop.order.domain.OrderDomain;
import com.dookay.coral.shop.order.enums.OrderStatusEnum;
import com.dookay.coral.shop.order.service.IOrderService;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/2
 */
@SuppressWarnings(value = "all")
@Controller
@RequestMapping("payment/")
public class PaymentContoller extends BaseController{

    @Autowired
    private AlipayConfig alipayConfig;

    @Autowired
    private UnionConfig unionConfig;

    @Autowired
    private IOrderService orderService;

    /**
     * 第三方支付提交页面
     * @param paymentMethod
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "buildPayment",method = RequestMethod.GET)
    public ModelAndView buildPayment(Integer paymentMethod,String orderNo){


        ModelAndView mv = new ModelAndView("payment/buildPayment");
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
        //sParaTemp.put("total_fee", String.format("%.2f", 0.01d));
        sParaTemp.put("show_url", "");//收银台页面上，商品展示的超链接，必填
        //sParaTemp.put("app_pay","Y");//启用此参数可唤起钱包APP支付。
        sParaTemp.put("body", ""); //商品描述，可空
        //其他业务参数根据在线开发文档，添加参数.文档地址:https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.2Z6TSk&treeId=60&articleId=103693&docType=1

        //建立请求
        String form = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
        mv.addObject("form",form);
        System.out.println(form);
        return mv;
    }

    /**
     * 第三方支付异步回调
     * @return
     */
    @RequestMapping(value = "asynReturnUrl",method = RequestMethod.POST)
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
                if(orderDomain.getStatus() == OrderStatusEnum.UNPAID.getValue() &&
                        total_fee.equals(String.format("%.2f",orderDomain.getOrderTotal()))&&
                        seller_id.equals(alipayConfig.getSeller_id())){

                    updateOrderStatus(orderDomain);
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
    @RequestMapping(value = "returnUrl",method = RequestMethod.GET)
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
        OrderDomain orderDomain = orderService.getOrder(out_trade_no);
        String message="";
        if(verify_result){//验证成功
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码
            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
            if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序
                if(orderDomain.getStatus() == OrderStatusEnum.UNPAID.getValue() &&
                        total_fee.equals(String.format("%.2f",orderDomain.getOrderTotal()))&&
                        seller_id.equals(alipayConfig.getSeller_id())){

                    updateOrderStatus(orderDomain);
                }
            }
            //该页面可做页面美工编辑
            message = "验证成功";
        }else{
            //该页面可做页面美工编辑
            message = "验证失败";
        }
        ModelAndView mv = new ModelAndView("payment/returnUrl");
        mv.addObject("message",message);
        mv.addObject("order",orderDomain);
        return mv;
    }

    @RequestMapping(value = "initUnionPay",method = RequestMethod.GET)
    public ModelAndView initUnionPay(HttpServletRequest req,String orderNo,HttpServletResponse resp){
        resp.setContentType("text/html; charset="+ unionConfig.getEncoding());
        SDKConfig.getConfig().loadPropertiesFromSrc();
        //前台页面传过来的
        OrderDomain orderDomain = orderService.getOrder(orderNo);
        Double txnAmt = orderDomain.getOrderTotal();

        Map<String, String> requestData = new HashMap<String, String>();
        /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
        requestData.put("version", unionConfig.getVersion());   			  //版本号，全渠道默认值
        requestData.put("encoding", unionConfig.getEncoding()); 			  //字符集编码，可以使用UTF-8,GBK两种方式
        requestData.put("signMethod", unionConfig.getSignMethod());           //签名方法，只支持 01：RSA方式证书加密
        requestData.put("txnType", unionConfig.getTxnType());                 //交易类型 ，01：消费
        requestData.put("txnSubType", unionConfig.getTxnSubType());           //交易子类型， 01：自助消费
        requestData.put("bizType", unionConfig.getBizType());           	  //业务类型，B2C网关支付，手机wap支付
        requestData.put("channelType", unionConfig.getChannelType());         //渠道类型，这个字段区分B2C网关支付和手机wap支付；07：PC,平板  08：手机

        /***商户接入参数***/
        requestData.put("merId", unionConfig.getMerId());    	          	  //商户号码，请改成自己申请的正式商户号或者open上注册得来的777测试商户号
        requestData.put("accessType", unionConfig.getAccessType());             			  //接入类型，0：直连商户
        requestData.put("orderId",orderNo);                           //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
        requestData.put("txnTime", DemoBase.getCurrentTime());        //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
        requestData.put("currencyCode", unionConfig.getCurrencyCode());         			  //交易币种（境内商户一般是156 人民币）
        requestData.put("txnAmt", amtRemovePoint(txnAmt));             			      //交易金额，单位分，不要带小数点
        //requestData.put("reqReserved", "透传字段");        		      //请求方保留域，如需使用请启用即可；透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节

        //前台通知地址 （需设置为外网能访问 http https均可），支付成功后的页面 点击“返回商户”按钮的时候将异步通知报文post到该地址
        //如果想要实现过几秒中自动跳转回商户页面权限，需联系银联业务申请开通自动返回商户权限
        //异步通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
        requestData.put("frontUrl", unionConfig.getFrontUrl());

        //后台通知地址（需设置为【外网】能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，失败的交易银联不会发送后台通知
        //后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
        //注意:1.需设置为外网能访问，否则收不到通知    2.http https均可  3.收单后台通知后需要10秒内返回http200或302状态码
        //    4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200，那么银联会间隔一段时间再次发送。总共发送5次，每次的间隔时间为0,1,2,4分钟。
        //    5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
        requestData.put("backUrl", unionConfig.getBackUrl());

        // 订单超时时间。
        // 超过此时间后，除网银交易外，其他交易银联系统会拒绝受理，提示超时。 跳转银行网银交易如果超时后交易成功，会自动退款，大约5个工作日金额返还到持卡人账户。
        // 此时间建议取支付时的北京时间加15分钟。
        // 超过超时时间调查询接口应答origRespCode不是A6或者00的就可以判断为失败。
        requestData.put("payTimeout", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime() + 15 * 60 * 1000));

        //////////////////////////////////////////////////
        //
        //       报文中特殊用法请查看 PCwap网关跳转支付特殊用法.txt
        //
        //////////////////////////////////////////////////

        /**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
        Map<String, String> submitFromData = AcpService.sign(requestData,unionConfig.getEncoding());  //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。

        String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();  //获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
        String html = AcpService.createAutoFormHtml(requestFrontUrl, submitFromData,unionConfig.getEncoding());   //生成自动跳转的Html表单

        LogUtil.writeLog("打印请求HTML，此为请求报文，为联调排查问题的依据："+html);
        //将生成的html写到浏览器中完成自动跳转打开银联支付页面；这里调用signData之后，将html写到浏览器跳转到银联页面之前均不能对html中的表单项的名称和值进行修改，如果修改会导致验签不通过

        ModelAndView mv = new ModelAndView("payment/initUnionPay");
        mv.addObject("html",html);
        //resp.getWriter().write(html);
        return mv;
    }



    @RequestMapping(value = "backRcvResponse",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult backRcvResponse(HttpServletRequest req)throws ServletException, IOException{

        LogUtil.writeLog("BackRcvResponse接收后台通知开始");

        String encoding = req.getParameter(SDKConstants.param_encoding);
        // 获取银联通知服务器发送的后台通知参数
        Map<String, String> reqParam = getAllRequestParam(req);

        LogUtil.printRequestLog(reqParam);

        Map<String, String> valideData = null;
        if (null != reqParam && !reqParam.isEmpty()) {
            Iterator<Map.Entry<String, String>> it = reqParam.entrySet().iterator();
            valideData = new HashMap<String, String>(reqParam.size());
            while (it.hasNext()) {
                Map.Entry<String, String> e = it.next();
                String key = (String) e.getKey();
                String value = (String) e.getValue();
                value = new String(value.getBytes(encoding), encoding);
                valideData.put(key, value);
            }
        }

        //重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
        if (!AcpService.validate(valideData, encoding)) {
            LogUtil.writeLog("验证签名结果[失败].");
            //验签失败，需解决验签问题

        } else {
            LogUtil.writeLog("验证签名结果[成功].");
            //【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态

            String orderNo =valideData.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取
            String respCode =valideData.get("respCode"); //获取应答码，收到后台通知了respCode的值一般是00，可以不需要根据这个应答码判断。
            String txnAmt = valideData.get("txnAmt");
            String merId = valideData.get("merId");
            OrderDomain orderDomain = orderService.getOrder(orderNo);
            if(orderDomain.getStatus() == OrderStatusEnum.UNPAID.getValue() &&
                    txnAmt.equals(amtRemovePoint(orderDomain.getOrderTotal())) ){
                updateOrderStatus(orderDomain);
            }else{
                return errorResult("验证失败");
            }

        }
        LogUtil.writeLog("BackRcvResponse接收后台通知结束");
        //返回给银联服务器http 200  状态码
        //resp.getWriter().print("ok");

        return successResult("支付成功");
    }

    @RequestMapping(value = "frontRcvResponse",method = RequestMethod.POST)
    public ModelAndView frontRcvResponse(HttpServletRequest req)throws ServletException, IOException {
        LogUtil.writeLog("FrontRcvResponse前台接收报文返回开始");

        String encoding = req.getParameter(SDKConstants.param_encoding);
        LogUtil.writeLog("返回报文中encoding=[" + encoding + "]");
        String pageResult = "";
        if (DemoBase.encoding_UTF8.equalsIgnoreCase(encoding)) {
            pageResult = "payment/utf8_result";
        } else {
            pageResult = "payment/gbk_result";
        }
        Map<String, String> respParam = getAllRequestParam(req);

        // 打印请求报文
        LogUtil.printRequestLog(respParam);

        Map<String, String> valideData = null;
        StringBuffer page = new StringBuffer();
        if (null != respParam && !respParam.isEmpty()) {
            Iterator<Map.Entry<String, String>> it = respParam.entrySet()
                    .iterator();
            valideData = new HashMap<String, String>(respParam.size());
            while (it.hasNext()) {
                Map.Entry<String, String> e = it.next();
                String key = (String) e.getKey();
                String value = (String) e.getValue();
                value = new String(value.getBytes(encoding), encoding);
                page.append("<tr><td width=\"30%\" align=\"right\">" + key
                        + "(" + key + ")</td><td>" + value + "</td></tr>");
                valideData.put(key, value);
            }
        }
        if (!AcpService.validate(valideData, encoding)) {
            page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>失败</td></tr>");
            LogUtil.writeLog("验证签名结果[失败].");
        } else {
            page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>成功</td></tr>");
            LogUtil.writeLog("验证签名结果[成功].");
            System.out.println(valideData.get("orderId")); //其他字段也可用类似方式获取

            String orderNo =valideData.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取
            String respCode =valideData.get("respCode"); //获取应答码，收到后台通知了respCode的值一般是00，可以不需要根据这个应答码判断。
            String txnAmt = valideData.get("txnAmt");
            String merId = valideData.get("merId");
            OrderDomain orderDomain = orderService.getOrder(orderNo);
            if(orderDomain.getStatus() == OrderStatusEnum.UNPAID.getValue() &&
                    txnAmt.equals(amtRemovePoint(orderDomain.getOrderTotal())) ){
                updateOrderStatus(orderDomain);
            }

        }

        ModelAndView mv = new ModelAndView(pageResult);
        mv.addObject("result",page);//req.setAttribute("result", page.toString());
        //req.getRequestDispatcher(pageResult).forward(req, resp);
        LogUtil.writeLog("FrontRcvResponse前台接收报文返回结束");

        return mv;
    }




    /**
     * 获取请求参数中所有的信息
     *
     * @param request
     * @return
     */
    public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                // 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                if (res.get(en) == null || "".equals(res.get(en))) {
                    // System.out.println("======为空的字段名===="+en);
                    res.remove(en);
                }
            }
        }
        return res;
    }


    public void updateOrderStatus(OrderDomain orderDomain){
        orderDomain.setPaidTime(new Date());
        orderDomain.setStatus(OrderStatusEnum.PAID.getValue());
        orderService.update(orderDomain);
    }

    public String amtRemovePoint(Double amt){
        String returnAmt = "0";
        if(amt!=null){
            returnAmt = amt.toString();
            returnAmt = returnAmt.replace(".","");
        }
        return  returnAmt;
    }
}
