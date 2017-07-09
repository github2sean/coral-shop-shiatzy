package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.adapter.sendmsg.sendmail.SimpleAliDMSendMail;
import com.dookay.coral.shop.content.domain.MessageTemplateDomain;
import com.dookay.coral.shop.content.query.MessageTemplateQuery;
import com.dookay.coral.shop.content.service.IMessageTemplateService;
import com.dookay.coral.shop.message.enums.MessageTypeEnum;
import com.dookay.coral.shop.message.util.FreemarkerUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 磊 on 2017/7/9.
 */
public class PassportControllerTest {

    Boolean isEn = true;
    String userName = "425880812@qq.com";


    @Autowired
    private IMessageTemplateService messageTemplateService;

    @Autowired
    private SimpleAliDMSendMail simpleAliDMSendMail;

    @Test
    public void register() throws Exception {

        //发送邮件通知
        MessageTemplateQuery query = new MessageTemplateQuery();
        query.setType(1);
        query.setCode(MessageTypeEnum.CREATE_ORDER.getValue());
        query.setIsValid(1);
        MessageTemplateDomain messageTemplate = messageTemplateService.getFirst(query);
        //生成模版
        Map<String, Object> map = new HashMap<>();
        map.put("picUrl", FreemarkerUtil.getLogoUrl("static/images/logoSC.png"));
        map.put("title", isEn ? messageTemplate.getEnContent() : messageTemplate.getTitle());
        map.put("name", "test");
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

}