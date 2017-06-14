package com.dookay.coral.shop.content.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 订阅的domain
 * @author : luxor
 * @since : 2017年06月14日
 * @version : v0.0.1
 */
@Table(name = "t_content_subscribe")
public class SubscribeDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*邮件*/
	private String email;
	
	/*创建时间*/
	private Date createTime;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	
}
