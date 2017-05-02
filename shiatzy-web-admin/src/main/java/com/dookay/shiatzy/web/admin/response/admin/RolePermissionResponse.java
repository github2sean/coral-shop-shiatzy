package com.dookay.shiatzy.web.admin.response.admin;

import com.dookay.coral.host.security.domain.ModuleDomain;
import com.dookay.coral.host.security.domain.RoleDomain;
import com.dookay.shiatzy.web.admin.model.RolePermissionsModel;
import com.dookay.shiatzy.web.admin.response.SuccessResponse;

import java.util.List;

/**
 * @author Luxor
 * @version v0.0.1
 * @since 2017/3/29
 */
public class RolePermissionResponse extends SuccessResponse {
    private List<ModuleDomain> moduleDomainList;
    private List<RolePermissionsModel> rolePermissionsModelList;
    private RoleDomain roleDomain;

    public List<ModuleDomain> getModuleDomainList() {
        return moduleDomainList;
    }

    public void setModuleDomainList(List<ModuleDomain> moduleDomainList) {
        this.moduleDomainList = moduleDomainList;
    }

    public List<RolePermissionsModel> getRolePermissionsModelList() {
        return rolePermissionsModelList;
    }

    public void setRolePermissionsModelList(List<RolePermissionsModel> rolePermissionsModelList) {
        this.rolePermissionsModelList = rolePermissionsModelList;
    }

    public RoleDomain getRoleDomain() {
        return roleDomain;
    }

    public void setRoleDomain(RoleDomain roleDomain) {
        this.roleDomain = roleDomain;
    }
}
