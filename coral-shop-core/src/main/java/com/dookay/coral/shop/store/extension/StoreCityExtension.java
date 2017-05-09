package com.dookay.coral.shop.store.extension;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.shop.store.domain.StoreCityDomain;
import com.dookay.coral.shop.store.domain.StoreCountryDomain;
import com.dookay.coral.shop.store.domain.StoreCityDomain;
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
public class StoreCityExtension {

    @Autowired
    private IStoreCountryService storeCountryService;

    public void withCountry(PageList<StoreCityDomain> storeCityDomainPageList){
        this.withCountry(storeCityDomainPageList.getList());
    }

    public void withCountry(List<StoreCityDomain> storeCityDomainList){
        for (StoreCityDomain storeCityDomain:storeCityDomainList){
            this.withCountry(storeCityDomain);
        }
    }
    public void  withCountry(StoreCityDomain StoreCityDomain){
        StoreCountryDomain storeCountryDomain = storeCountryService.get(Long.parseLong(StoreCityDomain.getCountryId()));
        StoreCityDomain.setStoreCountryDomain(storeCountryDomain);
    }


}
