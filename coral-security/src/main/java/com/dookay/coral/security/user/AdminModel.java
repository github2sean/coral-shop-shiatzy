package com.dookay.coral.security.user;

import java.io.Serializable;

/**
 * 用户信息模型
 * @author : kezhan
 * @since : 2016年12月1日
 * @version : v0.0.1
 */
public class AdminModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*管理员唯一标识*/
	private Long Id;

	/*名称*/
	private String realName;

	/*用户名*/
	private String userName;

	/*密码*/
	private String password;

	/*盐*/
	private String salt;

	/*是否可用 0:true 1:false*/
	private Integer locked;

	/*最后登录ip*/
	private String lastLoginIp;

	/*登录次数*/
	private Integer loginCount;

	public AdminModel() {
		super();
	}

	public AdminModel(Long Id, String realName, String userName, String password, String salt, Integer locked,
			String lastLoginIp, Integer loginCount) {
		super();
		this.Id = Id;
		this.realName = realName;
		this.userName = userName;
		this.password = password;
		this.salt = salt;
		this.locked = locked;
		this.lastLoginIp = lastLoginIp;
		this.loginCount = loginCount;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Integer getLocked() {
		return locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

}
