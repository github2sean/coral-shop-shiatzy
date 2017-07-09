package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.adapter.sendmsg.sendmail.SimpleAliDMSendMail;
import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.utils.RandomUtils;
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
import com.dookay.coral.shop.content.service.IMessageTemplateService;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.domain.GoodsItemDomain;
import com.dookay.coral.shop.goods.domain.SkuDomain;
import com.dookay.coral.shop.goods.service.IGoodsItemService;
import com.dookay.coral.shop.goods.service.IGoodsService;
import com.dookay.coral.shop.goods.service.IPrototypeSpecificationOptionService;
import com.dookay.coral.shop.message.service.ISmsService;
import com.dookay.coral.shop.message.util.EmailUtil;
import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;
import com.dookay.coral.shop.order.enums.ShoppingCartTypeEnum;
import com.dookay.coral.shop.order.query.ShoppingCartItemQuery;
import com.dookay.coral.shop.order.service.IShoppingCartService;
import com.dookay.coral.shop.shipping.service.IShippingCountryService;
import com.dookay.shiatzy.web.mobile.base.MobileBaseController;
import com.dookay.shiatzy.web.mobile.form.AddShoppingCartForm;
import com.dookay.shiatzy.web.mobile.form.ForgetForm;
import com.dookay.shiatzy.web.mobile.form.LoginForm;
import com.dookay.shiatzy.web.mobile.form.RegisterForm;
import com.dookay.shiatzy.web.mobile.taglib.DefaultTags;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
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
    @Autowired
    private IMessageTemplateService messageTemplateService;


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
        mv.addObject("date",new Date());
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
           return errorResult(DefaultTags.translate("账户不存在或者密码错误","Login fail"));
       }
        return successResult(DefaultTags.translate("登录成功","Login success"));
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult register(@ModelAttribute RegisterForm registerForm,HttpServletRequest request) throws MessagingException {
        beanValidator(registerForm);
        String userName = registerForm.getEmail();
        String password = registerForm.getPassword();
        String validCode = registerForm.getValidCode();

        if (!JCaptcha.validateResponse(request, validCode)) {
           return errorResult(DefaultTags.translate("验证码错误","Invalid code"));
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
                    return errorResult(DefaultTags.translate("注册失败","Register fail"));
                }
            }
        }

        AccountDomain accountDomain = accountService.getAccount(userName);
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        String secretKey = UUID.randomUUID().toString();//密钥
        accountDomain.setActiveCode(secretKey);
        accountService.update(accountDomain);
        //生成模版
        Boolean isEn = !"1".equals(CookieUtil.getCookieValueByKey(request,"shippingCountry"));
        EmailUtil.Register(userName, isEn);
        UserContext.signIn(accountDomain);
        return successResult(DefaultTags.translate("注册成功","Register success"));
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
    public JsonResult forgetPassword(@ModelAttribute ForgetForm forgetForm,HttpServletRequest request){
        beanValidator(forgetForm);
        String userName = forgetForm.getUserName();
        String validCode = forgetForm.getValidCode();

        AccountDomain users = accountService.getAccount(userName);
        if(users == null){//用户名不存在
            return errorResult(DefaultTags.translate("用户名不存在!","username not exist"));
        }
        try{
            String secretKey= UUID.randomUUID().toString();//密钥
            Timestamp outDate = new Timestamp(System.currentTimeMillis()+30*60*1000);//30分钟后过期
            long date = outDate.getTime()/1000*1000;//忽略毫秒数
            System.out.println("date:"+date);
            users.setValidateCode(secretKey);
            users.setRegisterDate(date);
            accountService.update(users);//保存到数据库
            //生产新密码
            String newPass = RandomUtils.randomNumbers(6);
            accountService.resetPassword(users,newPass);
            //生成模版
            Boolean isEn = !"1".equals(CookieUtil.getCookieValueByKey(request,"shippingCountry"));
            EmailUtil.forgetPwd(users.getUserName(),date,secretKey,newPass,isEn);

            //三方jar包未选择
            //SendMail.getInstatnce().sendHtmlMail(emailTitle,emailContent,userName);
        }catch (Exception e){
            e.printStackTrace();
            return errorResult(DefaultTags.translate("邮箱不存在？未知错误,联系管理员吧。","Wrong email"));
        }
        return successResult(DefaultTags.translate("操作成功,已经发送找回密码链接到您邮箱，登录后请尽快修改次密码。","Send success"));
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
