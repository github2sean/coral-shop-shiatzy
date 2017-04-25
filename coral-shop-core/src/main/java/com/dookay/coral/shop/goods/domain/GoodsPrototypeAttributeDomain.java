package com.dookay.coral.shop.goods.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 原型属性的domain
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@Table(name = "t_goods_prototype_attribute")
public class GoodsPrototypeAttributeDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*属性名称*/
	private String name;
	
	/*原型id*/
	private Long prototypeId;
	
	/*是否筛选： 1是 0否*/
	private Integer isFilter;
	
	/*是否多选：1是 0否*/
	private Integer isMultiple;
	
	/**/
	private Integer isRequired;
	
	/*是否输入：1是 0否*/
	private Integer isInput;
	
	/*显示顺序*/
	private Integer displayOrder;
	
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
	
	public Long getPrototypeId(){
		return prototypeId;
	}
	
	public void setPrototypeId(Long prototypeId){
		this.prototypeId = prototypeId;
	}
	
	public Integer getIsFilter(){
		return isFilter;
	}
	
	public void setIsFilter(Integer isFilter){
		this.isFilter = isFilter;
	}
	
	public Integer getIsMultiple(){
		return isMultiple;
	}
	
	public void setIsMultiple(Integer isMultiple){
		this.isMultiple = isMultiple;
	}
	
	public Integer getIsRequired(){
		return isRequired;
	}
	
	public void setIsRequired(Integer isRequired){
		this.isRequired = isRequired;
	}
	
	public Integer getIsInput(){
		return isInput;
	}
	
	public void setIsInput(Integer isInput){
		this.isInput = isInput;
	}
	
	public Integer getDisplayOrder(){
		return displayOrder;
	}
	
	public void setDisplayOrder(Integer displayOrder){
		this.displayOrder = displayOrder;
	}
	
	
}
