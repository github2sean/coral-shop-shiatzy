package com.dookay.coral.security.utils;


import com.dookay.coral.common.utils.RandomUtils;

/**
 * 生成token号码
 * @author Luxor
 * @version v0.1.1
 * @since 2016/11/22
 */
public class TokenUtil {
    /**
     * 生成token号码
     * @return token号码
     */
    public static String generateToken() {
        return RandomUtils.randomCustomUUID().concat(RandomUtils.randomString(6));
    }
}
