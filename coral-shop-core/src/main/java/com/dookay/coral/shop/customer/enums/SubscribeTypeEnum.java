package com.dookay.coral.shop.customer.enums;

import com.dookay.coral.common.enums.IEnum;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/6/2
 */
public enum SubscribeTypeEnum implements IEnum {

    TYPE1(1, "快递及邮件"),
    TYPE2(2, "短信"),
    TYPE3(3, "彩信"),
    TYPE4(4, "我希望收到夏姿电商產品信息");

    private int value;
    private String description;

    SubscribeTypeEnum(int value, String description) {
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

    public static SubscribeTypeEnum valueOf(Integer value) {
        SubscribeTypeEnum[] values = SubscribeTypeEnum.values();
        for (SubscribeTypeEnum item : values) {
            if (item.value == value) {
                return item;
            }
        }
        return null;
    }
}
