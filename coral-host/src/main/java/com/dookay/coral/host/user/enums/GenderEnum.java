package com.dookay.coral.host.user.enums;


import com.dookay.coral.common.enums.IEnum;

/**
 * 用户性别
 *
 * @author : yinqichao
 * @version : v0.0.1
 * @since : 2017-03-05
 */
public enum GenderEnum implements IEnum {

    SECRECY(1, "保密"),
    MALE(2, "男"),
    FEMALE(3, "女");

    private int value;
    private String description;

    GenderEnum(int value, String description) {
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

    public static GenderEnum valueOf(Integer value) {
        GenderEnum[] values = GenderEnum.values();
        for (GenderEnum item : values) {
            if (item.value == value) {
                return item;
            }
        }
        return null;
    }
}
