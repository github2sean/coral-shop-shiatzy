package com.dookay.coral.shop.goods.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 原型规格选项的domain
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Table(name = "t_goods_prototype_specification_option")
public class PrototypeSpecificationOptionDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*原型规格id*/
	private String protorypeSpecificationId;
	
	/*选项名称*/
	private String name;
	
	/*选项值*/
	private String value;
	
	/*显示顺序*/
	private String displayOrder;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public String getProtorypeSpecificationId(){
		return protorypeSpecificationId;
	}
	
	public void setProtorypeSpecificationId(String protorypeSpecificationId){
		this.protorypeSpecificationId = protorypeSpecificationId;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getValue(){
		return value;
	}
	
	public void setValue(String value){
		this.value = value;
	}
	
	public String getDisplayOrder(){
		return displayOrder;
	}
	
	public void setDisplayOrder(String displayOrder){
		this.displayOrder = displayOrder;
	}
	
	
}
