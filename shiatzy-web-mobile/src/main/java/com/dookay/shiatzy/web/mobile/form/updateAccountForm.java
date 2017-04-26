package com.dookay.shiatzy.web.mobile.form;

import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.shop.customer.domain.CustomerAddressDomain;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import lombok.Data;

/**
 * Created by admin on 2017/4/26.
 */
@Data
public class updateAccountForm {

    private AccountDomain accountDomain;

    private CustomerDomain customerDomain;

    private CustomerAddressDomain customerAddressDomain;

}
