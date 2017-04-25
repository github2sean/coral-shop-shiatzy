package com.dookay.coral.shop.goods.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 商品sku的domain
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@Table(name = "t_goods_sku")
public class GoodsSkuDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*商品id*/
	private Long goodsId;
	
	/*商品名称*/
	private String name;
	
	/*sku编码*/
	private String code;
	
	/*单价*/
	private Double price;
	
	/*库存数量*/
	private Integer quantity;
	
	/*商品规格json*/
	private String specfications;
	
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
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getCode(){
		return code;
	}
	
	public void setCode(String code){
		this.code = code;
	}
	
	public Double getPrice(){
		return price;
	}
	
	public void setPrice(Double price){
		this.price = price;
	}
	
	public Integer getQuantity(){
		return quantity;
	}
	
	public void setQuantity(Integer quantity){
		this.quantity = quantity;
	}
	
	public String getSpecfications(){
		return specfications;
	}
	
	public void setSpecfications(String specfications){
		this.specfications = specfications;
	}
	
	
}
