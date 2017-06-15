package com.dookay.coral.shop.message.enums;

import com.dookay.coral.common.enums.IEnum;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/9
 */
public enum MessageTypeEnum implements IEnum {

    LOGIN_SUCCESS(1001,"注册成功"),
    CREATE_ORDER(1002,"订单通知"),
    SEND_GOODS(1003,"订单已配送"),
    CANCEL_ORDER(1004,"您的订单已取消"),
    RETURN_REQUEST(1005,"收到退货申请"),
    STORE_RESERVATION(1006,"精品店预约详情"),
    FAILED_RESERVATION(1007,"您的预约不成功");

    private int value;
    private String description;

    MessageTypeEnum(int value, String description) {
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

    public static MessageTypeEnum valueOf(Integer value) {
        MessageTypeEnum[] values = MessageTypeEnum.values();
        for (MessageTypeEnum item : values) {
            if (item.value == value) {
                return item;
            }
        }
        return null;
    }
}
