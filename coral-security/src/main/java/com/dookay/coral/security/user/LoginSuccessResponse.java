package com.dookay.coral.security.user;

import com.dookay.coral.security.user.ModuleModel;

import java.util.List;
import java.util.Set;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/3/26
 */
public class LoginSuccessResponse {
    private int code = 200;
    private String message = "登录成功";
    private String token;

    /*管理员信息*/
    private AdminModel adminModel;
    /*管理员角色集合*/
    private Set<String> roles;

    /*权限字符串*/
    private Set<String> permissions;

    /*菜单*/
    private List<ModuleModel> moduleModels;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AdminModel getAdminModel() {
        return adminModel;
    }

    public void setAdminModel(AdminModel adminModel) {
        this.adminModel = adminModel;
    }
}
