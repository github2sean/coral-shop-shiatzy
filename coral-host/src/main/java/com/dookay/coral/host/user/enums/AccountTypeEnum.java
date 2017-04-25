package com.dookay.coral.host.user.enums;


import com.dookay.coral.common.enums.IEnum;

/**
 * 账户状态
 *
 * @author : yinqichao
 * @version : v0.0.1
 * @since : 2017-03-05
 */
public enum AccountTypeEnum implements IEnum {

    VALID(1, "有效"),
    INVALID(2, "无效");

    private int value;
    private String description;

    AccountTypeEnum(int value, String description) {
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

    public static AccountTypeEnum valueOf(Integer value) {
        AccountTypeEnum[] values = AccountTypeEnum.values();
        for (AccountTypeEnum item : values) {
            if (item.value == value) {
                return item;
            }
        }
        return null;
    }
}
