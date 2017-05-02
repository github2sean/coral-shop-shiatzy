package com.dookay.coral.host.security.service.impl;

import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.host.security.domain.AdminDomain;
import com.dookay.coral.host.security.domain.RoleAssignDomain;
import com.dookay.coral.host.security.mapper.RoleAssignMapper;
import com.dookay.coral.host.security.query.RoleAssignQuery;
import com.dookay.coral.host.security.service.IRoleAssignService;
import com.google.common.collect.Sets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 系统角色授权表的业务实现类
 * @author : luxor
 * @since : 2016年11月19日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("roleAssignService")
public class RoleAssignServiceImpl extends BaseServiceImpl<RoleAssignDomain> implements IRoleAssignService {
	
	@Autowired
	private RoleAssignMapper roleAssignMapper;

	@Override
	public Set<Long> getRolesId(AdminDomain adminDomain) {
		Set<Long> roles = Sets.newHashSet();
		if(adminDomain != null) {
			List<RoleAssignDomain> roleAssignDomains = super.getList(new RoleAssignQuery(adminDomain.getId()));
			if(null != roleAssignDomains) {
				for (RoleAssignDomain roleAssignDomain : roleAssignDomains) {
					roles.add(roleAssignDomain.getRoleId());
				}
			}
		}
		return roles;
	}

}
