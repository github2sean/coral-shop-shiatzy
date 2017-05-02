package com.dookay.coral.host.user.context;


import com.dookay.coral.common.cache.RedisCache;
import com.dookay.coral.common.web.utils.SpringContextHolder;
import com.dookay.coral.host.user.enums.LoginClientEnum;

import java.util.Objects;

/**
 * 登录并发验证器
 *
 * @author : ChaosGod
 * @version : v0.0.1
 * @since : 2016/12/30
 */
public class UserLoginValidator {

    public static Boolean isLogin(Long userId, LoginClientEnum client) {
        if (userId == null)
            return false;
        RedisCache cache = SpringContextHolder.getBean(RedisCache.class);
        LoginClientModel oldClient = cache.getCache(getKey(userId), LoginClientModel.class);
        if (oldClient == null)
            return true;

        return Objects.equals(oldClient.getLoginClient(), client.getValue());
    }

    private static String getKey(Long userId) {
        return "market_login" + userId;
    }

    public static void setLoginClient(Long userId, LoginClientEnum client) {
        RedisCache cache = SpringContextHolder.getBean(RedisCache.class);
        String key = getKey(userId);
        cache.deleteCache(key);
        LoginClientModel model = new LoginClientModel();
        model.setLoginClient(client.getValue());
        cache.putCache(key, model);
    }
}
