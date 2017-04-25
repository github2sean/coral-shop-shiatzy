package com.dookay.coral.shop.order.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

/**
 * 订单退货申请明细的domain
 * @author : luxor
 * @since : 2017年04月24日
 * @version : v0.0.1
 */
@Table(name = "t_order_return_request_item")
public class OrderReturnRequestItemDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*主键id*/
	@Id
	private Long id;
	
	/*订单退货申请id*/
	private Long returnRequestId;
	
	/*订单明细id*/
	private String orderItemId;
	
	/*客户id*/
	private Long customerId;
	
	/*数量*/
	private Integer num;
	
	/*退货理由json*/
	private String returnReason;
	
	/*退货时间*/
	private Date createTime;
	
	/*退货状态：1待收货 2已收货 3接受退货 4拒绝退货 5取消退货*/
	private Integer status;
	
	/*管理员备注*/
	private String adminMemo;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getReturnRequestId(){
		return returnRequestId;
	}
	
	public void setReturnRequestId(Long returnRequestId){
		this.returnRequestId = returnRequestId;
	}
	
	public String getOrderItemId(){
		return orderItemId;
	}
	
	public void setOrderItemId(String orderItemId){
		this.orderItemId = orderItemId;
	}
	
	public Long getCustomerId(){
		return customerId;
	}
	
	public void setCustomerId(Long customerId){
		this.customerId = customerId;
	}
	
	public Integer getNum(){
		return num;
	}
	
	public void setNum(Integer num){
		this.num = num;
	}
	
	public String getReturnReason(){
		return returnReason;
	}
	
	public void setReturnReason(String returnReason){
		this.returnReason = returnReason;
	}
	
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Integer getStatus(){
		return status;
	}
	
	public void setStatus(Integer status){
		this.status = status;
	}
	
	public String getAdminMemo(){
		return adminMemo;
	}
	
	public void setAdminMemo(String adminMemo){
		this.adminMemo = adminMemo;
	}
	
	
}
