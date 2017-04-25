package com.dookay.coral.host.security.service.impl;


import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.host.security.domain.PermissionDomain;
import com.dookay.coral.host.security.enums.PermissionAvailableType;
import com.dookay.coral.host.security.mapper.PermissionMapper;
import com.dookay.coral.host.security.query.PermissionQuery;
import com.dookay.coral.host.security.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * 系统模块信息表的业务实现类
 * @author : luxor
 * @since : 2016年11月19日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("permissionService")
public class PermissionServiceImpl extends BaseServiceImpl<PermissionDomain> implements IPermissionService {
	
	@Autowired
	private PermissionMapper permissionMapper;

	@Override
	public Set<PermissionDomain> getPermissionsId(Set<Long> permissionsId) {
		Set<PermissionDomain> permissions = new HashSet<PermissionDomain>();
		for (Long permissionId : permissionsId) {
			PermissionDomain permissionDomain = super.getOne(new PermissionQuery(permissionId, PermissionAvailableType.ON.getValue(), false, "id"));//资源状态为可用
			if (permissionDomain != null) {
				permissions.add(permissionDomain);
			}
			
			//TODO 此代码新增测试使用
			/*if(permissionDomain.getType().equals(PermissionType.CLICK.getType())) {
				PermissionQuery permissionQuery = new PermissionQuery();
				permissionQuery.setParentId(permissionId);
				permissionQuery.setAvailable(PermissionAvailableType.ON.getValue());
				List<PermissionDomain> permissionList = super.getList(permissionQuery);//资源状态为可用
				if(permissionList != null && permissionList.size() > 0) {
					for (PermissionDomain permission : permissionList) {
						permissions.add(permission);
						
						
					}
				}
			}*/

		}	
		return permissions;
	}

}
