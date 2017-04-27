package com.dookay.coral.shop.order.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 购物车的domain
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Table(name = "t_order_shopping_cart_item")
public class ShoppingCartItemDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*购物车类型：1购物车 2心愿单*/
	private Integer shoppingCartType;
	
	/*客户id*/
	private Long customerId;
	
	/*sukId*/
	private Long skuId;
	
	/*数量*/
	private Integer num;
	
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
	
	public Integer getShoppingCartType(){
		return shoppingCartType;
	}
	
	public void setShoppingCartType(Integer shoppingCartType){
		this.shoppingCartType = shoppingCartType;
	}
	
	public Long getCustomerId(){
		return customerId;
	}
	
	public void setCustomerId(Long customerId){
		this.customerId = customerId;
	}
	
	public Long getSkuId(){
		return skuId;
	}
	
	public void setSkuId(Long skuId){
		this.skuId = skuId;
	}
	
	public Integer getNum(){
		return num;
	}
	
	public void setNum(Integer num){
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
