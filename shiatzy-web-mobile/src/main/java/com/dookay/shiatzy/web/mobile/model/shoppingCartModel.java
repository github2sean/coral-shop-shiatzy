package com.dookay.shiatzy.web.mobile.model;

import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;
import lombok.Data;

import java.util.List;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/9
 */
@Data
public class shoppingCartModel {
    private List<ShoppingCartItemDomain> shoppingCartItemDomainList;

}
