package com.dookay.coral.shop.goods.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 商品属性值的domain
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Table(name = "t_goods_attribute_value")
public class GoodsAttributeValueDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*商品id*/
	private Long goodsId;
	
	/*原型属性id*/
	private Long prototypeAttributeId;
	
	/*原型属性选项id*/
	private Long prototypeAttributeOptionId;
	
	/*商品属性值*/
	private String value;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getGoodsId(){
		return goodsId;
	}
	
	public void setGoodsId(Long goodsId){
		this.goodsId = goodsId;
	}
	
	public Long getPrototypeAttributeId(){
		return prototypeAttributeId;
	}
	
	public void setPrototypeAttributeId(Long prototypeAttributeId){
		this.prototypeAttributeId = prototypeAttributeId;
	}
	
	public Long getPrototypeAttributeOptionId(){
		return prototypeAttributeOptionId;
	}
	
	public void setPrototypeAttributeOptionId(Long prototypeAttributeOptionId){
		this.prototypeAttributeOptionId = prototypeAttributeOptionId;
	}
	
	public String getValue(){
		return value;
	}
	
	public void setValue(String value){
		this.value = value;
	}
	
	
}
