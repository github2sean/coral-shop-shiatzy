package com.dookay.shiatzy.web.mobile.controller;

import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.web.HttpContext;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.host.user.context.UserContext;
import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.host.user.query.AccountQuery;
import com.dookay.coral.host.user.service.IAccountService;
import com.dookay.coral.shop.customer.domain.CustomerAddressDomain;
import com.dookay.coral.shop.customer.domain.CustomerDomain;

import com.dookay.coral.shop.customer.service.ICustomerAddressService;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.message.enums.MessageTypeEnum;
import com.dookay.coral.shop.message.service.ISmsService;
import com.dookay.coral.shop.shipping.domain.ShippingCountryDomain;
import com.dookay.coral.shop.shipping.query.ShippingCountryQuery;
import com.dookay.coral.shop.shipping.service.IShippingCountryService;
import com.dookay.coral.shop.temp.domain.TempMemberDomain;
import com.dookay.coral.shop.temp.query.TempMemberQuery;
import com.dookay.coral.shop.temp.service.ITempMemberService;
import com.dookay.shiatzy.web.mobile.base.MobileBaseController;
import com.dookay.shiatzy.web.mobile.form.UpdateAccountForm;
import com.dookay.shiatzy.web.mobile.form.UpdateEmailForm;
import com.dookay.shiatzy.web.mobile.form.UpdatePasswordForm;
import com.dookay.shiatzy.web.mobile.util.I18NReverse;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.mail.search.SearchException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/4/25
 */
@Controller
@RequestMapping("u/account")
public class AccountController extends MobileBaseController {

    private Logger logger = Logger.getLogger(AccountController.class);
    @Autowired
    private IAccountService accountService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ICustomerAddressService customerAddressService;
    @Autowired
    private IShippingCountryService shippingCountryService;
    @Autowired
    private ITempMemberService tempMemberService;
    @Autowired
    private ISmsService smsService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index(){
     // System.out.print(UserContext.current().getAccountDomain().getEmail());
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        AccountDomain accountDomain = UserContext.current().getAccountDomain();
        if(accountDomain==null){
            return new ModelAndView("redirect:/passport/toLogin");
        }
        CustomerDomain customerDomain = customerService.getAccount(accountDomain.getId());
        ModelAndView mv = new ModelAndView("user/account/index");
        mv.addObject("accountDomain",accountDomain);
        session.setAttribute("customerDomain",customerDomain);
        return mv;
    }

    @RequestMapping(value = "details", method = RequestMethod.GET)
    public ModelAndView details(){

        AccountDomain accountDomain = UserContext.current().getAccountDomain();
        CustomerDomain customerDomain = customerService.getAccount(accountDomain.getId());
        CustomerAddressDomain customerAddressDomain = customerAddressService.getAccount(customerDomain.getId());
        if(customerDomain.getIsArtClubMember()!=null&&customerDomain.getIsArtClubMember()==1) {
            TempMemberQuery query = new TempMemberQuery();
            String phone = customerDomain.getPhone();
            query.setMobile(StringUtils.isNotBlank(phone)&&phone.length()>2?phone.substring(2,phone.length()):"");
            List<String> cardType = new ArrayList<>();
            cardType.add("CN-A");
            cardType.add("CN-B");
            cardType.add("CN-C");
            cardType.add("CN-D");
            query.setCardType(cardType);
            customerDomain.setTempMemberDomain(tempMemberService.getFirst(query));
        }
        ModelAndView mv = new ModelAndView("user/account/details");
        mv.addObject("accountDomain",accountDomain);
        mv.addObject("customerDomain",customerDomain);
        mv.addObject("customerAddressDomain",customerAddressDomain);
        return mv;
    }

    @RequestMapping(value = "toUpdate", method = RequestMethod.GET)
    public ModelAndView toUpdate(){
        AccountDomain accountDomain = UserContext.current().getAccountDomain();
        CustomerDomain customerDomain = customerService.getAccount(accountDomain.getId());
        CustomerAddressDomain customerAddressDomain = customerAddressService.getAccount(customerDomain.getId());
        String phone = customerDomain.getPhone();
        if(StringUtils.isNotBlank(phone)){
            customerDomain.setPhone(phone.substring(2,phone.length()));
        }
        ModelAndView mv = new ModelAndView("user/account/update");
        mv.addObject("accountDomain",accountDomain);
        mv.addObject("customerDomain",customerDomain);
        mv.addObject("customerAddressDomain",customerAddressDomain);

        //查询出配送国家
        List<ShippingCountryDomain> shippingCountryDomainList = shippingCountryService.getList(new ShippingCountryQuery());
        mv.addObject("countryList",shippingCountryDomainList);
        return mv;
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult update(@ModelAttribute UpdateAccountForm updateAccountForm){
        beanValidator(updateAccountForm);
        CustomerDomain getCustomer = updateAccountForm.getCustomerDomain();
        CustomerAddressDomain getCustomeAddress = updateAccountForm.getCustomerAddressDomain();
        AccountDomain getAccount =  updateAccountForm.getAccountDomain();

        String title = getCustomeAddress.getTitle();
        String firstName = getCustomer.getFirstName();
        String lastName = getCustomer.getLastName();
        //生日...
        String phone = getCustomer.getPhone();
        if (phone.contains(",")){
            phone = phone.replaceAll("\\,","");
        }
        System.out.println("up-phone:"+phone);
        Long accountId = UserContext.current().getAccountDomain().getId();
        AccountDomain updateAccount = accountService.get(accountId);
        CustomerDomain oldCustomer = customerService.getAccount(updateAccount.getId());
        //第一次填写手机号发送短信通知
        if(StringUtils.isNotBlank(phone)&& oldCustomer!=null && StringUtils.isBlank(oldCustomer.getPhone())){
            smsService.sendToSms(phone, MessageTypeEnum.LOGIN_SUCCESS.getValue());
        }

        Long countryId = getCustomeAddress.getCountryId();

        String address = getCustomeAddress.getAddress();


        updateAccount.setCellphone(phone);
        accountService.update(updateAccount);

        CustomerDomain updateCustomer = customerService.getAccount(accountId);
        updateCustomer.setFirstName(firstName);
        updateCustomer.setLastName(lastName);
        updateCustomer.setPhone(phone);
        updateCustomer.setBirthday(getCustomer.getBirthday());

        Long addressId = updateAccountForm.getAddressId();
        CustomerAddressDomain updaCustomerAddress = customerAddressService.get(addressId);
        updaCustomerAddress.setTitle(title);
        updaCustomerAddress.setCountryId(countryId);
        updaCustomerAddress.setProvince(getCustomeAddress.getProvince());
        updaCustomerAddress.setCity(getCustomeAddress.getCity());
        updaCustomerAddress.setAddress(address);
        updaCustomerAddress.setLastName(lastName);
        updaCustomerAddress.setFirstName(firstName);
        updaCustomerAddress.setPhone(phone);
        updaCustomerAddress.setPostalCode(getCustomeAddress.getPostalCode());

        System.out.print("updaCustomerAddress:"+ JsonUtils.toJSONString(updaCustomerAddress) +"\n updateCustomer:"+JsonUtils.toJSONString(updateCustomer)+"\n updateAccount:"+updateAccount);

        Boolean isSuccess = customerService.updateCustomer(updateAccount,updateCustomer,updaCustomerAddress);
        if(!isSuccess){
            return successResult(getI18N().getUpdateFailed());
        }

        return successResult(getI18N().getUpdateSuccess());
    }

    @RequestMapping(value = "toUpdateEmail", method = RequestMethod.GET)
    public ModelAndView toUpdateEmail(){
        AccountDomain accountDomain = UserContext.current().getAccountDomain();
        ModelAndView mv = new ModelAndView("user/account/updateEmail");
        mv.addObject("accountDomain",accountDomain);
        return mv;
    }

    @RequestMapping(value = "toUpdatePassword", method = RequestMethod.GET)
    public ModelAndView toUpdatePassword(){
        AccountDomain accountDomain = UserContext.current().getAccountDomain();
        ModelAndView mv = new ModelAndView("user/account/updatePassword");
        mv.addObject("accountDomain",accountDomain);
        return mv;
    }

    @RequestMapping(value = "toSetNewPassword", method = RequestMethod.GET)
    public ModelAndView toSetNewPassword(HttpServletRequest request){
        String userName = request.getParameter("userName");
        String sid = request.getParameter("sid");
        ModelAndView mv = new ModelAndView("user/account/setNewPassword");
        mv.addObject("userName",userName);
        mv.addObject("sid",sid);
        return mv;
    }

    @RequestMapping(value = "updateEmail", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateEmail(@ModelAttribute UpdateEmailForm updateEmailForm){
        beanValidator(updateEmailForm);
        AccountDomain accountDomain = UserContext.current().getAccountDomain();
        CustomerDomain customerDomain = customerService.getAccount(accountDomain.getId());
        String email = updateEmailForm.getEmail();
        String password = updateEmailForm.getPassword();

        Boolean checkAccount = accountService.validateAccount(accountDomain.getUserName(),password);
        if(checkAccount){
            accountDomain.setEmail(email);
            accountDomain.setUserName(email);
            customerDomain.setEmail(email);
            if(accountService.getAccountByEmail(email)!=null){
                return errorResult(getI18N().getEmailIsExsit());
            }
            accountService.update(accountDomain);
            customerService.update(customerDomain);
            return successResult(getI18N().getUpdateSuccess());
        }else {
            return errorResult(getI18N().getPasswordAndUserNameErro());
        }
    }

    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updatePassword(@ModelAttribute UpdatePasswordForm updatePasswordForm){
        AccountDomain accountDomain = UserContext.current().getAccountDomain();
        if(accountService.validateAccount(accountDomain.getEmail(), updatePasswordForm.getOldPassword())){
           // accountService.updateEmailOrPassword(accountDomain, accountDomain.getEmail(), updatePasswordForm.getNewPassword());
            accountService.changePassword(accountDomain,updatePasswordForm.getOldPassword(),updatePasswordForm.getNewPassword());
        }else{
            return errorResult(getI18N().getOldPasswordErro());
        }
        return successResult(getI18N().getUpdateSuccess());
    }

    @RequestMapping(value = "setNewPassword", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult setNewPassword(@ModelAttribute UpdatePasswordForm updatePasswordForm){
        String userName = updatePasswordForm.getUserName();
        String sid = updatePasswordForm.getSid();
        AccountQuery query = new AccountQuery();
        query.setUserName(userName);
        AccountDomain accountDomain = accountService.getFirst(query);
        if(accountDomain!=null&&new Timestamp(accountDomain.getRegisterDate()).getTime()<= System.currentTimeMillis()){
            return errorResult("修改时间已过期");
        }
        String secretKey = accountDomain.getValidateCode();
        long date = accountDomain.getRegisterDate();//忽略毫秒数
        System.out.println("date:"+date);
        String key = userName+"$"+date+"$"+secretKey;
        String digitalSignature = DigestUtils.md5Hex(key);//数字签名
        if(!digitalSignature.equals(sid)){
            return errorResult("加密数据不正确");
        }
        accountService.setNewPassword(accountDomain,updatePasswordForm.getNewPassword());
        UserContext userContext = UserContext.current();
        if(userContext!=null){
            AccountDomain login = userContext.getAccountDomain();
            if(login!=null){
                UserContext.signOut();
            }
        }
        return successResult(getI18N().getOperateSuccess());
    }


    @RequestMapping(value = "toSetAdress", method = RequestMethod.GET)
    public ModelAndView toSetAdress(){
        AccountDomain accountDomain = UserContext.current().getAccountDomain();
        CustomerDomain customerDomain = customerService.getAccount(accountDomain.getId());
        ModelAndView mv = new ModelAndView("user/account/setAdress");
        mv.addObject("customerDomain",customerDomain);
        return mv;
    }

    @RequestMapping(value = "toValidVip", method = RequestMethod.GET)
    public ModelAndView toValidVip(){
        AccountDomain accountDomain = UserContext.current().getAccountDomain();
        CustomerDomain customerDomain = customerService.getAccount(accountDomain.getId());
        ModelAndView mv = new ModelAndView("user/account/validVip");
        mv.addObject("customerDomain",customerDomain);
        return mv;
    }

    @RequestMapping(value = "validVip", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult validVip(String phoneNumber){
        AccountDomain accountDomain = UserContext.current().getAccountDomain();
        CustomerDomain customerDomain = customerService.getAccount(accountDomain.getId());
        customerService.validVip(customerDomain,phoneNumber);
        return successResult(getI18N().getVlaidSuccess());
    }

    @RequestMapping(value = "validUserName", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult validUserName(String userName){
        AccountQuery query = new AccountQuery();
        query.setUserName(userName);
        if(accountService.getOne(query)!=null){
            return errorResult(getI18N().getEmailIsExsit());
        }
        return successResult(getI18N().getVlaidSuccess());
    }

    @RequestMapping(value = "vipDetail", method = RequestMethod.GET)
    public ModelAndView vipDetail(){
        //TODO 查询会员卡号
        AccountDomain accountDomain = UserContext.current().getAccountDomain();
        CustomerDomain customerDomain = customerService.getAccount(accountDomain.getId());
        TempMemberQuery query = new TempMemberQuery();

        String record = customerDomain.getPhone();
        if(StringUtils.isNotBlank(record)&&record.length()>2){
            record = record.substring(2,record.length());
        }
        query.setMobile(record);
        List<String> cardType = new ArrayList<>();
        cardType.add("CN-A");
        cardType.add("CN-B");
        cardType.add("CN-C");
        cardType.add("CN-D");
        query.setCardType(cardType);
        TempMemberDomain tempMemberDomain = tempMemberService.getFirst(query);
        if(tempMemberDomain==null){
            return new ModelAndView("redirect:toValidVip");
        }
        ModelAndView mv = new ModelAndView("user/account/vipDetail");
        mv.addObject("tempMemberDomain",tempMemberDomain);
        return mv;
    }

    @RequestMapping(value = "initSubscribe", method = RequestMethod.GET)
    public ModelAndView initSubscribe(){
        ModelAndView mv = new ModelAndView("user/account/subscribe");
        AccountDomain accountDomain = UserContext.current().getAccountDomain();
        CustomerDomain customerDomain = customerService.getAccount(accountDomain.getId());
        mv.addObject("customerDomain",customerDomain);
        return mv;
    }

    @RequestMapping(value = "setSubscribe", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult setSubscribe(String subscribeType0,String subscribeType1,String subscribeType2){
        String subscribeType = "";
        if(StringUtils.isNotBlank(subscribeType0)){
            subscribeType += subscribeType0;
        }
        if(StringUtils.isNotBlank(subscribeType1)){
            subscribeType += ","+subscribeType1;
        }
        if(StringUtils.isNotBlank(subscribeType2)){
            subscribeType += ","+subscribeType2;
        }
        System.out.println("subscribeType:"+subscribeType);
        AccountDomain accountDomain = UserContext.current().getAccountDomain();
        CustomerDomain customerDomain = customerService.getAccount(accountDomain.getId());
        customerDomain.setSubscribeType(subscribeType);
        customerService.update(customerDomain);
        return successResult(getI18N().getOperateSuccess());
    }

    public I18NReverse getI18N() {
        HttpSession session = HttpContext.current().getRequest().getSession();
        int type = 0;
        String  lang = (String)session.getAttribute("language");
        type = "en_US".equals(lang)?1:0;
        return new I18NReverse(type);
    }
}
