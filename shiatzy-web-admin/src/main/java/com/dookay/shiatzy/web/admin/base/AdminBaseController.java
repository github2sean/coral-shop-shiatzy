package com.dookay.shiatzy.web.admin.base;

import com.dookay.coral.common.exception.ExceptionUtils;
import com.dookay.coral.security.user.AdminContext;
import com.dookay.coral.security.user.AdminModel;

import java.util.Set;

/**
 * 后台controller基类
 *
 * @author : ChaosGod
 * @version : v0.0.1
 * @since : 2016/11/20
 */
public class AdminBaseController extends BaseApiController {

    /**
     * 获取用户信息
     *
     * @return
     * @author : kezhan
     * @since : 2016年11月30日
     */
    protected AdminModel getAdmin() {
        AdminModel adminModel = AdminContext.getCurrent().getAdminModel();
        if(adminModel == null) {
        	ExceptionUtils.throwBaseException("登录信息异常");
        }
		return adminModel;
    }

    /**
     * 获取用户角色信息
     *
     * @return
     * @author : kezhan
     * @since : 2016年11月30日
     */
    protected Set<String> getAdminRoles() {
        return AdminContext.getCurrent().getRoles();
    }


    /**
     * 获取用户权限信息
     *
     * @return
     * @author : kezhan
     * @since : 2016年11月30日
     */
    protected Set<String> getAdminPermissions() {
        return AdminContext.getCurrent().getPermissions();
    }


}
