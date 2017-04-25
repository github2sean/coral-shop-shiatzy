package com.dookay.coral.shop.goods.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 商品的domain
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@Table(name = "t_goods")
public class GoodsDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*商品名称*/
	private String name;
	
	/*简单标题*/
	private String brief;
	
	/*商品编号*/
	private String code;
	
	/*商品描述*/
	private String description;
	
	/*商品详情*/
	private String details;
	
	/*原型id*/
	private Long prototypeId;
	
	/*商品分类id*/
	private Long categoryId;
	
	/*是否上架：1是 0否*/
	private Integer isPublished;
	
	/*创建时间*/
	private Date createTime;
	
	/*修改时间*/
	private Date updateTime;
	
	/*缩略图*/
	private String thumb;
	
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
	
	public String getBrief(){
		return brief;
	}
	
	public void setBrief(String brief){
		this.brief = brief;
	}
	
	public String getCode(){
		return code;
	}
	
	public void setCode(String code){
		this.code = code;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getDetails(){
		return details;
	}
	
	public void setDetails(String details){
		this.details = details;
	}
	
	public Long getPrototypeId(){
		return prototypeId;
	}
	
	public void setPrototypeId(Long prototypeId){
		this.prototypeId = prototypeId;
	}
	
	public Long getCategoryId(){
		return categoryId;
	}
	
	public void setCategoryId(Long categoryId){
		this.categoryId = categoryId;
	}
	
	public Integer getIsPublished(){
		return isPublished;
	}
	
	public void setIsPublished(Integer isPublished){
		this.isPublished = isPublished;
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
	
	public String getThumb(){
		return thumb;
	}
	
	public void setThumb(String thumb){
		this.thumb = thumb;
	}
	
	
}
