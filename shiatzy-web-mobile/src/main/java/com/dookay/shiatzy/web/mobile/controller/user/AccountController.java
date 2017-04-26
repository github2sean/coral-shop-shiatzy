package com.dookay.shiatzy.web.mobile.controller.user;

import com.dookay.coral.common.json.JsonUtils;
import com.dookay.coral.common.web.JsonResult;
import com.dookay.coral.host.user.context.UserContext;
import com.dookay.coral.host.user.domain.AccountDomain;
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


        ModelAndView mv = new ModelAndView("user/account/index");
        mv.addObject("accountDomain",UserContext.current().getAccountDomain());
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
        Long provinceId = getCustomeAddress.getProvinceId();
        Long cityId = getCustomeAddress.getCityId();
        String adress = getCustomeAddress.getAdress();

        Long accountId = UserContext.current().getAccountDomain().getId();
        AccountDomain updateAccount = accountService.get(accountId);
        updateAccount.setCellphone(phone);
        accountService.update(updateAccount);

        CustomerDomain updateCustomer = customerService.getAccount(accountId);
        updateCustomer.setFirstName(firstName);
        updateCustomer.setLastName(lastName);
        updateCustomer.setPhone(phone);

        CustomerAddressDomain updaCustomerAddress = customerAddressService.getAccount(updateCustomer.getId());
        updaCustomerAddress.setTitle(title);
        updaCustomerAddress.setCountryId(countryId);
        updaCustomerAddress.setProvinceId(provinceId);
        updaCustomerAddress.setCityId(cityId);
        updaCustomerAddress.setAdress(adress);
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
        Boolean checkAccount = accountService.validateAccount(accountDomain.getEmail(),updateEamilForm.getPassword());
        if(checkAccount){
            Boolean isSuccess =  accountService.updateEmailOrPassword(accountDomain,updateEamilForm.getNewEmail(),updateEamilForm.getNewPassword());
            if(!isSuccess){
                return successResult("修改失败");
            }
        }

        return successResult("修改成功");
    }

}
