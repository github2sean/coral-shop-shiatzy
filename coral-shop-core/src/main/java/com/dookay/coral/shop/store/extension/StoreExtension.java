package com.dookay.coral.shop.store.extension;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.order.domain.ReservationItemDomain;
import com.dookay.coral.shop.order.query.ReservationItemQuery;
import com.dookay.coral.shop.order.service.IReservationItemService;
import com.dookay.coral.shop.shipping.service.IShippingCountryService;
import com.dookay.coral.shop.store.domain.StoreCityDomain;
import com.dookay.coral.shop.store.domain.StoreCountryDomain;
import com.dookay.coral.shop.store.domain.StoreDomain;
import com.dookay.coral.shop.store.query.StoreCountryQuery;
import com.dookay.coral.shop.store.service.IStoreCityService;
import com.dookay.coral.shop.store.service.IStoreCountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/5
 */
@Component
public class StoreExtension {

    @Autowired
    private IStoreCountryService storeCountryService;

    @Autowired
    private IStoreCityService storeCityService;


    public void withCountryAndCity(PageList<StoreDomain> storeDomainPageList){
        this.withCountryAndCity(storeDomainPageList.getList());
    }

    public void withCountryAndCity(List<StoreDomain> storeDomainList){
        for (StoreDomain storeDomain:storeDomainList){
            this.withCountryAndCity(storeDomain);
        }
    }

    public void  withCountryAndCity(StoreDomain storeDomain){
        StoreCountryDomain storeCountryDomain = storeCountryService.get(Long.parseLong(storeDomain.getCountryId()));
        StoreCityDomain storeCityDomain = storeCityService.get(Long.parseLong(storeDomain.getCityId()));
        storeDomain.setStoreCityDomain(storeCityDomain);
        storeDomain.setStoreCountryDomain(storeCountryDomain);
    }


}
