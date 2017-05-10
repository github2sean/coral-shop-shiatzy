package com.dookay.shiatzy.web.mobile.controller.user;

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
import com.dookay.coral.shop.promotion.domain.CouponDomain;
import com.dookay.shiatzy.web.mobile.base.MobileBaseController;
import com.dookay.shiatzy.web.mobile.controller.PassportController;
import com.dookay.shiatzy.web.mobile.form.UpdateAccountForm;
import com.dookay.shiatzy.web.mobile.form.UpdateEamilForm;
import com.jfinal.json.Json;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    @RequestMapping(value = "updateEmailOrPassword", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateEmailOrPassword(@ModelAttribute UpdateEamilForm updateEamilForm){

        AccountDomain accountDomain = UserContext.current().getAccountDomain();
        System.out.print("updateEamilForm:"+ JsonUtils.toJSONString(updateEamilForm));
        if(accountService.validateAccount(accountDomain.getEmail(),updateEamilForm.getPassword())){
            accountService.updateEmailOrPassword(accountDomain,updateEamilForm.getNewEmail(),updateEamilForm.getNewPassword());
        }else{
            return errorResult("用户名和密码不匹配");
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
        return mv;
    }
    @RequestMapping(value = "setSubscribe", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult setSubscribe(Integer subscribeType){
        if(subscribeType==null){
            return errorResult("参数为空");
        }
        AccountDomain accountDomain = UserContext.current().getAccountDomain();
        CustomerDomain customerDomain = customerService.getAccount(accountDomain.getId());
        customerDomain.setSubscribeType(subscribeType);
        customerService.update(customerDomain);
        return successResult("操作成功");
    }

}
