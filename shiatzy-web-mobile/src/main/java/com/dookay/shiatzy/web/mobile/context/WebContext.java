package com.dookay.shiatzy.web.mobile.context;

import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import org.apache.shiro.authc.Account;


/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/9
 */
public class WebContext {

    /*当前客户*/
    private CustomerDomain currentCustomer;
    /*当前账户*/
    private AccountDomain currentAccount;

    public CustomerDomain getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(CustomerDomain currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public AccountDomain getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(AccountDomain currentAccount) {
        this.currentAccount = currentAccount;
    }
}
