package com.dookay.shiatzy.web.mobile.model;

import com.dookay.coral.shop.order.domain.ShoppingCartItemDomain;
import lombok.Data;

/**
 * Created by admin on 2017/5/3.
 */
@Data
public class PreOderItem {
    private ShoppingCartItemDomain leftItem;
    private ShoppingCartItemDomain rightItem;

    public PreOderItem(ShoppingCartItemDomain leftItem, ShoppingCartItemDomain rightItem) {
        this.leftItem = leftItem;
        this.rightItem = rightItem;
    }
}
