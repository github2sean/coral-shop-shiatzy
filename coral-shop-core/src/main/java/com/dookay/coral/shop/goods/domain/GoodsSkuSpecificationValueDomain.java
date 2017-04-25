package com.dookay.coral.shop.goods.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 商品sku规格值的domain
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@Table(name = "t_goods_sku_specification_value")
public class GoodsSkuSpecificationValueDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*skuid*/
	private Long skuId;
	
	/*商品id*/
	private Long goodsId;
	
	/*原型规格id*/
	private Long prototypeSpecificationId;
	
	/*原型规格选项id*/
	private Long prototypeSpecificationOptionId;
	
	/*sku规格名称*/
	private String name;
	
	/*sku规格值*/
	private String value;
	
	/*规格图片*/
	private String imageUrl;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getSkuId(){
		return skuId;
	}
	
	public void setSkuId(Long skuId){
		this.skuId = skuId;
	}
	
	public Long getGoodsId(){
		return goodsId;
	}
	
	public void setGoodsId(Long goodsId){
		this.goodsId = goodsId;
	}
	
	public Long getPrototypeSpecificationId(){
		return prototypeSpecificationId;
	}
	
	public void setPrototypeSpecificationId(Long prototypeSpecificationId){
		this.prototypeSpecificationId = prototypeSpecificationId;
	}
	
	public Long getPrototypeSpecificationOptionId(){
		return prototypeSpecificationOptionId;
	}
	
	public void setPrototypeSpecificationOptionId(Long prototypeSpecificationOptionId){
		this.prototypeSpecificationOptionId = prototypeSpecificationOptionId;
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
	
	public String getImageUrl(){
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl){
		this.imageUrl = imageUrl;
	}
	
	
}
