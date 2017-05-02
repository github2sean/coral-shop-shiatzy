package com.dookay.coral.host.security.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统角色表的domain
 * @author : luxor
 * @since : 2017年03月02日
 * @version : v0.0.1
 */
@Table(name = "t_security_role")
public class RoleDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*角色id*/
	@Id
	private Long id;
	
	/*角色名称*/
	private String name;
	
	/*角色描述*/
	private String description;
	
	/*是否可用 0:true 1:false*/
	private Integer available;
	
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
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public Integer getAvailable(){
		return available;
	}
	
	public void setAvailable(Integer available){
		this.available = available;
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
