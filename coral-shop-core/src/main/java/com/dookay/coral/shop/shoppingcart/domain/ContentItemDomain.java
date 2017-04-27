package com.dookay.coral.shop.shoppingcart.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 内容的domain
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@Table(name = "t_content_item")
public class ContentItemDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*标题*/
	private String title;
	
	/*缩略图*/
	private String thumb;
	
	/*分类id*/
	private Long categoryId;
	
	/*创建时间*/
	private Date createTime;
	
	/*创建人id*/
	private Long creatorId;
	
	/*内容*/
	private String content;
	
	/*排序*/
	private Long displayOrder;
	
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
	
	public String getThumb(){
		return thumb;
	}
	
	public void setThumb(String thumb){
		this.thumb = thumb;
	}
	
	public Long getCategoryId(){
		return categoryId;
	}
	
	public void setCategoryId(Long categoryId){
		this.categoryId = categoryId;
	}
	
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Long getCreatorId(){
		return creatorId;
	}
	
	public void setCreatorId(Long creatorId){
		this.creatorId = creatorId;
	}
	
	public String getContent(){
		return content;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public Long getDisplayOrder(){
		return displayOrder;
	}
	
	public void setDisplayOrder(Long displayOrder){
		this.displayOrder = displayOrder;
	}
	
	
}
