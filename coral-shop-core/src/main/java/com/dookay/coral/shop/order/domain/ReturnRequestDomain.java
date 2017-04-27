package com.dookay.coral.shop.order.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 订单退货申请的domain
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Table(name = "t_order_return_request")
public class ReturnRequestDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*订单id*/
	private Long orderId;
	
	/*订单号*/
	private String orderNo;
	
	/*下单时间*/
	private Date orderTime;
	
	/*创建时间*/
	private Date createTime;
	
	/*退货方式：1快递取件 2退回门店*/
	private Integer returnShippingMethod;
	
	/*备注*/
	private String memo;
	
	/*收货人姓名*/
	private String shipName;
	
	/*收货人地址*/
	private String shipAddress;
	
	/*退货门店id*/
	private Long returnShopId;
	
	/*客户id*/
	private Long customerId;
	
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
	
	public String getOrderNo(){
		return orderNo;
	}
	
	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
	}
	
	public Date getOrderTime(){
		return orderTime;
	}
	
	public void setOrderTime(Date orderTime){
		this.orderTime = orderTime;
	}
	
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Integer getReturnShippingMethod(){
		return returnShippingMethod;
	}
	
	public void setReturnShippingMethod(Integer returnShippingMethod){
		this.returnShippingMethod = returnShippingMethod;
	}
	
	public String getMemo(){
		return memo;
	}
	
	public void setMemo(String memo){
		this.memo = memo;
	}
	
	public String getShipName(){
		return shipName;
	}
	
	public void setShipName(String shipName){
		this.shipName = shipName;
	}
	
	public String getShipAddress(){
		return shipAddress;
	}
	
	public void setShipAddress(String shipAddress){
		this.shipAddress = shipAddress;
	}
	
	public Long getReturnShopId(){
		return returnShopId;
	}
	
	public void setReturnShopId(Long returnShopId){
		this.returnShopId = returnShopId;
	}
	
	public Long getCustomerId(){
		return customerId;
	}
	
	public void setCustomerId(Long customerId){
		this.customerId = customerId;
	}
	
	
}
