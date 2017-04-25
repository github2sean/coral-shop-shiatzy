package com.dookay.coral.host.security.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 管理员用户 组织关联表的domain
 * @author : luxor
 * @since : 2017年03月02日
 * @version : v0.0.1
 */
@Table(name = "t_security_admin_organization")
public class AdminOrganizationDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**/
	@Id
	private Long id;
	
	/*管理员id*/
	private Long adminId;
	
	/*组织id*/
	private Long organizationId;
	
	/*是否默认公司:0 true 1 false 默认1 原因在与一个管理员因业务关系会出现对应多个分公司情况,查询时查询默认分公司*/
	private Integer defaultOrganization;
	
	/*状态 0正常 1关闭 默认0*/
	private Integer status;
	
	/*创建时间*/
	private Date createTime = new Date();
	
	/*修改时间*/
	private Date updateTime= new Date();
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getAdminId(){
		return adminId;
	}
	
	public void setAdminId(Long adminId){
		this.adminId = adminId;
	}
	
	public Long getOrganizationId(){
		return organizationId;
	}
	
	public void setOrganizationId(Long organizationId){
		this.organizationId = organizationId;
	}
	
	public Integer getDefaultOrganization(){
		return defaultOrganization;
	}
	
	public void setDefaultOrganization(Integer defaultOrganization){
		this.defaultOrganization = defaultOrganization;
	}
	
	public Integer getStatus(){
		return status;
	}
	
	public void setStatus(Integer status){
		this.status = status;
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
