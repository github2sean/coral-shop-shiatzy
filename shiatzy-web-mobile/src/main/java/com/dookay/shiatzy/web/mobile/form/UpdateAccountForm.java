package com.dookay.shiatzy.web.mobile.form;

import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.shop.customer.domain.CustomerAddressDomain;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import lombok.Data;

/**
 * Created by admin on 2017/4/26.
 */
@Data
public class UpdateAccountForm {

    private CustomerAddressDomain customerAddressDomain;

    private CustomerDomain customerDomain;

    private AccountDomain accountDomain;

}
