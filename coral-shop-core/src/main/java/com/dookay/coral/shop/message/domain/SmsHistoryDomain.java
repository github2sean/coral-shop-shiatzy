package com.dookay.coral.shop.message.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 短信历史的domain
 * @author : luxor
 * @since : 2017年06月13日
 * @version : v0.0.1
 */
@Table(name = "t_message_sms_history")
public class SmsHistoryDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*编号ID*/
	@Id
	private Long id;
	
	/*创建时间*/
	private Date createTime;
	
	/*手机号码*/
	private String mobile;
	
	/*短信内容*/
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
	
	public String getMobile(){
		return mobile;
	}
	
	public void setMobile(String mobile){
		this.mobile = mobile;
	}
	
	public String getBody(){
		return body;
	}
	
	public void setBody(String body){
		this.body = body;
	}
	
	
}
