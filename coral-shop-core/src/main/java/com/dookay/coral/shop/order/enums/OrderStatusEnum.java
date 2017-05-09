package com.dookay.coral.shop.order.enums;

import com.dookay.coral.common.enums.IEnum;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/9
 */
public enum OrderStatusEnum implements IEnum {

    UNPAID(1, "待支付"),
    PAID(2, "已支付"),
    SHIPPED(3, "已发货"),
    RECEIVED(4, "已收货"),
    CANCELED(-1, "已取消"),;

    private int value;
    private String description;

    OrderStatusEnum(int value, String description) {
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

    public static OrderStatusEnum valueOf(Integer value) {
        OrderStatusEnum[] values = OrderStatusEnum.values();
        for (OrderStatusEnum item : values) {
            if (item.value == value) {
                return item;
            }
        }
        return null;
    }
}
