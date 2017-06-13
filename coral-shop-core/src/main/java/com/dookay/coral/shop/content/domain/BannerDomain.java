package com.dookay.coral.shop.content.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * banner的domain
 * @author : luxor
 * @since : 2017年06月13日
 * @version : v0.0.1
 */
@Table(name = "t_content_banner")
public class BannerDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*标题*/
	private String title;
	
	/*英文标题*/
	private String enTitle;
	
	/*子标题*/
	private String subTitle;
	
	/*英文子标题*/
	private String enSubTitle;
	
	/*是否有效：1有效 0无效*/
	private Integer isValid;
	
	/*图片*/
	private String image;
	
	/*链接地址*/
	private String link;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
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
	
	public String getSubTitle(){
		return subTitle;
	}
	
	public void setSubTitle(String subTitle){
		this.subTitle = subTitle;
	}
	
	public String getEnSubTitle(){
		return enSubTitle;
	}
	
	public void setEnSubTitle(String enSubTitle){
		this.enSubTitle = enSubTitle;
	}
	
	public Integer getIsValid(){
		return isValid;
	}
	
	public void setIsValid(Integer isValid){
		this.isValid = isValid;
	}
	
	public String getImage(){
		return image;
	}
	
	public void setImage(String image){
		this.image = image;
	}
	
	public String getLink(){
		return link;
	}
	
	public void setLink(String link){
		this.link = link;
	}
	
	
}
