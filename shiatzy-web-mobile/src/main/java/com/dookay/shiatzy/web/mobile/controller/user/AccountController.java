package com.dookay.shiatzy.web.mobile.controller.user;

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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

        ModelAndView mv = new ModelAndView("user/account/details");
        mv.addObject("accountDomain",accountDomain);
        mv.addObject("customerDomain",customerDomain);
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
}
