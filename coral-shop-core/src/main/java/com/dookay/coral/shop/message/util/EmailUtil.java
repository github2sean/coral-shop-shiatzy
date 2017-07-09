package com.dookay.coral.shop.message.util;

import com.dookay.coral.adapter.sendmsg.sendmail.SimpleAliDMSendMail;
import com.dookay.coral.common.web.HttpContext;
import com.dookay.coral.common.web.utils.SpringContextHolder;
import com.dookay.coral.shop.content.domain.MessageTemplateDomain;
import com.dookay.coral.shop.content.query.MessageTemplateQuery;
import com.dookay.coral.shop.content.service.IMessageTemplateService;
import com.dookay.coral.shop.message.enums.MessageTypeEnum;
import com.dookay.coral.shop.order.domain.OrderDomain;
import com.dookay.coral.shop.order.domain.OrderItemDomain;
import com.dookay.coral.shop.order.domain.ReturnRequestDomain;
import com.dookay.coral.shop.order.domain.ReturnRequestItemDomain;
import com.dookay.coral.shop.order.enums.OrderStatusEnum;
import com.dookay.coral.shop.order.service.IOrderService;
import org.apache.commons.codec.digest.DigestUtils;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 磊 on 2017/7/9.
 */
public class EmailUtil {

    private static IMessageTemplateService messageTemplateService = SpringContextHolder.getBean("messageTemplateService");

    private static SimpleAliDMSendMail simpleAliDMSendMail= SpringContextHolder.getBean("simpleAliDMSendMail");

    private static IOrderService orderService= SpringContextHolder.getBean("orderService");

    public static void Register(String userName,Boolean isEn) throws MessagingException {
        //发送邮件通知
        MessageTemplateQuery query = new MessageTemplateQuery();
        query.setType(1);
        query.setCode(MessageTypeEnum.LOGIN_SUCCESS.getValue());
        query.setIsValid(1);
        MessageTemplateDomain messageTemplate = messageTemplateService.getFirst(query);

        //生成模版
        Map<String, Object> map = new HashMap<>();
        map.put("picUrl", FreemarkerUtil.getLogoUrl("static/images/logoSC.png"));
        map.put("title", isEn ? messageTemplate.getEnContent() : messageTemplate.getTitle());
        map.put("name", userName);
        map.put("contentPrefix", !isEn ? messageTemplate.getContent() : messageTemplate.getEnContent());
        //map.put("contentSuffix",!isEn?"从现在起，您可以通过输入您的个人登录信息随时访问您的帐户。作为我们在线精品店的会员，你有幸成为第一个听到我们最新的收藏、特别活动和新闻的人。你可以充分利用一系列的好处和服务如下：在您的在线的时候":"From now on you can access your account at any time by entering your personal login details. As the member of our online boutique, you are privileged to be the first to hear about our latest collections, special events and style news. You could take full advantages of a range of benefits and services as below during your online sh");
        String html = FreemarkerUtil.printString(isEn ? "registerSuccessful_en.ftl" : "registerSuccessful.ftl", map);
        // 发邮件通知


        HashMap<String, String> emailMap = new HashMap<>();
        emailMap.put(simpleAliDMSendMail.SEND_EMAIL, simpleAliDMSendMail.SEND_EMAIL_SINGEL);
        emailMap.put(simpleAliDMSendMail.RECEIVE_EMAIL, userName);
        emailMap.put(simpleAliDMSendMail.TITLE, isEn ? messageTemplate.getEnContent() : messageTemplate.getTitle());
        //String resetPassHref =  basePath+"passport/activeEmail?userName="+userName+"&activeCode="+secretKey;
        //String emailContent = "如果您未申请夏资陈帐号,请删除此邮件，点击下面的链接,激活帐号<br/><a href="+resetPassHref+" target='_BLANK'>点击我重新设置密码</a>" ;
        emailMap.put(simpleAliDMSendMail.CONTENT, html);
        simpleAliDMSendMail.sendEmail(emailMap);
    }

    public static void forgetPwd(String userName,Long date,String secretKey,String newPass,Boolean isEn) throws MessagingException{
        String key = userName+"$"+date+"$"+secretKey;
        String digitalSignature = DigestUtils.md5Hex(key);//数字签名
        HttpServletRequest request= HttpContext.current().getRequest();
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        String resetPassHref =  basePath+"u/account/toSetNewPassword?sid="+digitalSignature+"&userName="+userName;

        String emailTitle = isEn?"SHIATZY CHEN reset password":"夏姿陈 找回密码";
        Map<String,Object> freeMap = new HashMap<>();

        freeMap.put("picUrl",FreemarkerUtil.getLogoUrl("static/images/logoSC.png"));
        freeMap.put("title",emailTitle);
        freeMap.put("name",userName);
        freeMap.put("setUrl",resetPassHref);
        freeMap.put("newPass",newPass);
        String html = FreemarkerUtil.printString(isEn?"resetPassword_en.ftl":"resetPassword.ftl",freeMap);


        HashMap<String,String> emailMap = new HashMap<>();
        emailMap.put(simpleAliDMSendMail.SEND_EMAIL,simpleAliDMSendMail.SEND_EMAIL_SINGEL);
        emailMap.put(simpleAliDMSendMail.RECEIVE_EMAIL,userName);
        emailMap.put(simpleAliDMSendMail.TITLE,emailTitle);
        emailMap.put(simpleAliDMSendMail.CONTENT,html);
        simpleAliDMSendMail.sendEmail(emailMap);
    }

    //下单成功
    public static void sendInformation(Boolean isEN,String email,OrderDomain order,List<OrderItemDomain> orderItemDomainList){
        //发送邮件通知
        MessageTemplateQuery query = new MessageTemplateQuery();
        query.setType(1);
        query.setCode(MessageTypeEnum.CREATE_ORDER.getValue());
        query.setIsValid(1);
        MessageTemplateDomain messageTemplate = messageTemplateService.getFirst(query);
        //emailService.sendSingleEmail(customerDomain.getEmail(),messageTemplate.getTitle(),messageTemplate.getContent());
        //生成模版
        Map<String,Object> freeMap = new HashMap<>();
        freeMap.put("picUrl", FreemarkerUtil.getLogoUrl("static/images/logoSC.png"));
        freeMap.put("title",isEN?messageTemplate.getEnTitle():messageTemplate.getTitle());
        freeMap.put("name",email);
        freeMap.put("status",isEN?"PAID": OrderStatusEnum.PAID.getValue());
        freeMap.put("content",isEN?messageTemplate.getEnContent():messageTemplate.getContent());
        freeMap.put("date",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(order.getOrderTime()));
        freeMap.put("order",order);
        //绑定order
        orderService.withGoodItme(orderItemDomainList);
        freeMap.put("orderItem",orderItemDomainList);
        String html = FreemarkerUtil.printString(isEN?"orderPaid_en.ftl":"orderPaid.ftl",freeMap);

        HashMap<String,String> emailMap = new HashMap<>();
        emailMap.put(simpleAliDMSendMail.SEND_EMAIL,simpleAliDMSendMail.SEND_EMAIL_SINGEL);
        emailMap.put(simpleAliDMSendMail.RECEIVE_EMAIL,email);
        emailMap.put(simpleAliDMSendMail.TITLE,isEN?messageTemplate.getEnTitle():messageTemplate.getTitle());
        emailMap.put(simpleAliDMSendMail.CONTENT,html);
        try {
            simpleAliDMSendMail.sendEmail(emailMap);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void SendGoods(Boolean isEN,String email,OrderDomain order,List<OrderItemDomain> orderItemDomainList){
        //发送邮件通知
        MessageTemplateQuery query = new MessageTemplateQuery();
        query.setType(1);
        query.setCode(MessageTypeEnum.SEND_GOODS.getValue());
        query.setIsValid(1);
        MessageTemplateDomain messageTemplate = messageTemplateService.getFirst(query);
        //emailService.sendSingleEmail(customerDomain.getEmail(),messageTemplate.getTitle(),messageTemplate.getContent());
        //生成模版
        Map<String,Object> freeMap = new HashMap<>();
        freeMap.put("picUrl", FreemarkerUtil.getLogoUrl("static/images/logoSC.png"));
        freeMap.put("title",isEN?messageTemplate.getEnTitle():messageTemplate.getTitle());
        freeMap.put("name",email);
        freeMap.put("status", isEN?"SHIPPED":OrderStatusEnum.SHIPPED.getDescription());
        freeMap.put("content",isEN?messageTemplate.getEnContent():messageTemplate.getContent());
        freeMap.put("date",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(order.getOrderTime()));
        freeMap.put("order",order);
        //绑定order
        orderService.withGoodItme(orderItemDomainList);
        freeMap.put("orderItem",orderItemDomainList);
        String html = FreemarkerUtil.printString(isEN?"orderSend_en.ftl":"orderSend.ftl",freeMap);

        HashMap<String,String> emailMap = new HashMap<>();
        emailMap.put(simpleAliDMSendMail.SEND_EMAIL,simpleAliDMSendMail.SEND_EMAIL_SINGEL);
        emailMap.put(simpleAliDMSendMail.RECEIVE_EMAIL,email);
        emailMap.put(simpleAliDMSendMail.TITLE,isEN?messageTemplate.getEnTitle():messageTemplate.getTitle());
        emailMap.put(simpleAliDMSendMail.CONTENT,html);
        try {
            simpleAliDMSendMail.sendEmail(emailMap);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void applyReturn(Boolean isEN, String email, ReturnRequestDomain returnRequestDomain, List<ReturnRequestItemDomain>requestList,Double totalFee){
        MessageTemplateQuery query = new MessageTemplateQuery();
        query.setType(1);
        query.setCode(MessageTypeEnum.RETURN_REQUEST.getValue());
        query.setIsValid(1);
        MessageTemplateDomain messageTemplate = messageTemplateService.getFirst(query);
        //2.生成模版
        Map<String,Object> freeMap = new HashMap<>();
        freeMap.put("picUrl", FreemarkerUtil.getLogoUrl("static/images/logoSC.png"));
        freeMap.put("title",isEN?messageTemplate.getEnTitle():messageTemplate.getTitle());
        freeMap.put("name",email);
        freeMap.put("status", isEN?"applying":"申请中");
        freeMap.put("content",isEN?messageTemplate.getEnContent():messageTemplate.getContent());
        freeMap.put("date",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(returnRequestDomain.getOrderTime()));
        orderService.returnWithGoodItem(requestList);//会设置图片地址
        freeMap.put("order",returnRequestDomain);
        freeMap.put("orderItem",requestList);
        freeMap.put("totalFee",totalFee);
        freeMap.put("backWay",returnRequestDomain.getReturnShippingMethod()==1?(isEN?"EXPRESS":"快递取件"):(isEN?"TO STORE":"退回门店"));
        freeMap.put("backAddress",returnRequestDomain.getReturnShippingMethod()==1?returnRequestDomain.getShipAddress():(isEN?returnRequestDomain.getStoreDomain().getEnAddress():returnRequestDomain.getStoreDomain().getAddress()));
        String html = FreemarkerUtil.printString(isEN?"returnOrder_en.ftl":"returnOrder.ftl",freeMap);
        //3.设置发送邮件参数
        HashMap<String,String> emailMap = new HashMap<>();
        emailMap.put(simpleAliDMSendMail.SEND_EMAIL,simpleAliDMSendMail.SEND_EMAIL_SINGEL);
        emailMap.put(simpleAliDMSendMail.RECEIVE_EMAIL,email);
        emailMap.put(simpleAliDMSendMail.TITLE,isEN?messageTemplate.getEnTitle():messageTemplate.getTitle());
        emailMap.put(simpleAliDMSendMail.CONTENT,html);
        try {
            simpleAliDMSendMail.sendEmail(emailMap);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
