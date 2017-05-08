package com.dookay.coral.shop.customer.extension;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.host.user.query.AccountQuery;
import com.dookay.coral.host.user.service.IAccountService;
import com.dookay.coral.shop.customer.domain.CustomerAddressDomain;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.query.CustomerAddressQuery;
import com.dookay.coral.shop.customer.service.ICustomerAddressService;
import com.dookay.coral.shop.goods.domain.GoodsDomain;
import com.dookay.coral.shop.goods.domain.SkuDomain;
import com.dookay.coral.shop.goods.query.GoodsQuery;
import com.dookay.coral.shop.goods.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/5
 */
@Component
public class CustomerExtension {

    @Autowired
    private IAccountService accountService;
    @Autowired
    private ICustomerAddressService customerAddressService;

    public void withAccountAndAddress(PageList<CustomerDomain> customerDomainList){
        this.withAccountAndAddress(customerDomainList.getList());
    }

    public void withAccountAndAddress(List<CustomerDomain> customerDomainList){
        List<Long> ids = customerDomainList.stream().map(CustomerDomain::getAccountId).collect(Collectors.toList());
        AccountQuery query = new AccountQuery();
        query.setIds(ids);
        List<AccountDomain> accountDomainList = accountService.getList(query);

        /*List<Long> customerIds = customerDomainList.stream().map(CustomerDomain::getId).collect(Collectors.toList());
        CustomerAddressQuery queryAddress = new CustomerAddressQuery();
        queryAddress.setCustomerIds(customerIds);
        List<CustomerAddressDomain> customerAddressList = customerAddressService.getList(queryAddress);*/
        for (CustomerDomain customerDomain:customerDomainList){
            AccountDomain accountDomain = accountDomainList.stream()
                    .filter(x-> Objects.equals(x.getId(), customerDomain.getAccountId())).findFirst().orElse(null);
            customerDomain.setAccountDomain(accountDomain);
            CustomerAddressQuery queryAddress = new CustomerAddressQuery();
            queryAddress.setCustomerId(customerDomain.getId());
            List<CustomerAddressDomain> customerAddressList = customerAddressService.getList(queryAddress);
            customerDomain.setCustomerAddressList(customerAddressList);
        }
    }

    public void  withAccountAndAddress(CustomerDomain customerDomain){
        AccountDomain accountDomain = accountService.get(customerDomain.getAccountId());
        CustomerAddressQuery query = new CustomerAddressQuery();
        query.setCustomerId(customerDomain.getId());
        List<CustomerAddressDomain> customerAddressList = customerAddressService.getList(query);
        customerDomain.setAccountDomain(accountDomain);
        customerDomain.setCustomerAddressList(customerAddressList);
    }

}
