package com.dookay.coral.common.web;

import com.dookay.coral.common.security.Cryptos;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * xxxxx
 *
 * @author : ChaosGod
 * @version : v0.0.1
 * @since : 2016/11/18
 */
public class CookieUtil {
    public static void setCookieValue(HttpServletResponse response, String key, String value, int maxAge) {
        String encryptedKey = Cryptos.aesEncrypt(key);
        String encryptedValue = Cryptos.aesEncrypt(value);
        Cookie cookie = new Cookie(encryptedKey, encryptedValue);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static String getCookieValue(HttpServletRequest request, String key) {
        if (request.getCookies() == null)
            return "";
        String encryptedKey = Cryptos.aesEncrypt(key);
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(encryptedKey)) {
                return Cryptos.aesDecrypt(cookie.getValue());
            }
        }
        return "";
    }

    public static void removeCookie(HttpServletResponse response, String key) {
        setCookieValue(response, key, "", -1);
    }
}
