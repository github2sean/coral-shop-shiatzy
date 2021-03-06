package com.dookay.coral.shop.order.enums;

import com.dookay.coral.common.enums.IEnum;
import com.dookay.coral.common.web.CookieUtil;
import com.dookay.coral.common.web.HttpContext;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/6/28
 */
public enum ReturnRequestStatusEnum implements IEnum {

    Processing(1, isEnglish()?"Requesting":"进行中"),
    Completed(2, isEnglish()?"Completed":"已完成"),
    Canceled(3,isEnglish()?"Canceled":"取消申请");

    private int value;
    private String description;

    ReturnRequestStatusEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public static Boolean isEnglish() {
        Boolean type;
        String  lang = CookieUtil.getCookieValueByKey(HttpContext.current().getRequest(),"Language");
        type = "en_US".equals(lang)?true:false;
        return type;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public static ReturnRequestStatusEnum valueOf(Integer value) {
        ReturnRequestStatusEnum[] values = ReturnRequestStatusEnum.values();
        for (ReturnRequestStatusEnum item : values) {
            if (item.value == value) {
                return item;
            }
        }
        return null;
    }
}
