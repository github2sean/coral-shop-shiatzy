package com.dookay.coral.security.realm;

import com.dookay.coral.security.token.StatelessToken;
import com.dookay.coral.security.token.TokenManager;
import com.dookay.coral.security.user.AdminContext;
import com.dookay.coral.security.user.AdminModel;
import com.dookay.coral.security.user.UserSecurityRealm;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/3/26
 */
public class StatelessRealm extends AuthorizingRealm {
    @Autowired
    private AdminContext adminContext;
    @Autowired
    private UserSecurityRealm userSecurityRealm;

    public boolean supports(AuthenticationToken token) {
        //仅支持StatelessToken类型的Token
        return token instanceof StatelessToken;
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        if (StringUtils.isEmpty(username)) {
            return authorizationInfo;
        }
        AdminModel adminModel = userSecurityRealm.getAdmin(username);
        authorizationInfo.setRoles(userSecurityRealm.getAdminRoles(adminModel)); // 获取角色名称
        authorizationInfo.setStringPermissions(userSecurityRealm.getAdminPermissions(adminModel)); // 获取权限标识
        return authorizationInfo;
    }

    /**
     * 认证回调函数,登录时调用.
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        StatelessToken statelessToken = (StatelessToken) authenticationToken;
        String username = statelessToken.getUsername();
        //摘要当做token处理
        String token = statelessToken.getClientDigest();//TODO key 通过username获取
        //在服务器端生成客户端参数消息摘要
        System.out.println(statelessToken.getClientDigest());
        System.out.println(token);
        //然后进行客户端消息摘要和服务器端消息摘要的匹配
        if (!StringUtils.isEmpty(token) && !TokenManager.getMe().valid(token)) {
            throw new AuthenticationException("登录状态已经失效");
        }
        return new SimpleAuthenticationInfo(
                username,
                token,
                getName());
    }

}
