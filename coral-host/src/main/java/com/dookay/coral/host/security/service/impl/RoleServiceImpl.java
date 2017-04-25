package com.dookay.coral.host.security.service.impl;

import com.dookay.coral.common.exception.ExceptionUtils;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.host.security.domain.*;
import com.dookay.coral.host.security.enums.RoleAvailableType;
import com.dookay.coral.host.security.mapper.RoleMapper;
import com.dookay.coral.host.security.query.PermissionAssignQuery;
import com.dookay.coral.host.security.query.RoleAssignQuery;
import com.dookay.coral.host.security.query.RoleQuery;
import com.dookay.coral.host.security.service.IPermissionAssignService;
import com.dookay.coral.host.security.service.IPermissionService;
import com.dookay.coral.host.security.service.IRoleAssignService;
import com.dookay.coral.host.security.service.IRoleService;
import com.google.common.collect.Sets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 系统角色表的业务实现类
 *
 * @author : luxor
 * @version : v0.0.1
 * @since : 2016年11月19日
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<RoleDomain> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private IPermissionAssignService permissionAssignService;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private IRoleAssignService roleAssignService;
    @Autowired
    private IRoleService roleService;

    @Override
    public Set<RoleDomain> getRoles(Set<Long> roleIds) {
        Set<RoleDomain> roles = Sets.newHashSet();
        for (Long roleId : roleIds) {
            RoleDomain roleDomain = super.getOne(new RoleQuery(roleId, RoleAvailableType.ON.getValue())); //根据角色主键ID 获取 状态为0 可用的角色
            if (roleDomain != null)
                roles.add(roleDomain);
        }
        return roles;
    }

    @Transactional(transactionManager = "transactionManager")
    @Override
    public void createRole(RoleDomain roleDomain) {
        //获取角色名称是否存在
        RoleDomain domain = super.getOne(new RoleQuery(roleDomain.getName()));
        if (domain != null) {
            ExceptionUtils.throwBaseException("角色已存在");
        }
        roleDomain.setCreateTime(new Date());
        super.create(roleDomain);
    }

    @Override
    public void updateRole(RoleDomain roleDomain) {
        RoleDomain domain = super.get(roleDomain.getId());
        if (domain == null) {
            ExceptionUtils.throwBaseException("角色不存在");
        }
        roleDomain.setUpdateTime(new Date());
        super.update(roleDomain);
    }

    @Override
    @Transactional
    public void rolePermissions(Long id, Set<Long> clickId, Set<Long> menuId, Set<Long> attributeId) {
        //获取角色是否存在
        RoleDomain roleDomain = super.get(id);
        if (roleDomain == null) {
            ExceptionUtils.throwBaseException("角色信息异常");
        }

        //角色权限关联数据
        List<PermissionAssignDomain> permissionAssignDomains = permissionAssign(id, clickId, menuId, attributeId);

        //删除当前角色所有权限记录
        List<PermissionAssignDomain> assignDomains = permissionAssignService.getList(new PermissionAssignQuery(id));
        if (assignDomains != null) {
            for (PermissionAssignDomain permissionAssignDomain : assignDomains) {
                permissionAssignService.delete(permissionAssignDomain.getId());
            }
        }
        //新增权限
        for (int i = 0; i < permissionAssignDomains.size(); i++) {
            PermissionAssignDomain assignDomain = permissionAssignService.create(permissionAssignDomains.get(i));
            if (assignDomain == null || assignDomain.getId() == null) {
                ExceptionUtils.throwBaseException("权限分配异常");
            }
        }
    }

    @Override
    public RoleDomain getRole(AdminDomain adminDomain) {
        RoleAssignQuery roleAssignQuery = new RoleAssignQuery();
        roleAssignQuery.setAdminId(adminDomain.getId());
        RoleAssignDomain roleAssignDomain = roleAssignService.getOne(roleAssignQuery);
        if (roleAssignDomain != null) {
            RoleDomain roleDomain = get(roleAssignDomain.getRoleId());
            return roleDomain;
        }
        return null;
    }

    /**
     * 角色权限关联处理
     *
     * @param id
     * @param clickId
     * @param menuId
     * @param attributeId
     * @return
     * @author : kezhan
     * @since : 2016年12月9日
     */
    public List<PermissionAssignDomain> permissionAssign(Long id, Set<Long> clickId, Set<Long> menuId, Set<Long> attributeId) {

        //获取权限集合
        Set<PermissionDomain> clickSet = permissionService.getPermissionsId(clickId);
        Set<PermissionDomain> menuSet = permissionService.getPermissionsId(menuId);
        Set<PermissionDomain> attributeSet = permissionService.getPermissionsId(attributeId);

        //角色权限关联数据
        List<PermissionAssignDomain> permissionAssignDomains = new ArrayList<PermissionAssignDomain>();
        //处理角色权限关系
        rolePermission(id, clickSet, permissionAssignDomains);
        rolePermission(id, menuSet, permissionAssignDomains);
        rolePermission(id, attributeSet, permissionAssignDomains);

        //这里注意权限关系执行比较执行顺序，从子级 往 父级比较
        permissionAssignParentId(attributeSet, permissionAssignDomains);
        permissionAssignParentId(clickSet, permissionAssignDomains);
        permissionAssignParentId(menuSet, permissionAssignDomains);

        return permissionAssignDomains;
    }

    /**
     * 角色权限累计
     *
     * @param id
     * @param permissionDomains
     * @param permissionAssignDomains
     * @author : kezhan
     * @since : 2016年12月9日
     */
    public void rolePermission(Long id, Set<PermissionDomain> permissionDomains,
                               List<PermissionAssignDomain> permissionAssignDomains) {
        if (permissionDomains != null && permissionAssignDomains != null && id != null) {
            Date date = new Date();
            for (PermissionDomain permissionDomain : permissionDomains) {
                PermissionAssignDomain domain = new PermissionAssignDomain();
                domain.setPermissionId(permissionDomain.getId());
                domain.setRoleId(id);
                domain.setCreateTime(date);
                permissionAssignDomains.add(domain);
            }
        }
    }

    /**
     * 对比是否村子父子关系权限 有则删除子权限id 保留父权限id即可
     *
     * @param set
     * @param permissionAssignDomains
     * @author : kezhan
     * @since : 2016年12月9日
     */
    public void permissionAssignParentId(Set<PermissionDomain> set, List<PermissionAssignDomain> permissionAssignDomains) {
        for (PermissionDomain permissionDomain : set) {
            for (int i = 0; i < permissionAssignDomains.size(); i++) {
                if (permissionDomain.getParentId().equals(permissionAssignDomains.get(i).getPermissionId())) {
                    for (int j = 0; j < permissionAssignDomains.size(); j++) {
                        if (permissionAssignDomains.get(j).getPermissionId().equals(permissionDomain.getParentId())) {
                            permissionAssignDomains.remove(j);
                        }
                    }
                }
            }
        }
    }
}
