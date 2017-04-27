package com.dookay.coral.shop.goods.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 商品分类的domain
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Table(name = "t_goods_category")
public class GoodsCategoryDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*父id*/
	private Long parentId;
	
	/*原型id*/
	private Long prototypeId;
	
	/*分类名称*/
	private String name;
	
	/*描述*/
	private String description;
	
	/*slug*/
	private String slug;
	
	/*缩略图*/
	private String thumb;
	
	/*是否有效：1是 0否*/
	private Integer isValid;
	
	/*显示顺序*/
	private Integer displayOrder;
	
	/*创建时间*/
	private Date createTime;
	
	/*创建人id*/
	private Long creatorId;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getParentId(){
		return parentId;
	}
	
	public void setParentId(Long parentId){
		this.parentId = parentId;
	}
	
	public Long getPrototypeId(){
		return prototypeId;
	}
	
	public void setPrototypeId(Long prototypeId){
		this.prototypeId = prototypeId;
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
	
	public String getSlug(){
		return slug;
	}
	
	public void setSlug(String slug){
		this.slug = slug;
	}
	
	public String getThumb(){
		return thumb;
	}
	
	public void setThumb(String thumb){
		this.thumb = thumb;
	}
	
	public Integer getIsValid(){
		return isValid;
	}
	
	public void setIsValid(Integer isValid){
		this.isValid = isValid;
	}
	
	public Integer getDisplayOrder(){
		return displayOrder;
	}
	
	public void setDisplayOrder(Integer displayOrder){
		this.displayOrder = displayOrder;
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
	
	
}
