package com.dookay.shiatzy.web.mobile.controller;

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
import com.dookay.coral.shop.shipping.domain.ShippingCountryDomain;
import com.dookay.coral.shop.shipping.query.ShippingCountryQuery;
import com.dookay.coral.shop.shipping.service.IShippingCountryService;
import com.dookay.shiatzy.web.mobile.base.MobileBaseController;
import com.dookay.shiatzy.web.mobile.form.UpdateAccountForm;
import com.dookay.shiatzy.web.mobile.form.UpdateEmailForm;
import com.dookay.shiatzy.web.mobile.form.UpdatePasswordForm;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index(){
     // System.out.print(UserContext.current().getAccountDomain().getEmail());
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        AccountDomain accountDomain = UserContext.current().getAccountDomain();
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

        Long countryId = getCustomeAddress.getCountryId();

        String address = getCustomeAddress.getAddress();

        Long accountId = UserContext.current().getAccountDomain().getId();
        AccountDomain updateAccount = accountService.get(accountId);
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
            return successResult("修改失败");
        }

        return successResult("修改成功");
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

    @RequestMapping(value = "updateEmail", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateEmail(@ModelAttribute UpdateEmailForm updateEmailForm){
        beanValidator(updateEmailForm);
        AccountDomain accountDomain = UserContext.current().getAccountDomain();
        CustomerDomain customerDomain = customerService.getAccount(accountDomain.getId());
        String email = updateEmailForm.getEmail();
        accountDomain.setEmail(email);
        accountDomain.setUserName(email);
        customerDomain.setEmail(email);
        if(accountService.getAccountByEmail(email)!=null){
            return errorResult("邮箱已存在");
        }
        accountService.update(accountDomain);
        customerService.update(customerDomain);
        return successResult("修改成功");
    }

    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updatePassword(@ModelAttribute UpdatePasswordForm updatePasswordForm){
        AccountDomain accountDomain = UserContext.current().getAccountDomain();
        if(accountService.validateAccount(accountDomain.getEmail(), updatePasswordForm.getOldPassword())){
           // accountService.updateEmailOrPassword(accountDomain, accountDomain.getEmail(), updatePasswordForm.getNewPassword());
            accountService.changePassword(accountDomain,updatePasswordForm.getOldPassword(),updatePasswordForm.getNewPassword());
        }else{
            return errorResult("旧密码不正确");
        }
        return successResult("修改成功");
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
        return successResult("验证成功");
    }

    @RequestMapping(value = "validUserName", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult validUserName(String userName){
        AccountQuery query = new AccountQuery();
        query.setUserName(userName);
        if(accountService.getOne(query)!=null){
            return errorResult("邮箱已存在");
        }
        return successResult("验证成功");
    }

    @RequestMapping(value = "vipDetail", method = RequestMethod.GET)
    public ModelAndView vipDetail(){
        //TODO 查询会员卡号

        ModelAndView mv = new ModelAndView("user/account/vipDetail");
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
        return successResult("操作成功");
    }

}
