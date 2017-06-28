package com.dookay.coral.shop.message.service;

import com.dookay.coral.shop.base.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/6/24
 */
public class IEmailServiceTest extends BaseTest {
    @Autowired
    private IEmailService emailService;
    @Test
    public void sendEmail() throws Exception {
        emailService.sendEmail("420782058@qq.com","夏姿陈网站测试邮件","只是一个测试邮件，请不要回复");
    }

}