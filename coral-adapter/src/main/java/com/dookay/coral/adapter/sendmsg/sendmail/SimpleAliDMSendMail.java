package com.dookay.coral.adapter.sendmsg.sendmail;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Properties;
@Component
public class SimpleAliDMSendMail {
    private static final String ALIDM_SMTP_HOST = "smtpdm.aliyun.com";
    private static final int ALIDM_SMTP_PORT = 25;


    public static final String SEND_EMAIL_SINGEL = "customercare@ec.shiatzychen.com";//customercare@ec.shiatzychen.com
    public static final String SEND_EMAIL_MULTI = "publish@ec.shiatzychen.com";
    public static final String SEND_EMAIL = "sendEmail";
    public static final String RECEIVE_EMAIL = "receiveEmail";
    public static final String CONTENT = "content";
    public static final String TITLE = "title";

    public static void sendEmail(HashMap<String,String> configMap) throws MessagingException {
        // 配置发送邮件的环境属性
        final Properties props = new Properties();
        // 表示SMTP发送邮件，需要进行身份验证
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", ALIDM_SMTP_HOST);
        props.put("mail.smtp.port", ALIDM_SMTP_PORT);
        //props.put("mail.smtp.","SHOP.SHIATZY.COM");
        // 如果使用ssl，则去掉使用25端口的配置，进行如下配置,
        // props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        // props.put("mail.smtp.socketFactory.port", "465");
        // props.put("mail.smtp.port", "465");

        // 发件人的账号
        props.put("mail.user", configMap.get(SEND_EMAIL));
        // 访问SMTP服务时需要提供的密码
        props.put("mail.password", "ShiatzycheN2017");

        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
        message.setFrom(form);
        // 设置收件人
        InternetAddress to = new InternetAddress(configMap.get(RECEIVE_EMAIL));
        message.setRecipient(MimeMessage.RecipientType.TO, to);
        // 设置邮件标题
        message.setSubject("夏资陈 "+configMap.get(TITLE),"UTF-8");
        // 设置邮件的内容体
        message.setContent(configMap.get(CONTENT), "text/html;charset=UTF-8");
        // 发送邮件
        Transport.send(message);
    }
}
