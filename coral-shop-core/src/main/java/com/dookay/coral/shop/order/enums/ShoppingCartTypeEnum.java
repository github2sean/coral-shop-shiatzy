package com.dookay.coral.shop.order.enums;

import com.dookay.coral.common.enums.IEnum;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/4/27
 */
public enum ShoppingCartTypeEnum implements IEnum {

    SHOPPING_CART(1, "购物车"),
    WISH_LIST(2,"心愿单"),
    RESERVATION(3,"精品店预约");

    private int value;
    private String description;

    ShoppingCartTypeEnum(int value, String description) {
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

    public static ShoppingCartTypeEnum valueOf(Integer value) {
        ShoppingCartTypeEnum[] values = ShoppingCartTypeEnum.values();
        for (ShoppingCartTypeEnum item : values) {
            if (item.value == value) {
                return item;
            }
        }
        return null;
    }
}
