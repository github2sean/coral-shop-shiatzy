package com.dookay.coral.shop.order.extension;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.order.domain.ReservationDomain;
import com.dookay.coral.shop.order.domain.ReservationItemDomain;
import com.dookay.coral.shop.order.domain.ReturnRequestDomain;
import com.dookay.coral.shop.order.domain.ReturnRequestItemDomain;
import com.dookay.coral.shop.order.query.ReservationItemQuery;
import com.dookay.coral.shop.order.query.ReturnRequestItemQuery;
import com.dookay.coral.shop.order.service.IReservationItemService;
import com.dookay.coral.shop.order.service.IReturnRequestItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/5
 */
@Component
public class ReservationExtension {

    @Autowired
    private IReservationItemService reservationItemService;
    @Autowired
    private ICustomerService customerService;


    public void withReservationItem(PageList<ReservationDomain> reservationDomainPageList){
        this.withReservationItem(reservationDomainPageList.getList());
    }

    public void withReservationItem(List<ReservationDomain> reservationDomainList){
        for (ReservationDomain reservationDomain:reservationDomainList){
            this.withReservationItem(reservationDomain);
        }
    }

    public void  withReservationItem(ReservationDomain reservationDomain){
        ReservationItemQuery query = new ReservationItemQuery();
        query.setReservationId(reservationDomain.getId());
        List<ReservationItemDomain> reservationItemDomains = reservationItemService.getList(query);
        reservationDomain.setReservationItemDomainList(reservationItemDomains);
    }


    public void withCustomer(PageList<ReservationDomain> reservationDomainPageList){
        this.withCustomer(reservationDomainPageList.getList());
    }

    public void withCustomer(List<ReservationDomain> reservationDomainList){
        for (ReservationDomain returnRequestDomain:reservationDomainList){
            this.withCustomer(returnRequestDomain);
        }
    }

    public void withCustomer(ReservationDomain reservationDomain){
        CustomerDomain customerDomain = customerService.get(reservationDomain.getCustomerId());
        reservationDomain.setCustomerDomain(customerDomain);
    }

}