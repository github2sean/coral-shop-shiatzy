package com.dookay.coral.host.security.service;

import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.host.security.domain.AdminDomain;
import com.dookay.coral.host.security.domain.AdminOrganizationDomain;

import java.util.List;

/**
 * 管理员用户 组织关联表的业务层接口
 * @author : luxor
 * @since : 2017年03月02日
 * @version : v0.0.1
 */
public interface IAdminOrganizationService extends IBaseService<AdminOrganizationDomain> {
    /**
     * 根据管理员id获取组织id
     * @param adminId
     * @return
     */
    Long getOrganizationIdByAdminId(Long adminId);

    /***
     *根据管理员id获取组织id集合
     * @param adminId
     * @author yedong
     * @return
     */
    List<AdminOrganizationDomain> getAdminOrganizationByAdminIdList(Long adminId);

    /***
     *根据管理员id获取组织id集合
     * @param adminId
     * @author yedong
     * @return
     */
    List<Long> getAdminOrganizationIdList(Long adminId);

    /**
     * 根据组织id获取管理员列表
     * @param organizationId
     * @return
     */
    List<AdminDomain> getAdminListByOrganizationId(Long organizationId);
}
