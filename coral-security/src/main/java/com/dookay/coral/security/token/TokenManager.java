package com.dookay.coral.security.token;

import com.alibaba.fastjson.JSON;
import com.dookay.coral.security.user.AdminContext;

import com.dookay.coral.security.utils.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Luxor
 * @version 2016/11/3.
 */
public class TokenManager {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static TokenManager me = new TokenManager();

    private Map<String, AdminContext> tokenUsers;
    private Map<String, AccessToken> tokens;

    public TokenManager() {
        tokens = new ConcurrentHashMap<String, AccessToken>();
        tokenUsers = new ConcurrentHashMap<String, AdminContext>();
    }

    /**
     * 获取单例对象
     * @return
     */
    public static TokenManager getMe() {
        return me;
    }

    /**
     * 验证AccessToken
     * @param token
     * @return
     */
    public boolean valid(String token) {
        AccessToken accessToken = tokens.get(token);
        logger.info(JSON.toJSONString(accessToken));
        if(accessToken == null || accessToken.isExpired())
            return false;
        tokens.replace(token,accessToken,new AccessToken(token));
        return true;
    }

    /**
     * 获取用户
     * @param token
     * @return
     */
    public AdminContext getUser(String token){
        return tokenUsers.get(token);
    }

    /**
     * 清除token
     * @param token
     */
    public void clear(String token){
        tokens.remove(token);
        tokenUsers.remove(token);
    }

    /**
     * 生成token值
     * @param user
     * @return
     */
    public String generateToken(AdminContext user) {
        String token = TokenUtil.generateToken();
        tokenUsers.put(token,user);
        tokens.put(token, new AccessToken(token));
        return token;
    }

    /**
     * 更新token
     * @param token
     * @param user
     */
    public void updateToken(String token,AdminContext user){
        tokenUsers.remove(token);
        tokenUsers.put(token,user);
    }
}
