package com.dookay.shiatzy.web.mobile.util;

import com.dookay.coral.common.web.CookieUtil;
import com.dookay.coral.common.web.HttpContext;

import javax.servlet.http.HttpSession;

/**
 * Created by admin on 2017/7/7.
 */
public class ChooseLanguage {
    public static I18NReverse getI18N() {
        int type = 0;
        String  lang =  CookieUtil.getCookieValueByKey(HttpContext.current().getRequest(),"Language");
        type = "en_US".equals(lang)?1:0;
        return new I18NReverse(type);
    }
}
