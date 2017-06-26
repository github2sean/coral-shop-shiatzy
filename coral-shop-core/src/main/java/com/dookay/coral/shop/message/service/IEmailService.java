package com.dookay.coral.shop.message.service;

import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.shop.message.domain.EmailDomain;

import javax.mail.MessagingException;

/**
 * 邮件的业务层接口
 * @author : luxor
 * @since : 2017年06月13日
 * @version : v0.0.1
 */
public interface IEmailService extends IBaseService<EmailDomain> {
    /**
     * 发送邮件
     * @param toEmail 接受人
     * @param title 邮件主题
     * @param body 邮件内容
     * @return 邮件domain
     */
    void sendEmail(String toEmail, String title, String body) throws MessagingException;
    void sendMultiEmail(String title, String body)throws MessagingException;
    void sendSingleEmail(String toEmail, String title, String body)throws MessagingException;
}
