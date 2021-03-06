package com.dookay.coral.shop.order.extension;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.query.CustomerQuery;
import com.dookay.coral.shop.customer.service.ICustomerService;
import com.dookay.coral.shop.goods.service.IGoodsItemService;
import com.dookay.coral.shop.goods.service.IPrototypeSpecificationOptionService;
import com.dookay.coral.shop.order.domain.ReturnRequestDomain;
import com.dookay.coral.shop.order.domain.OrderItemDomain;
import com.dookay.coral.shop.order.domain.ReturnRequestItemDomain;
import com.dookay.coral.shop.order.query.OrderItemQuery;
import com.dookay.coral.shop.order.query.ReturnRequestItemQuery;
import com.dookay.coral.shop.order.service.IOrderItemService;
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
public class ReturnRequestExtension {

    @Autowired
    private IReturnRequestItemService returnRequestItemService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IPrototypeSpecificationOptionService prototypeSpecificationOptionService;
    @Autowired
    private IGoodsItemService goodsItemService;


    public void withReturnRequestItem(PageList<ReturnRequestDomain> returnRequestDomainPageList){
        this.withReturnRequestItem(returnRequestDomainPageList.getList());
    }

    public void withReturnRequestItem(List<ReturnRequestDomain> returnRequestDomainList){
        for (ReturnRequestDomain returnRequestDomain:returnRequestDomainList){
            this.withReturnRequestItem(returnRequestDomain);
        }
    }

    public void  withReturnRequestItem(ReturnRequestDomain returnRequestDomain){
        ReturnRequestItemQuery query = new ReturnRequestItemQuery();
        query.setReturnRequestId(returnRequestDomain.getId());
        List<ReturnRequestItemDomain> returnRequestItemDomainList = returnRequestItemService.getList(query);
        for(ReturnRequestItemDomain line:returnRequestItemDomainList){
            line.setSizeDomain(prototypeSpecificationOptionService.get(net.sf.json.JSONObject.fromObject(line.getSkuSpecifications()).getLong("size")));
            line.setGoodsItemDomain(goodsItemService.get(line.getItemId()));
        }
        returnRequestDomain.setReturnRequestItemDomainList(returnRequestItemDomainList);
    }


    public void withCustomer(PageList<ReturnRequestDomain> returnRequestDomainPageList){
        this.withCustomer(returnRequestDomainPageList.getList());
    }

    public void withCustomer(List<ReturnRequestDomain> returnRequestDomainList){
        for (ReturnRequestDomain returnRequestDomain:returnRequestDomainList){
            this.withCustomer(returnRequestDomain);
        }
    }

    public void withCustomer(ReturnRequestDomain returnRequestDomain){
        CustomerDomain customerDomain = customerService.get(returnRequestDomain.getCustomerId());
        returnRequestDomain.setCustomerDomain(customerDomain);
    }

}
