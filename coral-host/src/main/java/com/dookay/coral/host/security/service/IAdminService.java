package com.dookay.coral.host.security.service;



import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.host.security.domain.AdminDomain;
import com.dookay.coral.host.security.domain.PermissionDomain;

import java.util.Set;

/**
 * 管理员信息表的业务层接口
 * @author : luxor
 * @since : 2016年11月19日
 * @version : v0.0.1
 */
public interface IAdminService extends IBaseService<AdminDomain> {
    /**
     * 获取管理员
     *
     * @param userName 用户名
     * @return 管理员domain
     */
    AdminDomain getAdmin(String userName);

    /**
     * 获取管理员
     * @param id     编号Id
     * @return 管理员domain
     */
    AdminDomain getAdmin(Long id);

    /**
     * 用户登录
     *
     * @param userName 用户名
     * @param password 未加密密码
     * @return 用户domain
     */
    AdminDomain login(String userName, String password, String ip) throws Exception;

    /**
     * 更改密码
     *
     * @param adminDomain  管理员
     * @param oldPassword 未加密旧密码
     * @param newPassword 未加密新密码
     * @return 用户domain
     */
    AdminDomain changePassword(AdminDomain adminDomain, String oldPassword, String newPassword);

    /**
     * 获取管理员角色名称
     * @param adminDomain 用户名
     * @return
     * @since : 2016年11月21日
     * @author : kezhan
     */
    Set<String> getAdminRoles(AdminDomain adminDomain);
    
    /**
     * 获取管理员权限
     * @param adminDomain 用户名
     * @return
     * @since : 2016年11月21日
     * @author : kezhan
     */
    Set<PermissionDomain> getAdminPermissions(AdminDomain adminDomain);
    
    /**
     * 添加管理员账户
     * @param adminDomain
     * @return
     * @author : kezhan	
     * @since : 2016年12月5日
     *
     */
    AdminDomain createAdmin(AdminDomain adminDomain);
    
    /**
     * 修改管理员账户信息
     * @param adminDomain
     * @author : kezhan	
     * @since : 2016年12月7日
     *
     */
    void updateAdmin(AdminDomain adminDomain);
    
    /**
     * 管理员重置账户密码
     * @param adminDomain 账户
     * @param newPassword 新密码
     * @author : kezhan	
     * @since : 2016年12月7日
     *
     */
    void resetPassword(AdminDomain adminDomain, String newPassword);
    
    /**
     * 账户角色授权
     * @param id
     * @param roleIds
     * @author : kezhan	
     * @since : 2016年12月11日
     */
    void adminAuthor(Long id, Set<Long> roleIds);
    
}
