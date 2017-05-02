package com.dookay.coral.adapter.wechat.kit;

/**
 * 微信配置类，spring注入
 *
 * @author : houkun
 * @version : v0.0.1
 * @since : 2017/1/4
 */
public class WechatWxConfig {

    /*授权登录回调地址*/
    private String oauthLoginCallback;

    public WechatWxConfig() {
    }


    public WechatWxConfig(String oauthLoginCallback) {
        this.oauthLoginCallback = oauthLoginCallback;
    }

    public String getOauthLoginCallback() {
        return oauthLoginCallback;
    }

    public String getOauthLoginCallback(String ref) {
        return oauthLoginCallback + "?ref=" + ref;
    }

    public void setOauthLoginCallback(String oauthLoginCallback) {
        this.oauthLoginCallback = oauthLoginCallback;
    }
}
