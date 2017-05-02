package com.dookay.coral.host.user.context;

import com.dookay.coral.common.web.HttpContext;
import com.dookay.coral.host.user.domain.AccountDomain;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用户上下文
 *
 * @author : ChaosGod
 * @version : v0.0.1
 * @since : 2016/11/18
 */
@Data
public class UserContext {
    private static final String USER_CONTEXT_KEY = "user_context";

    private AccountDomain accountDomain;

    public UserContext(AccountDomain accountDomain){
        this.accountDomain = accountDomain;
    }

    /**
     * 用户登录
     *
     * @param accountDomain 帐号domain
     */
    public static void signIn(AccountDomain accountDomain) {
        UserContext userContext = new UserContext(accountDomain);
        HttpServletRequest request = HttpContext.current().getRequest();
        request.getSession().setAttribute(USER_CONTEXT_KEY, userContext);
    }

    /**
     * 获取当前用户
     *
     * @return
     */
    public static UserContext current() {
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpSession session = request.getSession();
        if (session == null) return null;
        return (UserContext) session.getAttribute(USER_CONTEXT_KEY);
    }

    /**
     * 是否游客
     *
     * @return
     */
    public static Boolean isGuest() {
        return current() == null;
    }

    /**
     * 退出
     */
    public static void signOut() {
        HttpServletRequest request = HttpContext.current().getRequest();
        request.getSession().setAttribute(USER_CONTEXT_KEY, null);
    }


}
