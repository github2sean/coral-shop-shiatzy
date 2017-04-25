package com.dookay.shiatzy.web.mobile.base;

import com.dookay.coral.common.web.BaseController;
import com.dookay.coral.common.web.HttpContext;
import com.dookay.coral.host.user.context.UserContext;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 前台控制器基类
 *
 * @author : houkun
 * @version : v0.0.1
 * @since : 2017/3/13
 */
public abstract class MobileBaseController extends BaseController {

    /**
     * 同步验证登录
     *
     * @return
     */
    public String validLoginSync(String ref) throws IOException {
        HttpServletRequest request = HttpContext.current().getRequest();
        HttpServletResponse response = HttpContext.current().getResponse();
        if (StringUtils.isEmpty(ref)) {
            ref = URLEncoder.encode(request.getServletPath(), "UTF-8");
        }
        if (UserContext.isGuest()) {
            String loginRef = "/passport/login?ref=" + URLEncoder.encode(ref, "UTF-8");
            return loginRef;
        }
        return null;
    }

    public String validLoginSync() throws IOException {
        return this.validLoginSync(null);
    }

}
