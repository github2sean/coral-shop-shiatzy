package com.dookay.coral.shop.order.extension;

import com.dookay.coral.common.persistence.pager.PageList;
import com.dookay.coral.host.user.domain.AccountDomain;
import com.dookay.coral.host.user.query.AccountQuery;
import com.dookay.coral.host.user.service.IAccountService;
import com.dookay.coral.shop.customer.domain.CustomerAddressDomain;
import com.dookay.coral.shop.customer.domain.CustomerDomain;
import com.dookay.coral.shop.customer.query.CustomerAddressQuery;
import com.dookay.coral.shop.customer.service.ICustomerAddressService;
import com.dookay.coral.shop.goods.service.IGoodsItemService;
import com.dookay.coral.shop.goods.service.IPrototypeSpecificationOptionService;
import com.dookay.coral.shop.order.domain.OrderDomain;
import com.dookay.coral.shop.order.domain.OrderItemDomain;
import com.dookay.coral.shop.order.query.OrderItemQuery;
import com.dookay.coral.shop.order.service.IOrderItemService;
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
public class OrderExtension {

    @Autowired
    private IOrderItemService orderItemService;
    @Autowired
    private IPrototypeSpecificationOptionService prototypeSpecificationOptionService;
    @Autowired
    private IGoodsItemService goodsItemService;

    public void withOrderItem(PageList<OrderDomain> orderDomainPageList){
        this.withOrderItem(orderDomainPageList.getList());
    }

    public void withOrderItem(List<OrderDomain> orderDomainList){
        for (OrderDomain orderDomain:orderDomainList){
            this.withOrderItem(orderDomain);
        }
    }

    public void  withOrderItem(OrderDomain orderDomain){
        OrderItemQuery query = new OrderItemQuery();
        query.setOrderId(orderDomain.getId());
        List<OrderItemDomain> orderItemDomainList = orderItemService.getList(query);
        for(OrderItemDomain line :orderItemDomainList){
            line.setSizeDomain(prototypeSpecificationOptionService.get(net.sf.json.JSONObject.fromObject(line.getSkuSpecifications()).getLong("size")));
            line.setGoodsItemDomain(goodsItemService.get(line.getItemId()));
        }
        orderDomain.setOrderItemDomainList(orderItemDomainList);
    }

}
