package com.dookay.coral.host.security.service;


import com.dookay.coral.host.security.domain.AdminDomain;

/**
 * 密码业务处理接口
 * @since : 2016年11月21日
 * @author : kezhan
 * @version : v0.0.1
 */
public interface IPasswordService {
	
	/**
	 * 信息校验
	 * @param adminQuery
	 * @param password
	 * @since : 2016年11月21日
	 * @author : kezhan
	 */
	void validate(AdminDomain adminDomain, String password) throws Exception;

	/**
	 * 密码校验
	 * @param adminQuery
	 * @param newPassword
	 * @return
	 * @since : 2016年11月21日
	 * @author : kezhan
	 */
	boolean matches(AdminDomain adminDomain, String newPassword) throws Exception;

	/**
	 * 生成新密码
	 * @param username
	 * @param password
	 * @param salt
	 * @return
	 * @since : 2016年11月21日
	 * @author : kezhan
	 */
	String encryptPassword(String username, String password, String salt) throws Exception;

}
