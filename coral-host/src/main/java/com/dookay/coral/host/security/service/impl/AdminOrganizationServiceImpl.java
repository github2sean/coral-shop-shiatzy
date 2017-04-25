package com.dookay.coral.host.security.service.impl;

import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.host.security.domain.AdminDomain;
import com.dookay.coral.host.security.domain.AdminOrganizationDomain;
import com.dookay.coral.host.security.enums.AdminLockedType;
import com.dookay.coral.host.security.enums.AdminOrganizationStatus;
import com.dookay.coral.host.security.mapper.AdminOrganizationMapper;
import com.dookay.coral.host.security.query.AdminOrganizationQuery;
import com.dookay.coral.host.security.query.AdminQuery;
import com.dookay.coral.host.security.service.IAdminOrganizationService;
import com.dookay.coral.host.security.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 管理员用户 组织关联表的业务实现类
 * @author : luxor
 * @since : 2017年03月02日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("adminOrganizationService")
public class AdminOrganizationServiceImpl extends BaseServiceImpl<AdminOrganizationDomain> implements IAdminOrganizationService {
	

	@Autowired
	private AdminOrganizationMapper adminOrganizationMapper;
	@Autowired
	private IAdminService adminService;
	@Override
	public Long getOrganizationIdByAdminId(Long adminId) {
		AdminOrganizationQuery adminOrganizationQuery = new AdminOrganizationQuery();
		adminOrganizationQuery.setStatus(AdminOrganizationStatus.NO.getValue());
		adminOrganizationQuery.setAdminId(adminId);
		return super.getOne(adminOrganizationQuery).getOrganizationId();
	}

	@Override
	public List<AdminOrganizationDomain> getAdminOrganizationByAdminIdList(Long adminId) {
		AdminOrganizationQuery adminOrganizationQuery = new AdminOrganizationQuery();
		adminOrganizationQuery.setStatus(AdminOrganizationStatus.NO.getValue());
		adminOrganizationQuery.setAdminId(adminId);
		return super.getList(adminOrganizationQuery);
	}

	@Override
	public List<Long> getAdminOrganizationIdList(Long adminId) {
		AdminOrganizationQuery adminOrganizationQuery = new AdminOrganizationQuery();
		adminOrganizationQuery.setStatus(AdminOrganizationStatus.NO.getValue());
		adminOrganizationQuery.setAdminId(adminId);
		List<Long> ids=new ArrayList<Long>();
		super.getList(adminOrganizationQuery).stream().forEach(vo->{
			ids.add(vo.getOrganizationId());
		});
		return ids;
	}

	/**
	 * 根据组织id获取管理员id列表
	 * @param organizationId
	 * @return
	 */
	private List<Long> getAdminIdsByOrganizationId(Long organizationId) {
		AdminOrganizationQuery adminOrganizationQuery = new AdminOrganizationQuery();
		adminOrganizationQuery.setStatus(AdminOrganizationStatus.NO.getValue());
		adminOrganizationQuery.setOrganizationId(organizationId);
		List<AdminOrganizationDomain> adminOrganizationDomainList = super.getList(adminOrganizationQuery);
		return adminOrganizationDomainList.stream().map(AdminOrganizationDomain::getAdminId).collect(Collectors.toList());
	}

	@Override
	public List<AdminDomain> getAdminListByOrganizationId(Long organizationId) {
		List<Long> adminIds= this.getAdminIdsByOrganizationId(organizationId);
		AdminQuery adminQuery = new AdminQuery();
		adminQuery.setIdList(adminIds);
		adminQuery.setLocked(AdminLockedType.OFF.getValue());
		return adminService.getList(adminQuery);
	}
}
