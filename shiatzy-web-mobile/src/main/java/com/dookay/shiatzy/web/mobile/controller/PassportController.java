package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.common.web.HttpContext;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.common.web.jcaptcha.JCaptcha;
import com.dookay.coral.host.user.context.UserContext;
import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.host.user.service.IAccountService;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.query.CustomerAddressQuery;
import com.dookay.coral.shop.customer.service.ICustomerAddressService;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.order.enums.ShoppingCartTypeEnum;
import com.dookay.coral.shop.order.query.ShoppingCartItemQuery;
import com.dookay.coral.shop.order.service.IShoppingCartService;
import com.dookay.shiatzy.web.mobile.base.MobileBaseController;
import com.dookay.shiatzy.web.mobile.form.ForgetForm;
import com.dookay.shiatzy.web.mobile.form.LoginForm;
import com.dookay.shiatzy.web.mobile.form.RegisterForm;
import com.sun.activation.registries.MailcapParseException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.util.StringUtil;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    @Autowired
    private IShoppingCartService shoppingCartService;

    public static String CRAT_NUM = "cartNumber";

    @RequestMapping(value = "toRegister", method = RequestMethod.GET)
    public String index(){
        return "passport/register";
    }

    @RequestMapping(value = "toLogin", method = RequestMethod.GET)
    public String toLogin(){

        //如果已经登陆直接跳转到个人信息页面
        if(!UserContext.isGuest()){
            return "redirect:/u/account/index";
        }

        return "passport/login";
    }

    @RequestMapping(value = "toForget", method = RequestMethod.GET)
    public String toForget(){
        return "passport/forgetPassword";
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
           CustomerDomain customerDomain = customerService.getAccount(accountDomain.getId());
           ShoppingCartItemQuery query = new ShoppingCartItemQuery();
           query.setCustomerId(customerDomain.getId());
           query.setShoppingCartType(ShoppingCartTypeEnum.SHOPPING_CART.getValue());
           HttpServletRequest request = HttpContext.current().getRequest();
           HttpSession session = request.getSession();
           int cartNum = shoppingCartService.count(query);
           session.setAttribute(CRAT_NUM,cartNum);
       }else{
           return errorResult("账户不存在或者密码错误");
       }

        return successResult("登录成功");
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult register(@ModelAttribute RegisterForm registerForm,HttpServletRequest request){
        beanValidator(registerForm);
        String userName = registerForm.getEmail();
        String password = registerForm.getPassword();
        String validCode = registerForm.getValidCode();

        if (!JCaptcha.validateResponse(request, validCode)) {
           return errorResult("验证码错误");
        }

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
                    return errorResult("注册失败");
                }
            }
        }

        AccountDomain accountDomain = accountService.getAccount(userName);
        UserContext.signIn(accountDomain);
        CustomerDomain customerDomain = customerService.getAccount(accountDomain.getId());
        ShoppingCartItemQuery query = new ShoppingCartItemQuery();
        query.setCustomerId(customerDomain.getId());
        query.setShoppingCartType(ShoppingCartTypeEnum.SHOPPING_CART.getValue());
        HttpSession session = request.getSession();
        int cartNum = shoppingCartService.count(query);
        session.setAttribute(CRAT_NUM,cartNum);
        return successResult("注册成功");
    }

    @RequestMapping(value = "sendPassword", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult sendPassword(@ModelAttribute ForgetForm forgetForm){
        beanValidator(forgetForm);
        String userName = forgetForm.getUserName();
        String validCode = forgetForm.getValidCode();




        return successResult("发送成功");
    }

    @RequestMapping(value = "/forgetPassword")
    @ResponseBody
    public Map forgetPassword(@ModelAttribute ForgetForm forgetForm,HttpServletRequest request){
        beanValidator(forgetForm);
        String userName = forgetForm.getUserName();
        String validCode = forgetForm.getValidCode();

        AccountDomain users = accountService.getAccount(userName);
        Map map = new HashMap<String ,String >();
        String msg = "";
        if(users == null){//用户名不存在
            msg = "用户名不存在!";
            map.put("msg",msg);
            return map;
        }
        try{
            String secretKey= UUID.randomUUID().toString();//密钥
            Timestamp outDate = new Timestamp(System.currentTimeMillis()+30*60*1000);//30分钟后过期
            long date = outDate.getTime()/1000*1000;//忽略毫秒数
            users.setValidateCode(secretKey);
            users.setRegisterDate(outDate);
            accountService.update(users);//保存到数据库
            String key = users.getUserName()+"$"+date+"$"+secretKey;
            String digitalSignature = DigestUtils.md5Hex(key);//数字签名

            String emailTitle = "夏资陈密码找回";
            String path = request.getContextPath();
            String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
            String resetPassHref =  basePath+"user/reset_password?sid="+digitalSignature+"&userName="+users.getUserName();
            String emailContent = "请勿回复本邮件.点击下面的链接,重设密码<br/><a href="+resetPassHref +" target='_BLANK'>点击我重新设置密码</a>" +
                    "<br/>tips:本邮件超过30分钟,链接将会失效，需要重新申请'找回密码'"+key+"\t"+digitalSignature;
            System.out.print(resetPassHref);

            //三方jar包未选择
            //SendMail.getInstatnce().sendHtmlMail(emailTitle,emailContent,userName);
            msg = "操作成功,已经发送找回密码链接到您邮箱。请在30分钟内重置密码";

        }catch (Exception e){
            e.printStackTrace();
            msg="邮箱不存在？未知错误,联系管理员吧。";
        }
        map.put("msg",msg);
        return map;
    }


    @RequestMapping(value = "/resetPassword",method = RequestMethod.GET)
    public ModelAndView resetPassword(String sid,String userName){
        ModelAndView model = new ModelAndView("error");
        String msg = "";
        if(sid.equals("") || userName.equals("")){
            msg="链接不完整,请重新生成";
            model.addObject("msg",msg) ;
            return model;
        }
        AccountDomain users = accountService.getAccount(userName);
        if(users == null){
            msg = "链接错误,无法找到匹配用户,请重新申请找回密码.";
            model.addObject("msg",msg) ;
            return model;
        }
        Timestamp outDate = users.getRegisterDate();
        if(outDate.getTime() <= System.currentTimeMillis()){         //表示已经过期
            msg = "链接已经过期,请重新申请找回密码.";
            model.addObject("msg",msg);
            return model;
        }
        String key = users.getUserName()+"$"+outDate.getTime()/1000*1000+"$"+users.getValidateCode();          //数字签名
        String digitalSignature = DigestUtils.md5Hex(key);
        System.out.println(key+"\t"+digitalSignature);
        if(!digitalSignature.equals(sid)) {
            msg = "链接不正确,是否已经过期了?重新申请吧";
            model.addObject("msg",msg) ;
            return model;
        }
        model.setViewName("redirect:u/account/resetPassword");  //返回到修改密码的界面
        model.addObject("userName",userName);
        return model;
    }

    @RequestMapping(value = "loginOut", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult loginOut(){
        try{
            AccountDomain accountDomain = UserContext.current().getAccountDomain();
            if(accountDomain!=null){
                UserContext.signOut();
            }
        }catch (Exception e){
            e.printStackTrace();
            return successResult("退出失败",0);
        }
        return successResult("退出成功",1);
    }
}
