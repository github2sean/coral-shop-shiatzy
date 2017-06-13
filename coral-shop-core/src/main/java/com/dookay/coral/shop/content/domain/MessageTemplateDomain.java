package com.dookay.coral.shop.content.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 消息模板的domain
 * @author : luxor
 * @since : 2017年06月13日
 * @version : v0.0.1
 */
@Table(name = "t_content_message_template")
public class MessageTemplateDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键*/
	@Id
	private Long id;
	
	/*创建时间*/
	private Date createTime;
	
	/*标题*/
	private String title;
	
	/*英文标题*/
	private String enTitle;
	
	/*内容*/
	private String content;
	
	/*英文内容*/
	private String enContent;
	
	/*是否有效：1有效 0无效*/
	private Boolean isValid;
	
	/*模板类型：1邮件 2短信*/
	private Integer type;
	
	/*模板代码*/
	private Integer code;
	
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
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getEnTitle(){
		return enTitle;
	}
	
	public void setEnTitle(String enTitle){
		this.enTitle = enTitle;
	}
	
	public String getContent(){
		return content;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public String getEnContent(){
		return enContent;
	}
	
	public void setEnContent(String enContent){
		this.enContent = enContent;
	}
	
	public Boolean getIsValid(){
		return isValid;
	}
	
	public void setIsValid(Boolean isValid){
		this.isValid = isValid;
	}
	
	public Integer getType(){
		return type;
	}
	
	public void setType(Integer type){
		this.type = type;
	}
	
	public Integer getCode(){
		return code;
	}
	
	public void setCode(Integer code){
		this.code = code;
	}
	
	
}
