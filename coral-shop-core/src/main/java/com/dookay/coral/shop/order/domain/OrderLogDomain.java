package com.dookay.coral.shop.order.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 订单日志的domain
 * @author : luxor
 * @since : 2017年04月27日
 * @version : v0.0.1
 */
@Table(name = "t_order_log")
public class OrderLogDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*订单id*/
	private Long orderId;
	
	/*创建时间*/
	private Date createTime;
	
	/*操作人id*/
	private Long adminId;
	
	/*日志内容*/
	private String message;
	
	/*是否成功：1是 0否*/
	private Integer isSuccessed;
	
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
	
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Long getAdminId(){
		return adminId;
	}
	
	public void setAdminId(Long adminId){
		this.adminId = adminId;
	}
	
	public String getMessage(){
		return message;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	
	public Integer getIsSuccessed(){
		return isSuccessed;
	}
	
	public void setIsSuccessed(Integer isSuccessed){
		this.isSuccessed = isSuccessed;
	}
	
	
}
