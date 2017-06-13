package com.dookay.coral.shop.message.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 邮件历史的domain
 * @author : luxor
 * @since : 2017年06月13日
 * @version : v0.0.1
 */
@Table(name = "t_message_email_history")
public class EmailHistoryDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*编号ID*/
	@Id
	private Long id;
	
	/*创建时间*/
	private Date createTime;
	
	/*邮箱*/
	private String email;
	
	/*标题*/
	private String title;
	
	/*邮件内容*/
	private String body;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getBody(){
		return body;
	}
	
	public void setBody(String body){
		this.body = body;
	}
	
	
}
