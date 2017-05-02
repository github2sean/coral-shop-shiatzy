package com.dookay.coral.host.security.service.impl;

import com.dookay.coral.common.exception.ServiceException;
import com.dookay.coral.common.utils.Md5Utils;
import com.dookay.coral.host.security.domain.AdminDomain;
import com.dookay.coral.host.security.service.IPasswordService;
import org.springframework.stereotype.Service;

/**
 * 密码业务处理
 * @since : 2016年11月21日
 * @author : kezhan
 * @version : v0.0.1
 */
@Service(value="passwordService")
public class PasswordServiceImpl implements IPasswordService {
	@Override
	public void validate(AdminDomain adminDomain, String password) throws Exception {
		/*String username = user.getUsername();
		int retryCount = 0;*/
		//缓存功能待处理
		if (!matches(adminDomain, password))
			throw new ServiceException("账户或密码错误");
	}

	@Override
	public boolean matches(AdminDomain adminDomain, String newPassword) throws Exception {
		return adminDomain.getPassword().equals(encryptPassword(adminDomain.getUserName(), newPassword, adminDomain.getSalt()));
	}
	
	@Override
	public String encryptPassword(String username, String password, String salt) throws Exception {
		return Md5Utils.hash(username + password + salt);
	}
	
}
