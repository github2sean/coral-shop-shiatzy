package com.dookay.coral.shop.goods.enums;

import com.dookay.coral.common.enums.IEnum;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/5/3
 */
public enum GoodsCategoryLevel implements IEnum {

    LEVEL1(1, "一级分类"),
    LEVEL2(2, "二级分类");

    private int value;
    private String description;

    GoodsCategoryLevel(int value, String description) {
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

    public static GoodsCategoryLevel valueOf(Integer value) {
        GoodsCategoryLevel[] values = GoodsCategoryLevel.values();
        for (GoodsCategoryLevel item : values) {
            if (item.value == value) {
                return item;
            }
        }
        return null;
    }
}
