package com.dookay.coral.host.security.service;



import com.dookay.coral.common.service.IBaseService;
import com.dookay.coral.host.security.domain.AdminDomain;
import com.dookay.coral.host.security.domain.ModuleDomain;

import java.util.List;

/**
 * 系统模块信息表的业务层接口
 * @author : luxor
 * @since : 2016年11月19日
 * @version : v0.0.1
 */
public interface IModuleService extends IBaseService<ModuleDomain> {
	
	/**
	 * 根据管理员信息获取模块信息
	 * @param adminDomain
	 * @return
	 * @author : kezhan	
	 * @since : 2016年11月24日
	 *
	 */
	List<ModuleDomain> getModulesByAdmin(AdminDomain adminDomain);

}
