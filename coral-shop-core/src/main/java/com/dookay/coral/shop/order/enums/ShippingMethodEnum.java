package com.dookay.coral.shop.order.enums;

import com.dookay.coral.common.enums.IEnum;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/9
 */
public enum ShippingMethodEnum implements IEnum {

    EXPRESS(1, "快递运送"),
    STORE(2,"门店取货");

    private int value;
    private String description;

    ShippingMethodEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public static ShippingMethodEnum valueOf(Integer value) {
        ShippingMethodEnum[] values = ShippingMethodEnum.values();
        for (ShippingMethodEnum item : values) {
            if (item.value == value) {
                return item;
            }
        }
        return null;
    }
}
