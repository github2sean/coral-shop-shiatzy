package com.dookay.shiatzy.web.admin.response.admin;

import com.dookay.coral.common.utils.DateUtils;
import com.dookay.coral.host.security.domain.AdminDomain;
import com.dookay.coral.host.security.domain.RoleDomain;

import java.util.Date;

/**
 * Created by LJY on 2017/3/13.
 */
public class AdminResponse {


    private Long id;

    private String realName;

    private String userName;

    private String password;

    private String phone;
    private String email;

    private Integer locked;


    private String description;


    private Date lastLoginTime;


    private String lastLoginIp;


    private Integer loginCount;

    private String companyCode;


    private String createTime;


    private String updateTime;

    private Long createAdmin;

    private Long updateAdmin;

    private boolean isCustomer;

    private String regionCode;

    private Long roleId;

    public static AdminResponse toResponse(AdminDomain adminDomain, RoleDomain roleDomain) {
        AdminResponse adminResponse = new AdminResponse();
        adminResponse.setId(adminDomain.getId());
        adminResponse.setRealName(adminDomain.getRealName());
        adminResponse.setEmail(adminDomain.getEmail());
        adminResponse.setPhone(adminDomain.getPhone());
        adminResponse.setPassword(adminDomain.getPassword());
        adminResponse.setDescription(adminDomain.getDescription());
        adminResponse.setUserName(adminDomain.getUserName());

        adminResponse.setCreateTime(DateUtils.formatDate(adminDomain.getCreateTime(), "yyyy-MM-dd"));

        if (roleDomain != null) {
            adminResponse.setRoleId(roleDomain.getId());
        }
        return adminResponse;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public Boolean getIsCustomer() {
        return isCustomer;
    }

    public void setIsCustomer(Boolean isCustomer) {
        this.isCustomer = isCustomer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }


    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateAdmin() {
        return createAdmin;
    }

    public void setCreateAdmin(Long createAdmin) {
        this.createAdmin = createAdmin;
    }

    public Long getUpdateAdmin() {
        return updateAdmin;
    }

    public void setUpdateAdmin(Long updateAdmin) {
        this.updateAdmin = updateAdmin;
    }
}
