package com.dookay.coral.host.user.enums;


import com.dookay.coral.common.enums.IEnum;

/**
 * 登录客户端
 * @author Luxor
 * @version v0.0.1
 * @since 2016/12/30
 */
public enum LoginClientEnum implements IEnum {
    PC(1, "PC"),
    IOS(2, "IOS"),
    ANDROID(3,"ANDROID"),
    WECHAT(4, "WECHAT"),
    OTHER(5, "OTHER");

    private int value;
    private String description;

    LoginClientEnum(int value, String description) {
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
}
