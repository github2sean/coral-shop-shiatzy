package com.dookay.coral.host.security.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统权限 角色 关联授权表的domain
 * @author : luxor
 * @since : 2017年03月02日
 * @version : v0.0.1
 */
@Table(name = "t_security_permission_assign")
public class PermissionAssignDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*权限分配id*/
	@Id
	private Long id;
	
	/*权限id*/
	private Long permissionId;
	
	/*角色id*/
	private Long roleId;
	
	/*创建时间*/
	private Date createTime= new Date();
	
	/*修改时间*/
	private Date updateTime= new Date();
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getPermissionId(){
		return permissionId;
	}
	
	public void setPermissionId(Long permissionId){
		this.permissionId = permissionId;
	}
	
	public Long getRoleId(){
		return roleId;
	}
	
	public void setRoleId(Long roleId){
		this.roleId = roleId;
	}
	
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getUpdateTime(){
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
	
	
}
