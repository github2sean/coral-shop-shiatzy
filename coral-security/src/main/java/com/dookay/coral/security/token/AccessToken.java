package com.dookay.coral.security.token;

import java.io.Serializable;
import java.util.Date;

/**
 *  访问令牌，用于restfull身份认证
 * @author Luxor
 * @version 2016/11/3.
 */
public class AccessToken implements Serializable {
    private static final long serialVersionUID = -3667914001133777991L;

    private String accessToken;

    private Date expirationTime;

    public AccessToken(String accessToken, Date expirationTime) {
        if (accessToken == null)
            throw new IllegalArgumentException("accessToken can not be null");

        this.accessToken = accessToken;
        this.expirationTime = expirationTime;
    }

    public AccessToken(String accessToken) {
        if (accessToken == null)
            throw new IllegalArgumentException("accessToken can not be null");

        this.accessToken = accessToken;
        this.expirationTime = new Date (System.currentTimeMillis() +7200 * 1000L);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public int getExpiresIn() {
        return expirationTime != null ? Long.valueOf((expirationTime.getTime() - System.currentTimeMillis()) / 1000L)
                .intValue() : 0;
    }

    protected void setExpiresIn(int delta) {
        setExpirationTime(new Date(System.currentTimeMillis() + delta));
    }

    public boolean isExpired() {
        return expirationTime != null && expirationTime.before(new Date());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccessToken)) return false;

        AccessToken that = (AccessToken) o;

        return accessToken.equals(that.accessToken);

    }

    @Override
    public int hashCode() {
        return accessToken.hashCode();
    }
}
