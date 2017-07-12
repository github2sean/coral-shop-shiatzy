package com.dookay.coral.shop.customer.service;

import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.host.user.query.AccountQuery;
import com.dookay.coral.host.user.service.IAccountService;
import com.dookay.coral.shop.base.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/7/11
 */
public class ICustomerServiceTest extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IAccountService accountService;

    @Test
    public void updatePassword(){
        AccountQuery  accountQuery = new AccountQuery();

        List<AccountDomain> accountDomainList = accountService.getList(accountQuery);
        for (AccountDomain accountDomain:accountDomainList){
            accountService.resetPassword(accountDomain,"1234546");
        }
    }
}