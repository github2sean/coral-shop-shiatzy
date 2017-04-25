package com.dookay.coral.host.security.service.impl;

import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.host.security.domain.OrganizationDomain;
import com.dookay.coral.host.security.mapper.OrganizationMapper;
import com.dookay.coral.host.security.service.IOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 部门公司组织表的业务实现类
 * @author : luxor
 * @since : 2017年03月02日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("organizationService")
public class OrganizationServiceImpl extends BaseServiceImpl<OrganizationDomain> implements IOrganizationService {
	
	@Autowired
	private OrganizationMapper organizationMapper;
	  
}
