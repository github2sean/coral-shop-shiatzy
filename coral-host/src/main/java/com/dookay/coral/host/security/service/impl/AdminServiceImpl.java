package com.dookay.coral.host.security.service.impl;


import com.dookay.coral.common.exception.ExceptionUtils;
import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.common.utils.RandomUtils;
import com.dookay.coral.common.utils.RegularExpressionsUtils;
import com.dookay.coral.host.security.domain.AdminDomain;
import com.dookay.coral.host.security.domain.PermissionDomain;
import com.dookay.coral.host.security.domain.RoleAssignDomain;
import com.dookay.coral.host.security.domain.RoleDomain;
import com.dookay.coral.host.security.enums.AdminLockedType;
import com.dookay.coral.host.security.query.AdminQuery;
import com.dookay.coral.host.security.query.RoleAssignQuery;
import com.dookay.coral.host.security.service.*;
import com.google.common.collect.Sets;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 管理员信息表的业务实现类
 * @author : luxor
 * @since : 2016年11月19日
 * @version : v0.0.1
 */
@Service("adminService")
public class AdminServiceImpl extends BaseServiceImpl<AdminDomain> implements IAdminService {
	
	protected Logger logger = Logger.getLogger(AdminServiceImpl.class);
	
	@Autowired
	private IPasswordService passwordService;
	@Autowired
	private IRoleAssignService roleAssignService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IPermissionAssignService permissionAssignService;
	@Autowired
	private IPermissionService permissionService;
	
	@Override
	public AdminDomain getAdmin(String userName) {
		if (StringUtils.isEmpty(userName))
			return null;
		AdminQuery query = new AdminQuery();
		query.setUserName(userName);
		return super.getOne(query);
	}

	@Override
	public AdminDomain getAdmin(Long id) {
		return super.get(id);
	}
	
	@Override
	public AdminDomain login(String userName, String password, String ip) throws Exception {
		if (StringUtils.isEmpty(userName))
			throw new ServiceException("用户名不能为空");
		if (StringUtils.isEmpty(password))
			throw new ServiceException("密码不能为空");
		
		AdminDomain admin = null;
		admin =	super.getOne(new AdminQuery(userName));
		if (admin == null) {
			throw new ServiceException("用户不存在");
		}
		passwordService.validate(admin, password);//密码验证
		
		if(admin.getLocked() == AdminLockedType.OFF.getValue()) //用户被锁定
			throw new ServiceException("用户被锁定");
		if(admin.getLocked() != AdminLockedType.ON.getValue())//用户不为0不为1 则异常
			throw new ServiceException("账户状态异常");
		
		admin.setLastLoginIp(ip);
		admin.setLastLoginTime(new Date());
		super.update(admin);// 更新操作建议异步 jms 处理
		return admin;
	}

	@Override
	public AdminDomain changePassword(AdminDomain adminDomain, String oldPassword, String newPassword) {
		if (adminDomain == null)
			ExceptionUtils.throwBaseException("用户信息错误");

		try {
			if (!passwordService.matches(adminDomain, oldPassword)) {
				ExceptionUtils.throwBaseException("原密码错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionUtils.throwBaseException(e.getMessage());
		}
		
		//修改密码
		try {
			adminDomain.setSalt(RandomUtils.randomString(10));//生成新随机盐
			String encrypedNewPassword = passwordService.encryptPassword(adminDomain.getUserName(), newPassword, adminDomain.getSalt());
			adminDomain.setPassword(encrypedNewPassword);
			adminDomain.setUpdateTime(new Date());
			super.update(adminDomain);
			//修改成功后短信通知管理员
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionUtils.throwBaseException("修改密码失败");
		};
		return adminDomain;
	}
	
	
	@Override
	public Set<String> getAdminRoles(AdminDomain adminDomain) {
		//获取用户对应的角色列表
		Set<Long> roles = roleAssignService.getRolesId(adminDomain);
		Set<RoleDomain> roleDomains = roleService.getRoles(roles);
		
		Set<String> roleSet = new HashSet<String>();
		for (RoleDomain roleDomain : roleDomains) {
			roleSet.add(roleDomain.getName());
		}
		return roleSet;
	}

	
	@Override
	public Set<PermissionDomain> getAdminPermissions(AdminDomain adminDomain) {
		Set<PermissionDomain> permissions = Sets.newHashSet();
		
		Set<Long> roleIds = roleAssignService.getRolesId(adminDomain);//获取用户对应的角色列表
		Set<Long> permissionIds = permissionAssignService.getPermissionsAssignId(roleIds);//获取用户角色对应权限列表
		Set<PermissionDomain> permissionDomains = permissionService.getPermissionsId(permissionIds);
		
		for (PermissionDomain permission : permissionDomains) {
			permissions.add(permission);//获取对象  
		}
		
		return permissions;
	}

	@Override
	@Transactional("transactionManager")
	public AdminDomain createAdmin(AdminDomain adminDomain) {
		verifyAdmin(adminDomain);
		verifyCreateAdmin(adminDomain);
		AdminDomain adminLatest = null;
		try {
			String password = RandomUtils.randomNumbers(8);//产生随机密码
			adminDomain.setSalt(RandomUtils.randomString(10));//生成随机盐
			adminDomain.setPassword(passwordService.encryptPassword(adminDomain.getUserName(), password, adminDomain.getSalt()));
			adminLatest = super.create(adminDomain);
			logger.debug(" createpwd： " + password);
			adminLatest.setPassword(password);
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionUtils.throwBaseException("创建账户失败");
		}
		return adminLatest;
	}
	
	@Override
	@Transactional("transactionManager")
	public void updateAdmin(AdminDomain adminDomain) {
		AdminDomain domain = getAdmin(adminDomain.getId());
		verifyAdmin(adminDomain);
		verifyUpdateAdmin(adminDomain, domain);
		adminDomain.setUpdateTime(new Date());
		super.update(adminDomain);
	}
	
	@Override
	@Transactional("transactionManager")
	public void resetPassword(AdminDomain adminDomain , String newPassword) {
		if(adminDomain == null) {
			ExceptionUtils.throwBaseException("用户不存在");
		}
		if(!adminDomain.getLocked().equals(AdminLockedType.ON.getValue())) {//判断当前用户状态
			ExceptionUtils.throwBaseException("用户状态锁定");
		}
		
		//重置密码
		try {
			AdminDomain updateAdmin = new AdminDomain(); //声明修改对象
			//String newPassword = RandomUtils.randomNumbers(8);//产生随机密码
			updateAdmin.setId(adminDomain.getId());
			updateAdmin.setSalt(RandomUtils.randomString(10)); //生成新的盐
			updateAdmin.setUpdateTime(new Date());
			updateAdmin.setPassword(passwordService.encryptPassword(adminDomain.getUserName(), newPassword, updateAdmin.getSalt()));
			super.update(updateAdmin);
			logger.debug(" newPassword： " + newPassword);
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionUtils.throwBaseException("重置密码失败");
		}
	}
	
	@Override
	@Transactional("transactionManager")
	public void adminAuthor(Long id, Set<Long> roleIds) {
		//判断用户是否存在
		if(null == getAdmin(id)) {
			ExceptionUtils.throwBaseException("账户信息不存在");
		}
		
		//获取当前账户是否存在角色关联关系，存在则删除
		List<RoleAssignDomain> roleAssignDomains = roleAssignService.getList(new RoleAssignQuery(id));
		if(roleAssignDomains != null && roleAssignDomains.size() > 0) {
			for (RoleAssignDomain roleAssignDomain : roleAssignDomains) {
				roleAssignService.delete(roleAssignDomain.getId());
			}
		}
		
		//增加角色关联关系
		Set<RoleDomain> roleDomains = roleService.getRoles(roleIds);
		Date date = new Date();
		for (RoleDomain role : roleDomains) {
			RoleAssignDomain assignDomain = new RoleAssignDomain();
			assignDomain.setAdminId(id);
			assignDomain.setRoleId(role.getId());
			assignDomain.setCreateTime(date);
			RoleAssignDomain domain = roleAssignService.create(assignDomain);
			if(domain == null || domain.getId() == null) {
				ExceptionUtils.throwBaseException("角色授权异常");
			}
		}
	}
	
	/**
	 * 创建管理员信息验证
	 * @param adminDomain
	 * @author : kezhan	
	 * @since : 2016年12月6日
	 *
	 */
	private void verifyCreateAdmin(AdminDomain adminDomain) {
		if(null != getAdmin(adminDomain.getUserName())) {//查询用户是否存在
			ExceptionUtils.throwBaseException("登录名已被使用");
		}
		if(null != super.getOne(new AdminQuery(null, null, null, null, null, null, adminDomain.getEmail()))) {//获取邮箱是否存在
			ExceptionUtils.throwBaseException("邮箱已被使用");
		}
	}
	
	/**
	 * 修改管理员信息验证
	 * @param adminDomain
	 * @author : kezhan	
	 * @since : 2016年12月7日
	 *
	 */
	private void verifyUpdateAdmin(AdminDomain adminDomain, AdminDomain admin) {
		if(admin == null) {
			ExceptionUtils.throwBaseException("账户不存在");
		}
		if(!adminDomain.getUserName().equals(adminDomain.getUserName())) {
			ExceptionUtils.throwBaseException("登录名不允许修改");
		}
		if(!adminDomain.getEmail().equals(admin.getEmail())) {//如果邮箱有修改，获取修改邮箱是否存在
			if(null != super.getOne(new AdminQuery(null, null, null, null, null, null, adminDomain.getEmail()))) {
				ExceptionUtils.throwBaseException("邮箱已被使用");
			}
		}
	}

	/**
	 * 管理员基本信息验证
	 * @param adminDomain
	 * @author : kezhan	
	 * @since : 2016年12月7日
	 *
	 */
	private void verifyAdmin(AdminDomain adminDomain) {
		if(!RegularExpressionsUtils.verifyEmail(adminDomain.getEmail())) {
			ExceptionUtils.throwBaseException("邮箱异常");
		}
		if(!RegularExpressionsUtils.verifyMobilePhoneNumber(adminDomain.getPhone())) {
			ExceptionUtils.throwBaseException("手机号码异常");
		}
	}
	
}
