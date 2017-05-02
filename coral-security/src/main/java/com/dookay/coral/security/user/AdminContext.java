package com.dookay.coral.security.user;

import com.dookay.coral.common.enums.Constant;
import com.dookay.coral.common.web.HttpContext;
import com.dookay.coral.security.enums.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.List;
import java.util.Set;


/**
 * 管理员用户信息上下文
 *
 * @author : kezhan
 * @version : v0.0.1
 * @since : 2016年11月30日
 */
@Component
public class AdminContext implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /*管理员信息*/
    private AdminModel adminModel;

    /*管理员角色集合*/
    private Set<String> roles;

    /*权限字符串*/
    private Set<String> permissions;

    /*菜单*/
    private List<ModuleModel> moduleModels;

    public AdminModel getAdminModel() {
        return adminModel;
    }

    public void setAdminModel(AdminModel adminModel) {
        this.adminModel = adminModel;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public List<ModuleModel> getModuleModels() {
        return moduleModels;
    }

    public void setModuleModels(List<ModuleModel> moduleModels) {
        this.moduleModels = moduleModels;
    }

    /**
     * 获取用户上下文信息
     *
     * @return
     * @author : kezhan
     * @since : 2016年11月30日
     */
    public static AdminContext getCurrent() {
        AdminContext adminContext = new AdminContext();
        HttpSession session = HttpContext.current().getSession();
        if (session != null) {
            Object object = session.getAttribute(Constants.CURRENT);
            if (object != null) {
                adminContext = (AdminContext) object;
            }
        }

        /*Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            if (null != session) {
                Object object = session.getAttribute(Constants.CURRENT);
                if (null != object) {
                    adminContext = (AdminContext) object;
                }
            }
        }*/
        return adminContext;
    }

    //后台登录
    public static void signIn(AdminContext adminContext) {
        HttpSession session = HttpContext.current().getSession();
        if (session != null) {
            session.setAttribute(Constants.CURRENT, adminContext);
        }
        /*Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            if (null != session) {
                session.setAttribute(Constants.CURRENT, adminContext);
            }
        }*/
    }

    //后台退出
    public static void signOut() {
        HttpSession session = HttpContext.current().getSession();
        session.setAttribute(Constants.CURRENT, null);
    }
}
