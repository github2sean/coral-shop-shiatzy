package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.adapter.sendmsg.sendmail.SimpleAliDMSendMail;
import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.utils.HttpClientUtil;
import com.dookay.coral.common.web.CookieUtil;
import com.dookay.coral.common.web.HttpContext;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.common.web.jcaptcha.JCaptcha;
import com.dookay.coral.host.user.context.UserContext;
import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.host.user.service.IAccountService;
import com.dookay.coral.shop.content.domain.ContentCategoryDomain;
import com.dookay.coral.shop.content.query.ContentCategoryQuery;
import com.dookay.coral.shop.content.service.IContentCategoryService;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.query.CustomerAddressQuery;
import com.dookay.coral.shop.customer.service.ICustomerAddressService;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.domain.GoodsItemDomain;
import com.dookay.coral.shop.goods.domain.SkuDomain;
import com.dookay.coral.shop.goods.service.IGoodsItemService;
import com.dookay.coral.shop.goods.service.IGoodsService;
import com.dookay.coral.shop.goods.service.IPrototypeSpecificationOptionService;
import com.dookay.coral.shop.message.service.ISmsService;
import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;
import com.dookay.coral.shop.order.enums.ShoppingCartTypeEnum;
import com.dookay.coral.shop.order.query.ShoppingCartItemQuery;
import com.dookay.coral.shop.order.service.IShoppingCartService;
import com.dookay.coral.shop.shipping.domain.ShippingCountryDomain;
import com.dookay.coral.shop.shipping.query.ShippingCountryQuery;
import com.dookay.coral.shop.shipping.service.IShippingCountryService;
import com.dookay.shiatzy.web.mobile.base.MobileBaseController;
import com.dookay.shiatzy.web.mobile.form.AddShoppingCartForm;
import com.dookay.shiatzy.web.mobile.form.ForgetForm;
import com.dookay.shiatzy.web.mobile.form.LoginForm;
import com.dookay.shiatzy.web.mobile.form.RegisterForm;
import com.dookay.shiatzy.web.mobile.util.FreemarkerUtil;
import com.dookay.shiatzy.web.mobile.util.HistoryUtil;
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

import javax.mail.MessagingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IGoodsItemService goodsItemService;
    @Autowired
    private IPrototypeSpecificationOptionService prototypeSpecificationOptionService;
    @Autowired
    private ISmsService smsService;
    @Autowired
    private IContentCategoryService contentCategoryService;
    @Autowired
    private SimpleAliDMSendMail simpleAliDMSendMail;
    @Autowired
    private IShippingCountryService shippingCountryService;


    public static final String CRAT_NUM = "cartNumber";

    //Session 购物车
    private static final String SESSION_CART ="session_cart";
    private static final String IS_GUEST ="isGuest";
    private final static String LANGUAGE_HISTORY = "language_history";
    private final static int MAX_COOKIE_AGE = 24*60*60;


    @RequestMapping(value = "toRegister", method = RequestMethod.GET)
    public String index(){
        return "passport/register";
    }

    @RequestMapping(value = "toLogin", method = RequestMethod.GET)
    public ModelAndView toLogin(){

        ModelAndView mv = new ModelAndView("passport/login");
        //如果已经登陆直接跳转到个人信息页面
        if(!UserContext.isGuest()){
            return new ModelAndView("redirect:/u/account/index");
        }
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();

        //查询页尾内容
        ContentCategoryQuery querys=new ContentCategoryQuery();
        querys.setLevel(1);
        List<ContentCategoryDomain> domainList=contentCategoryService.getList(querys);
        session.setAttribute("domainList",domainList);

        //判断session购物中是否有商品
        List<AddShoppingCartForm> listCart  = (List<AddShoppingCartForm>)session.getAttribute(SESSION_CART);
        List<SkuDomain> skuDomainList = new ArrayList<>();
        if(listCart!=null&&listCart.size()>0){
            for(AddShoppingCartForm form:listCart){
                if(form.getType()==2){
                    Long itemId = form.getItemId();
                    Long sizeId = form.getSizeId();
                    System.out.println("tologinForm:"+JsonUtils.toJSONString(form));
                    SkuDomain skuDomain = shoppingCartService.getSkubySizeAndItem(itemId,sizeId);
                    if(skuDomain == null) {
                        continue;
                    }
                    skuDomain.setItemId(itemId);
                    Integer num = form.getNum();
                    //准备商品数据
                    GoodsItemDomain goodsItemDomain =  goodsItemService.get(itemId);
                    GoodsDomain goodsDomain = goodsService.get(goodsItemDomain.getGoodsId());//得到商品
                    skuDomain.setGoodsItem(goodsItemDomain);
                    skuDomain.setGoods(goodsDomain);
                    skuDomain.setSizeDomain(prototypeSpecificationOptionService.get(sizeId));
                    skuDomainList.add(skuDomain);
                }
            }
        }
        mv.addObject("skuList",skuDomainList);
        System.out.println("skuList:"+ JsonUtils.toJSONString(skuDomainList));
        return mv;
    }

    @RequestMapping(value = "toForget", method = RequestMethod.GET)
    public String toForget(){
        return "passport/forgetPassword";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult login(@ModelAttribute LoginForm loginForm) throws MessagingException {
        beanValidator(loginForm);
        String userName = loginForm.getUserName();
        String password = loginForm.getPassword();
        Boolean checkAccount = accountService.validateAccount(userName,password);
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        CustomerDomain customerDomain = null;
       if(checkAccount) {
           AccountDomain accountDomain = accountService.getAccount(userName);
           UserContext.signIn(accountDomain);
           customerDomain = customerService.getAccount(accountDomain.getId());
           ShoppingCartItemQuery query = new ShoppingCartItemQuery();
           query.setCustomerId(customerDomain.getId());
           query.setShoppingCartType(ShoppingCartTypeEnum.SHOPPING_CART.getValue());
           int cartNum = shoppingCartService.count(query);
           session.setAttribute(CRAT_NUM,cartNum);
           session.setAttribute(IS_GUEST,"onLine");
           //判断session购物中是否有商品
           List<AddShoppingCartForm> listCart  = (List<AddShoppingCartForm>)session.getAttribute(SESSION_CART);
           if(listCart!=null&&listCart.size()>0){ //全部加入到真实购物车中
               for(AddShoppingCartForm form:listCart){
                   Long itemId = form.getItemId();
                   Long sizeId = form.getSizeId();
                   SkuDomain skuDomain = shoppingCartService.getSkubySizeAndItem(itemId,sizeId);
                   if(skuDomain == null) {
                       System.out.println("loginForm:"+JsonUtils.toJSONString(form));
                       continue;
                   }
                   skuDomain.setItemId(itemId);
                   Integer num = form.getNum();
                   if(form.getType()==ShoppingCartTypeEnum.WISH_LIST.getValue()){
                       ShoppingCartItemDomain queryShoppingCart = shoppingCartService.isExistInWish(customerDomain, skuDomain);
                       if(queryShoppingCart!=null){
                           continue;
                       }
                   }
                   System.out.println("customerDomain"+customerDomain+"\nskuDomain"+skuDomain+"\ntype:"+form.getType()+"\nnum:"+num);
                   shoppingCartService.addToCart(customerDomain, skuDomain, form.getType(),num);
               }
               //清空session中商品
               session.setAttribute(SESSION_CART,null);
           }
       }else{
           return errorResult("账户不存在或者密码错误");
       }
        return successResult("登录成功");
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult register(@ModelAttribute RegisterForm registerForm,HttpServletRequest request) throws MessagingException {
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
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        //生成模版
        Map<String,Object> map = new HashMap<>();
        map.put("picUrl",FreemarkerUtil.getLogoUrl("static/images/logoSC.png"));
        map.put("title","夏资陈");
        map.put("name",userName);
        map.put("contentPrefix","We are pleased you’ve opened an account at");
        map.put("contentSuffix","From now on you can access your account at any time by entering your personal login details. As the member of our online boutique, you are privileged to be the first to hear about our latest collections, special events and style news. You could take full advantages of a range of benefits and services as below during your online sh");
        String html = FreemarkerUtil.printString("registerSuccessful.ftl",map);
        // 发邮件通知 TODO: 2017/6/15
        String secretKey = UUID.randomUUID().toString();//密钥
        accountDomain.setActiveCode(secretKey);
        accountService.update(accountDomain);

        HashMap<String,String> emailMap = new HashMap<>();
        emailMap.put(simpleAliDMSendMail.SEND_EMAIL,simpleAliDMSendMail.SEND_EMAIL_SINGEL);
        emailMap.put(simpleAliDMSendMail.RECEIVE_EMAIL,userName);
        emailMap.put(simpleAliDMSendMail.TITLE,"夏资陈 注册成功");
        //String resetPassHref =  basePath+"passport/activeEmail?userName="+userName+"&activeCode="+secretKey;
        //String emailContent = "如果您未申请夏资陈帐号,请删除此邮件，点击下面的链接,激活帐号<br/><a href="+resetPassHref+" target='_BLANK'>点击我重新设置密码</a>" ;
        emailMap.put(simpleAliDMSendMail.CONTENT,html);
        simpleAliDMSendMail.sendEmail(emailMap);
        UserContext.signIn(accountDomain);
        return successResult("注册成功");
    }

    @RequestMapping(value = "activeEmail", method = RequestMethod.GET)
    public ModelAndView activeEmail(String userName,String activeCode){
        beanValidator(userName);
       AccountDomain accountDomain =  accountService.getAccountByEmail(userName);
        if(accountDomain!=null){
            String code = accountDomain.getActiveCode();
            if(StringUtils.isNotBlank(code)&&code.equals(activeCode)){
                accountDomain.setIsValid(1);
            }else {
                throw new ServiceException("激活码校验出错");
            }
        }
        accountService.update(accountDomain);
        ModelAndView mv = new ModelAndView("passport/login");
        return mv;
    }


    @RequestMapping(value = "sendPassword", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult sendPassword(@ModelAttribute ForgetForm forgetForm){
        beanValidator(forgetForm);
        String userName = forgetForm.getUserName();
        String validCode = forgetForm.getValidCode();
        return successResult("发送成功");
    }

    @RequestMapping(value = "forgetPassword")
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
            System.out.println("date:"+date);
            users.setValidateCode(secretKey);
            users.setRegisterDate(date);
            accountService.update(users);//保存到数据库
            String key = users.getUserName()+"$"+date+"$"+secretKey;
            String digitalSignature = DigestUtils.md5Hex(key);//数字签名
            String emailTitle = "夏资陈找回密码";
            String path = request.getContextPath();
            String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
            String resetPassHref =  basePath+"u/account/toSetNewPassword?sid="+digitalSignature+"&userName="+users.getUserName();

            //生成模版
            Map<String,Object> freeMap = new HashMap<>();
            freeMap.put("picUrl",FreemarkerUtil.getLogoUrl("static/images/logoSC.png"));
            freeMap.put("title","夏资陈 找回密码");
            freeMap.put("name",userName);
            freeMap.put("setUrl",resetPassHref);
            String html = FreemarkerUtil.printString("resetPassword.ftl",freeMap);

            String emailContent = "请勿回复本邮件.点击下面的链接,重设密码<br/><a href="+resetPassHref +" target='_BLANK'>点击我重新设置密码</a>" +
                    "<br/>tips:本邮件超过30分钟,链接将会失效"+key+"\tmd5:"+digitalSignature;
            HashMap<String,String> emailMap = new HashMap<>();
            emailMap.put(simpleAliDMSendMail.SEND_EMAIL,simpleAliDMSendMail.SEND_EMAIL_SINGEL);
            emailMap.put(simpleAliDMSendMail.RECEIVE_EMAIL,userName);
            emailMap.put(simpleAliDMSendMail.TITLE,emailTitle);
            emailMap.put(simpleAliDMSendMail.CONTENT,html);
            simpleAliDMSendMail.sendEmail(emailMap);

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
        Long outDate = users.getRegisterDate();
        if( new Timestamp(outDate).getTime()<= System.currentTimeMillis()){         //表示已经过期
            msg = "链接已经过期,请重新申请找回密码.";
            model.addObject("msg",msg);
            return model;
        }
        String key = users.getUserName()+"$"+outDate+"$"+users.getValidateCode();          //数字签名
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
