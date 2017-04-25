package com.dookay.coral.host.security.service.impl;

import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.host.security.domain.PermissionAssignDomain;
import com.dookay.coral.host.security.mapper.PermissionAssignMapper;
import com.dookay.coral.host.security.query.PermissionAssignQuery;
import com.dookay.coral.host.security.service.IPermissionAssignService;
import com.google.common.collect.Sets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 系统模块信息表的业务实现类
 * @author : luxor
 * @since : 2016年11月19日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("permissionAssignService")
public class PermissionAssignServiceImpl extends BaseServiceImpl<PermissionAssignDomain> implements IPermissionAssignService {
	
	@Autowired
	private PermissionAssignMapper permissionAssignMapper;

	@Override
	public Set<Long> getPermissionsAssignId(Set<Long> roleIds) {
		Set<Long> resourceIds = Sets.newHashSet();
		for (Long roleId : roleIds) {
			List<PermissionAssignDomain> permissionAssignDomains = super.getList(new PermissionAssignQuery(roleId));
			if(permissionAssignDomains != null) {
				for (PermissionAssignDomain permissionAssignDomain : permissionAssignDomains) {
					resourceIds.add(permissionAssignDomain.getPermissionId());
				}
			}
		}
		return resourceIds;
	}
	
}
