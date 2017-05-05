package com.dookay.coral.common.enums;

import com.dookay.coral.common.enums.IEnum;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/3
 */
public enum ValidEnum implements IEnum {

    YES(1, "是"),
    NO(0, "否");

    private int value;
    private String description;

    ValidEnum(int value, String description) {
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

    public static ValidEnum valueOf(Integer value) {
        ValidEnum[] values = ValidEnum.values();
        for (ValidEnum item : values) {
            if (item.value == value) {
                return item;
            }
        }
        return null;
    }
}
