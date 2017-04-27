package com.dookay.coral.shop.goods.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 原型规格的domain
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Table(name = "t_goods_prototype_specification")
public class PrototypeSpecificationDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*规格名称*/
	private String name;
	
	/*显示顺序*/
	private Integer displayOrder;
	
	/*原型id*/
	private Long prototypeId;
	
	/*是否颜色：1是 0否*/
	private Integer isColor;
	
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
	
	public Integer getDisplayOrder(){
		return displayOrder;
	}
	
	public void setDisplayOrder(Integer displayOrder){
		this.displayOrder = displayOrder;
	}
	
	public Long getPrototypeId(){
		return prototypeId;
	}
	
	public void setPrototypeId(Long prototypeId){
		this.prototypeId = prototypeId;
	}
	
	public Integer getIsColor(){
		return isColor;
	}
	
	public void setIsColor(Integer isColor){
		this.isColor = isColor;
	}
	
	
}
