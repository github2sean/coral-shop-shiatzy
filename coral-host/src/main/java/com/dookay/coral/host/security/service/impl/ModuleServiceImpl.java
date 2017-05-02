package com.dookay.coral.host.security.service.impl;


import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.host.security.domain.AdminDomain;
import com.dookay.coral.host.security.domain.ModuleDomain;
import com.dookay.coral.host.security.domain.PermissionDomain;
import com.dookay.coral.host.security.mapper.ModuleMapper;
import com.dookay.coral.host.security.query.ModuleQuery;
import com.dookay.coral.host.security.service.IModuleService;
import com.dookay.coral.host.security.service.IPermissionAssignService;
import com.dookay.coral.host.security.service.IPermissionService;
import com.dookay.coral.host.security.service.IRoleAssignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 系统模块信息表的业务实现类
 * @author : luxor
 * @since : 2016年11月19日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("moduleService")
public class ModuleServiceImpl extends BaseServiceImpl<ModuleDomain> implements IModuleService {
	
	@Autowired
	private ModuleMapper moduleMapper;
	
	@Autowired
	private IRoleAssignService roleAssignService;
	@Autowired
	private IPermissionAssignService permissionAssignService;
	@Autowired
	private IPermissionService permissionService;
	
	@Override
	public List<ModuleDomain> getModulesByAdmin(AdminDomain adminDomain) {
		List<ModuleDomain> moduleDomains = null;
		Set<Long> roleIds = roleAssignService.getRolesId(adminDomain);//获取角色
		Set<Long> permissionIds = permissionAssignService.getPermissionsAssignId(roleIds);//获取资源ID
		Set<PermissionDomain> PermissionDomains = permissionService.getPermissionsId(permissionIds);
		
		List<Long> idList = new ArrayList<Long>();
		for (PermissionDomain permissionDomain : PermissionDomains) {
			if(permissionDomain != null)
				idList.add(permissionDomain.getModuleId());
		}
		
		if(idList.size() > 0) {
			moduleDomains = super.getList(new ModuleQuery(idList, false, "id"));
		}
		return moduleDomains;
	}

}
