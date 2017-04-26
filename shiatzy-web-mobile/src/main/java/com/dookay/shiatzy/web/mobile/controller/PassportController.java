package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.host.user.context.UserContext;
import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.host.user.service.IAccountService;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.shiatzy.web.mobile.base.MobileBaseController;
import com.dookay.shiatzy.web.mobile.form.LoginForm;
import com.dookay.shiatzy.web.mobile.form.RegisterForm;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by admin on 2017/4/25.
 */

@Controller
@RequestMapping("passport/")
public class PassportController extends MobileBaseController{

    private Logger logger = Logger.getLogger(PassportController.class);
    @Autowired
    private IAccountService accountService;
    @Autowired
    private ICustomerService customerService;

    @RequestMapping(value = "toRegister", method = RequestMethod.GET)
    public String index(){
        return "/passport/register";
    }

    @RequestMapping(value = "toLogin", method = RequestMethod.GET)
    public String toLogin(){
        return "/passport/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult login(@ModelAttribute LoginForm loginForm){
        beanValidator(loginForm);
        String userName = loginForm.getUserName();
        String password = loginForm.getPassword();
        Boolean checkAccount = accountService.validateAccount(userName,password);
       if(checkAccount) {
           AccountDomain accountDomain = accountService.getAccount(userName);
           UserContext.signIn(accountDomain);
       }
        return successResult("登录成功");
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult register(@ModelAttribute RegisterForm registerForm){
        beanValidator(registerForm);
        String userName = registerForm.getEmail();
        String password = registerForm.getPassword();
        String validCode = registerForm.getValidCode();

        if(StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)){
           Boolean isExist =  accountService.isExist(userName);
            if(!isExist){
                AccountDomain accountDomain = new AccountDomain();
                accountDomain.setUserName(userName);
                accountDomain.setPassword(password);
                accountDomain.setEmail(userName);
                accountDomain.setCreateTime(new Date());
                CustomerDomain retCustomer = customerService.register(null,accountDomain);
                if(retCustomer==null) {
                    return successResult("注册失败");
                }
            }
        }

        return successResult("注册成功");
    }
}