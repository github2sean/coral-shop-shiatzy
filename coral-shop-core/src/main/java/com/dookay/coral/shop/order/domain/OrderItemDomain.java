package com.dookay.coral.shop.order.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 订单明细的domain
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@Table(name = "t_order_item")
public class OrderItemDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*订单id*/
	private Long orderId;
	
	/*skuId*/
	private Long skuId;
	
	/*数量*/
	private Long num;
	
	/*商品名称*/
	private String goodsName;
	
	/*商品编号*/
	private String goodsCode;
	
	/*商品价格*/
	private Double goodsPrice;
	
	/*sku规格json*/
	private String skuSpecifications;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getOrderId(){
		return orderId;
	}
	
	public void setOrderId(Long orderId){
		this.orderId = orderId;
	}
	
	public Long getSkuId(){
		return skuId;
	}
	
	public void setSkuId(Long skuId){
		this.skuId = skuId;
	}
	
	public Long getNum(){
		return num;
	}
	
	public void setNum(Long num){
		this.num = num;
	}
	
	public String getGoodsName(){
		return goodsName;
	}
	
	public void setGoodsName(String goodsName){
		this.goodsName = goodsName;
	}
	
	public String getGoodsCode(){
		return goodsCode;
	}
	
	public void setGoodsCode(String goodsCode){
		this.goodsCode = goodsCode;
	}
	
	public Double getGoodsPrice(){
		return goodsPrice;
	}
	
	public void setGoodsPrice(Double goodsPrice){
		this.goodsPrice = goodsPrice;
	}
	
	public String getSkuSpecifications(){
		return skuSpecifications;
	}
	
	public void setSkuSpecifications(String skuSpecifications){
		this.skuSpecifications = skuSpecifications;
	}
	
	
}
