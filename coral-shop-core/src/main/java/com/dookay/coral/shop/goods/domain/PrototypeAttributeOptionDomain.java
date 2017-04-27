package com.dookay.coral.shop.goods.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 原型属性选项的domain
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Table(name = "t_goods_prototype_attribute_option")
public class PrototypeAttributeOptionDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*原型属性id*/
	private Long prototypeAttributeId;
	
	/*选项值*/
	private String value;
	
	/*显示顺序*/
	private Integer displayOrder;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getPrototypeAttributeId(){
		return prototypeAttributeId;
	}
	
	public void setPrototypeAttributeId(Long prototypeAttributeId){
		this.prototypeAttributeId = prototypeAttributeId;
	}
	
	public String getValue(){
		return value;
	}
	
	public void setValue(String value){
		this.value = value;
	}
	
	public Integer getDisplayOrder(){
		return displayOrder;
	}
	
	public void setDisplayOrder(Integer displayOrder){
		this.displayOrder = displayOrder;
	}
	
	
}
